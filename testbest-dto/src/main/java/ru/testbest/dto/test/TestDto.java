package ru.testbest.dto.test;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.Data;
import ru.testbest.dto.BaseDTO;

@Data
public class TestDto implements BaseDTO {

  private UUID id;
  private String name;
  private String description;
  private LocalDateTime created;
  private Short duration;
  private UUID topicId;
  private UUID authorId;
  private Set<ChapterDto> chapters;
  private Short passScore;
}
