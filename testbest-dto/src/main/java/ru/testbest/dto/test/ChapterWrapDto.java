package ru.testbest.dto.test;

import java.util.UUID;
import lombok.Data;
import ru.testbest.dto.Wrapper;

@Data
public class ChapterWrapDto implements Wrapper {

  private UUID id;
  private String name;
  private String description;
}
