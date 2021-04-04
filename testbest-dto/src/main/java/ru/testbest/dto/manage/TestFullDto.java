package ru.testbest.dto.manage;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.Data;
import ru.testbest.dto.BaseDTO;

@Data
public class TestFullDto implements BaseDTO {

  private UUID id;
  private String name;
  private String description;
  private LocalDateTime created;
  private Short duration;
  private Boolean isDeleted;
  private UUID topicId;
  private UUID authorId;
  private Set<ChapterFullDto> chapters;
  private Short passScore;
}
