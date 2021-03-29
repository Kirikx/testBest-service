package ru.testbest.service.impl.test;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.testbest.converter.impl.test.QuestionConverter;
import ru.testbest.converter.impl.test.UserTestConverter;
import ru.testbest.converter.impl.test.UserTestQuestionConverter;
import ru.testbest.dto.test.AnswerDto;
import ru.testbest.dto.test.QuestionDto;
import ru.testbest.dto.test.UserTestDto;
import ru.testbest.dto.test.UserTestQuestionDto;
import ru.testbest.persistence.dao.QuestionDao;
import ru.testbest.persistence.dao.UserTestDao;
import ru.testbest.persistence.dao.UserTestQuestionDao;
import ru.testbest.persistence.entity.Answer;
import ru.testbest.persistence.entity.Chapter;
import ru.testbest.persistence.entity.Question;
import ru.testbest.persistence.entity.UserTest;
import ru.testbest.persistence.entity.UserTestQuestion;
import ru.testbest.service.QuestionTypeEnum;
import ru.testbest.service.UserTestService;

@Service
@RequiredArgsConstructor
public class UserTestServiceImpl implements UserTestService {

  private final UserTestDao userTestDao;
  private final UserTestConverter userTestConverter;

  private final QuestionDao questionDao;
  private final QuestionConverter questionConverter;

  private final UserTestQuestionDao userTestQuestionDao;
  private final UserTestQuestionConverter userTestQuestionConverter;

  @Override
  @Transactional(readOnly = true)
  public List<UserTestDto> getUserTests(UUID userId) {
    return userTestDao.findAllByUserId(userId).stream()
        .map(userTestConverter::convertToDto)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public UserTestDto getActiveUserTestByUserId(UUID userId) {
    Optional<UserTest> oActiveUserTest = userTestDao
        .findAllByUserIdAndFinishedIsNull(userId).stream()
        .findFirst(); // TODO продумать механизм получения активного теста. Пока берем крайний

    return oActiveUserTest
        .map(userTestConverter::convertToDto)
        .orElse(null);
  }

  @Override
  @Transactional
  public Optional<QuestionDto> startUserTest(UUID testId, UUID userId) {
    UserTestDto findActiveUserTest = getActiveUserTestByUserId(userId);
    UserTestDto activeUserTest;
    if (findActiveUserTest != null) {
      activeUserTest = findActiveUserTest;
    } else {
      UserTestDto newUserTest = new UserTestDto();
      newUserTest.setUserId(userId);
      newUserTest.setTestId(testId);
      newUserTest.setStarted(LocalDateTime.now());

      activeUserTest = userTestConverter.convertToDto(
          userTestDao.save(
              userTestConverter.convertToEntity(newUserTest)));
    }
    return getNextQuestion(activeUserTest.getId());
  }


  private Optional<QuestionDto> getNextQuestion(UUID userTestId) {
    UserTest userTest = userTestDao.findById(userTestId)
        .orElseThrow(
            () -> new RuntimeException(String.format("User test id %s not found", userTestId)));

    LocalDateTime started = userTest.getStarted();
    Short duration = userTest.getTest().getDuration();
    LocalDateTime endTime = started.plusSeconds(duration);

    if (LocalDateTime.now().isBefore(endTime)) {
      finishUserTest(userTestId);
      return Optional.empty();
    }

    Set<Question> testQuestions = userTest.getTest().getChapters().stream()
        .map(Chapter::getQuestions)
        .flatMap(Collection::stream)
        .collect(Collectors.toSet());

    Set<UserTestQuestion> userTestQuestions = userTest.getUserTestQuestions();

    return testQuestions.stream()
        .filter(q -> userTestQuestions.stream()
            .noneMatch(utq -> q.getId().equals(utq.getQuestion().getId())))
        .map(questionConverter::convertToDto)
        .findAny();
  }

  @Override
  @Transactional
  public Optional<QuestionDto> createUserAnswer(UserTestQuestionDto userTestQuestionDto,
      UUID userId) {
    userTestQuestionDto.setAnswered(LocalDateTime.now());
    userTestQuestionDto.setIsCorrect(
        checkCorrectUserAnswer(userTestQuestionDto));

    UserTestQuestion userTestQuestion = userTestQuestionConverter
        .convertToEntity(userTestQuestionDto);
    userTestQuestionDao.save(userTestQuestion);

    return getNextQuestion(userTestQuestionDto.getUserTestId());
  }

  private Boolean checkCorrectUserAnswer(UserTestQuestionDto userTestQuestionDto) {
    Question question = questionDao.findById(userTestQuestionDto.getQuestionId())
        .orElseThrow(() -> new RuntimeException(
            String.format("Question id %s not found", userTestQuestionDto.getQuestionId())));

    QuestionTypeEnum questionType = Optional.ofNullable(QuestionTypeEnum
        .fromString(question.getQuestionType().getName()))
        .orElseThrow(() -> new RuntimeException(
            String
                .format("Question type name %s not found", question.getQuestionType().getName())));

    boolean correct = false;

    switch (questionType) {
      case SET:
        Optional<UUID> oSelectedAnswerId = userTestQuestionDto.getAnswers().stream()
            .map(AnswerDto::getId)
            .findFirst();
        if (oSelectedAnswerId.isPresent()) {
          UUID correctAnswerId = question.getAnswers().stream()
              .filter(Answer::getIsCorrect)
              .map(Answer::getId)
              .findFirst()
              .orElseThrow(() -> new RuntimeException("Question answer no contain correct answer"));
          correct = oSelectedAnswerId.get().equals(correctAnswerId);
        }
        break;

      case MALTY:
        Set<UUID> selectedAnswerIds = userTestQuestionDto.getAnswers().stream()
            .map(AnswerDto::getId)
            .collect(Collectors.toSet());
        if (!selectedAnswerIds.isEmpty()) {
          Set<UUID> correctAnswerIds = question.getAnswers().stream()
              .filter(Answer::getIsCorrect)
              .map(Answer::getId)
              .collect(Collectors.toSet());
          correct = selectedAnswerIds.containsAll(correctAnswerIds);
        }
        break;

      case FREE:
        correct = checkFreeTextAnswer(
            question.getAnswers().stream().findFirst(), userTestQuestionDto.getFreeAnswer());
        break;

      default:
        break;

    }

    return correct;
  }

  private boolean checkFreeTextAnswer(Optional<Answer> any, String freeAnswer) {
    String correctAnswer = any.map(Answer::getAnswer)
        .orElseThrow(() -> new RuntimeException("Question answer no contain correct answer"));
    return correctAnswer.equalsIgnoreCase(freeAnswer);
  }

  @Override
  @Transactional
  public UserTestDto finishUserTest(UUID userTestId) {
    UserTest userTest = userTestDao.findById(userTestId)
        .orElseThrow(
            () -> new RuntimeException(String.format("User test id %s not found", userTestId)));

    UserTestDto userTestDto = userTestConverter.convertToDto(userTest);
    userTestDto.setFinished(getMaxLocalDateTimeFromAnswers(userTestDto));
    userTestDto.setScore((short) userTestDto.getUserTestQuestions().stream()
        .filter(UserTestQuestionDto::getIsCorrect)
        .count());
    userTestDto.setIsPassed(userTest.getTest().getChapters().stream()
        .mapToLong(ch -> ch.getQuestions().size())
        .sum() <= userTestDto.getScore()); // TODO Тест пройден если все ответы правильные

    return userTestConverter.convertToDto(
        userTestDao.save(
            userTestConverter.convertToEntity(userTestDto)));
  }

  private LocalDateTime getMaxLocalDateTimeFromAnswers(UserTestDto userTestDto) {
    return userTestDto.getUserTestQuestions().stream()
        .map(UserTestQuestionDto::getAnswered)
        .max(LocalDateTime::compareTo)
        .orElse(LocalDateTime.now());
  }
}
