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
  public UserTestDto getActiveUserTest(UUID userId) {
    return getLastUserTestByUserId(userId)
        .filter(this::isLegalTime)
        .map(userTestConverter::convertToDto)
        .orElse(null);
  }

  private Optional<UserTest> getLastUserTestByUserId(UUID userId) {
//    return userTestDao.findLastByUserIdAndStarted(userId);
    return Optional.empty();
  }

  private boolean isLegalTime(UserTest userTest) {
    LocalDateTime timeStart = Optional.ofNullable(userTest.getStarted())
        .orElseThrow(RuntimeException::new);
    return timeStart
        .plusSeconds(userTest.getTest().getDuration())
        .isBefore(LocalDateTime.now());
  }

  @Override
  @Transactional
  public Optional<QuestionDto> startUserTest(UUID testId, UUID userId) {
    UserTestDto findActiveUserTest = getActiveUserTest(userId);
    UserTestDto activeUserTest;
    if (findActiveUserTest != null) {
      activeUserTest = findActiveUserTest;
    } else {
      UserTestDto newUserTest = new UserTestDto();
      newUserTest.setUserId(userId);
      newUserTest.setTestId(testId);
      newUserTest.setStarted(LocalDateTime.now());
      newUserTest.setFinished(LocalDateTime.now());

      activeUserTest = userTestConverter.convertToDto(
          userTestDao.save(
              userTestConverter.convertToEntity(newUserTest)));
    }
    return getNextQuestion(activeUserTest.getId());
  }

  @Override
  @Transactional
  public Optional<QuestionDto> getNextQuestion(UUID userTestId) {
    UserTest userTest = userTestDao.findById(userTestId)
        .orElseThrow(
            () -> new RuntimeException(String.format("User test id %s not found", userTestId)));

    if (isLegalTime(userTest)) {

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

    return Optional.empty();
  }

  @Override
  @Transactional
  public Optional<QuestionDto> createUserAnswer(UserTestQuestionDto userTestQuestionDto,
      UUID userId) {
    Optional<UserTest> lastUserTestByUserId = getLastUserTestByUserId(userId);
    UserTest userTest = lastUserTestByUserId.orElse(null);
    if (lastUserTestByUserId.isPresent() && isLegalTime(userTest)) {
      userTest.setFinished(LocalDateTime.now());

      userTestQuestionDto.setAnswered(LocalDateTime.now());
      userTestQuestionDto.setIsCorrect(
          checkCorrectSelectedAnswer(userTestQuestionDto));

      userTest.setScore((short) (userTestQuestionDto.getIsCorrect() ?
          userTest.getScore() + 1 : userTest.getScore()));
      userTest.setIsPassed(userTest.getScore() >= userTest.getTest().getPassScore());

      UserTestQuestion userTestQuestion = userTestQuestionConverter
          .convertToEntity(userTestQuestionDto);

      userTestDao.save(userTest);
      userTestQuestionDao.save(userTestQuestion);

      return getNextQuestion(userTestQuestionDto.getUserTestId());
    }
    return Optional.empty();
  }

  private Boolean checkCorrectSelectedAnswer(UserTestQuestionDto userTestQuestionDto) {
    Question question = questionDao.findById(userTestQuestionDto.getQuestionId())
        .orElseThrow(() -> new RuntimeException(
            String.format("Question id %s not found", userTestQuestionDto.getQuestionId())));

    QuestionTypeEnum questionType = Optional.ofNullable(QuestionTypeEnum
        .fromString(question.getQuestionType().getName()))
        .orElseThrow(() -> new RuntimeException(
            String
                .format("Question type name %s not found", question.getQuestionType().getName())));

    boolean isCorrect = false;

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
          isCorrect = oSelectedAnswerId.get().equals(correctAnswerId);
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
          isCorrect = selectedAnswerIds.containsAll(correctAnswerIds);
        }
        break;

      case FREE:
        isCorrect = checkCorrectFreeTextAnswer(
            question.getAnswers().stream().findFirst(), userTestQuestionDto.getFreeAnswer());
        break;

      default:
        break;

    }

    return isCorrect;
  }

  private boolean checkCorrectFreeTextAnswer(Optional<Answer> any, String freeAnswer) {
    String correctAnswer = any.map(Answer::getAnswer)
        .orElseThrow(() -> new RuntimeException("Question answer no contain correct answer"));
    return correctAnswer.equalsIgnoreCase(freeAnswer);
  }

  @Override
  @Transactional(readOnly = true)
  public List<UserTestQuestionDto> getFailQuestionsByUserTestId(UUID userTestId) {
    return userTestQuestionDao.findAllByUserTestId(userTestId).stream()
        .filter(utq -> !utq.getIsCorrect())
        .map(userTestQuestionConverter::convertToDto)
        .collect(Collectors.toList());
  }
}
