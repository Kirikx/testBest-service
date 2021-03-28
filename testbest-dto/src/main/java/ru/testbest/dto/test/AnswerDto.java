package ru.testbest.dto.test;

import java.util.UUID;
import lombok.Data;
import ru.testbest.dto.BaseDTO;

@Data
public class AnswerDto implements BaseDTO {

  private UUID id;
  private String answerText;
  private UUID questionId;
}
