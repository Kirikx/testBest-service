package ru.testbest.dto.test;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class TestDto {

  private String id;
  private String name;
  private String description;
  private LocalDateTime created;
  private Short duration;
  private Boolean isDeleted;
  private String topicId;
  private String authorId;
  private Set<ChapterDto> chapters;
}
