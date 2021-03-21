package ru.testbest.converter.impl.test;

import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.testbest.converter.ConverterTest;
import ru.testbest.dto.test.UserTestQuestionDto;
import ru.testbest.persistence.dao.QuestionDao;
import ru.testbest.persistence.dao.UserTestDao;
import ru.testbest.persistence.entity.UserTestQuestion;

@Component
@RequiredArgsConstructor
public class UserTestQuestionConverter implements
    ConverterTest<UserTestQuestion, UserTestQuestionDto> {

  private final SelectedAnswerConverter selectedAnswerConverter;
  private final UserTestDao userTestDao;
  private final QuestionDao questionDao;

  @Override
  public UserTestQuestionDto convertToDto(UserTestQuestion entity) {
    UserTestQuestionDto userTestQuestionDto = new UserTestQuestionDto();
    userTestQuestionDto.setId(entity.getId());
    userTestQuestionDto.setFreeAnswer(entity.getFreeAnswer());
    userTestQuestionDto.setAnswered(entity.getAnswered());
    userTestQuestionDto.setIsCorrect(entity.getIsCorrect());
    userTestQuestionDto
        .setUserTestId(entity.getUserTest() == null ? null : entity.getUserTest().getId());
    userTestQuestionDto
        .setQuestionId(entity.getQuestion() == null ? null : entity.getQuestion().getId());
    userTestQuestionDto.setSelectedAnswers(entity.getSelectedAnswers().stream()
        .map(selectedAnswerConverter::convertToDto)
        .collect(Collectors.toSet()));
    return userTestQuestionDto;

  }

  @Override
  public UserTestQuestion convertToEntity(UserTestQuestionDto dto) {
    UserTestQuestion userTestQuestion = new UserTestQuestion();
    Optional.ofNullable(dto.getId())
        .ifPresent(userTestQuestion::setId);
    userTestQuestion.setFreeAnswer(dto.getFreeAnswer());
    userTestQuestion.setAnswered(dto.getAnswered());
    userTestQuestion.setIsCorrect(dto.getIsCorrect());
    userTestQuestion.setUserTest(userTestDao.findById(dto.getUserTestId()).orElse(null));
    userTestQuestion.setQuestion(questionDao.findById(dto.getQuestionId()).orElse(null));
    userTestQuestion.setSelectedAnswers(dto.getSelectedAnswers().stream()
        .map(selectedAnswerConverter::convertToEntity)
        .collect(Collectors.toSet()));
    return userTestQuestion;

  }
}
