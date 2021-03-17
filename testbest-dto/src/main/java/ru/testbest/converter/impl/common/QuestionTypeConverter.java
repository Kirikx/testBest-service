package ru.testbest.converter.impl.common;

import org.springframework.stereotype.Component;
import ru.testbest.converter.ConverterTest;
import ru.testbest.dto.common.QuestionTypeDto;
import ru.testbest.persistence.entity.QuestionType;

@Component
public class QuestionTypeConverter implements ConverterTest<QuestionType, QuestionTypeDto> {

  @Override
  public QuestionTypeDto convertToDto(QuestionType entity) {
    QuestionTypeDto questionTypeDto = new QuestionTypeDto();
    questionTypeDto.setId(entity.getId());
    questionTypeDto.setName(entity.getName());
    return questionTypeDto;
  }

  @Override
  public QuestionType convertToEntity(QuestionTypeDto dto) {
    QuestionType questionType = new QuestionType();
    questionType.setId(dto.getId());
    questionType.setName(dto.getName());
    return questionType;
  }
}
