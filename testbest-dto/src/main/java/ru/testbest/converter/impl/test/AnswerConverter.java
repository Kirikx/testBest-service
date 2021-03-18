package ru.testbest.converter.impl.test;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.testbest.converter.ConverterTest;
import ru.testbest.dto.test.AnswerDto;
import ru.testbest.persistence.entity.Answer;

@Component
@RequiredArgsConstructor
public class AnswerConverter implements ConverterTest<Answer, AnswerDto> {

  @Override
  public AnswerDto convertToDto(Answer entity) {
    AnswerDto answerDto = new AnswerDto();
    answerDto.setId(entity.getId());
    answerDto.setAnswerText(entity.getAnswer());
    answerDto.setQuestionId(Optional.ofNullable(entity.getQuestion().getId())
        .orElseThrow(() -> new RuntimeException("Answer is not contains question link")));
    return answerDto;
  }

  @Override
  public Answer convertToEntity(AnswerDto dto) {
    throw new UnsupportedOperationException();
  }
}
