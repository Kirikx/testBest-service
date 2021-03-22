package ru.testbest.dto.test;

import lombok.Data;

@Data
public class AnswerFullDto {

  private String id;
  private String answerText;
  private String questionId;
  private Boolean isCorrect;
  private Boolean isDeleted;
}
