package ru.testbest.dto.test;

import java.util.Set;
import lombok.Data;

@Data
public class ChapterDto {

  private String id;
  private String name;
  private String description;
  private Boolean isDeleted;
  private String testId;
  private Set<QuestionDto> questions;
}
