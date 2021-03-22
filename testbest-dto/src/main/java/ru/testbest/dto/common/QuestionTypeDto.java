package ru.testbest.dto.common;

import lombok.Data;

import java.util.UUID;

@Data
public class QuestionTypeDto {

  private UUID id;
  private String name;
}
