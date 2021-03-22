package ru.testbest.dto.test;

import lombok.Data;

import java.util.UUID;

@Data
public class AnswerDto {

  private UUID id;
  private String answerText;
  private UUID questionId;
}
