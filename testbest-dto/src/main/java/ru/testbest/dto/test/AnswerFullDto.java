package ru.testbest.dto.test;

import java.util.UUID;
import lombok.Data;
import ru.testbest.dto.BaseDTO;

@Data
public class AnswerFullDto implements BaseDTO {

  private UUID id;
  private String answerText;
  private UUID questionId;
  private Boolean isCorrect;
  private Boolean isDeleted;
}
