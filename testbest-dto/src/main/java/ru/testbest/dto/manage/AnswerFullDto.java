package ru.testbest.dto.manage;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.testbest.dto.BaseDTO;
import ru.testbest.dto.test.AnswerDto;

@EqualsAndHashCode(callSuper = true)
@Data
public class AnswerFullDto extends AnswerDto implements BaseDTO {

  private Boolean isCorrect;
  private Boolean isDeleted;
}
