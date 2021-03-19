package ru.testbest.converter.impl.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.testbest.converter.ConverterTest;
import ru.testbest.dto.test.SelectedAnswerDto;
import ru.testbest.persistence.dao.AnswerDao;
import ru.testbest.persistence.dao.UserTestQuestionDao;
import ru.testbest.persistence.entity.SelectedAnswer;

@Component
@RequiredArgsConstructor
public class SelectedAnswerConverter implements ConverterTest<SelectedAnswer, SelectedAnswerDto> {

  private final UserTestQuestionDao userTestQuestionDao;
  private final AnswerDao answerDao;

  @Override
  public SelectedAnswerDto convertToDto(SelectedAnswer entity) {
    SelectedAnswerDto selectedAnswerDto = new SelectedAnswerDto();
    selectedAnswerDto.setId(entity.getId());
    selectedAnswerDto.setUserTestQuestionId(
        entity.getUserTestQuestion() == null ? null : entity.getUserTestQuestion().getId());
    selectedAnswerDto.setAnswerId(entity.getAnswer() == null ? null : entity.getAnswer().getId());
    return selectedAnswerDto;
  }

  @Override
  public SelectedAnswer convertToEntity(SelectedAnswerDto dto) {
    SelectedAnswer selectedAnswer = new SelectedAnswer();
    selectedAnswer.setId(dto.getId());
    selectedAnswer.setUserTestQuestion(
        userTestQuestionDao.findById(dto.getUserTestQuestionId()).orElse(null));
    selectedAnswer.setAnswer(answerDao.findById(dto.getAnswerId()).orElse(null));
    return selectedAnswer;
  }
}
