package ru.testbest.converter.impl.common;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.testbest.converter.ConverterTest;
import ru.testbest.dto.common.QuestionTypeDto;
import ru.testbest.persistence.entity.QuestionType;

@Component
@RequiredArgsConstructor
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
    Optional.ofNullable(dto.getId())
        .ifPresent(questionType::setId);
    questionType.setName(dto.getName());
    return questionType;
  }
}
