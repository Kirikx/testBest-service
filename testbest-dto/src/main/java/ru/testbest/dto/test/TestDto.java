package ru.testbest.dto.test;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import lombok.Data;

@Data
public class TestDto {

  private UUID id;
  private String name;
  private String description;
  private LocalDateTime created;
  private Short duration;
  private Boolean isDeleted;
  private UUID topicId;
  private UUID authorId;
  private Set<ChapterDto> chapters;
}
