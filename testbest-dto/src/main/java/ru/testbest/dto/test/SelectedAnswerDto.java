package ru.testbest.dto.test;

import lombok.Data;

@Data
public class SelectedAnswerDto {

  private String id;
  private String userTestQuestionId;
  private String answerId;
}
