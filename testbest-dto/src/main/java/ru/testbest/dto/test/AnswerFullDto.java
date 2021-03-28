package ru.testbest.dto.test;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.testbest.dto.BaseDTO;

@EqualsAndHashCode(callSuper = true)
@Data
public class AnswerFullDto extends AnswerDto implements BaseDTO {

  private Boolean isCorrect;
  private Boolean isDeleted;
}
