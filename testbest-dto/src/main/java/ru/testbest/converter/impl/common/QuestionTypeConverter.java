package ru.testbest.converter.impl.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.testbest.converter.impl.AbstractMapper;
import ru.testbest.dto.common.QuestionTypeDto;
import ru.testbest.persistence.entity.QuestionType;

@Component
public class QuestionTypeConverter extends AbstractMapper<QuestionType, QuestionTypeDto> {

  @Autowired
  public QuestionTypeConverter() {
    super(QuestionType.class, QuestionTypeDto.class);
  }
}
