package ru.testbest.dto.test;

import java.util.Set;
import java.util.UUID;

import lombok.Data;

@Data
public class ChapterDto {

  private UUID id;
  private String name;
  private String description;
  private Boolean isDeleted;
  private UUID testId;
  private Set<QuestionDto> questions;
}
