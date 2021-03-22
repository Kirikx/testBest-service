package ru.testbest.dto.test;

import java.util.UUID;
import lombok.Data;

@Data
public class AnswerFullDto {

  private UUID id;
  private String answerText;
  private UUID questionId;
  private Boolean isCorrect;
  private Boolean isDeleted;
}
