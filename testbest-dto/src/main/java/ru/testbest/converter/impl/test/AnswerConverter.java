package ru.testbest.converter.impl.test;

import org.springframework.stereotype.Component;
import ru.testbest.converter.ConverterTest;
import ru.testbest.dto.test.AnswerDto;
import ru.testbest.persistence.entity.Answer;

@Component
public class AnswerConverter implements ConverterTest<Answer, AnswerDto> {


  @Override
  public AnswerDto convertToDto(Answer entity) {
    AnswerDto answerDto = new AnswerDto();
    answerDto.setId(entity.getId());
    answerDto.setAnswerText(entity.getAnswer());
    return answerDto;
  }

  @Override
  public Answer convertToEntity(AnswerDto dto) {
    throw new UnsupportedOperationException();
  }
}
