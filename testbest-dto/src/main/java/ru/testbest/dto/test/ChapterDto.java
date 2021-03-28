package ru.testbest.dto.test;

import java.util.Set;
import java.util.UUID;
import lombok.Data;
import ru.testbest.dto.BaseDTO;

@Data
public class ChapterDto implements BaseDTO {

  private UUID id;
  private String name;
  private String description;
  private Boolean isDeleted;
  private UUID testId;
  private Set<QuestionDto> questions;
}
