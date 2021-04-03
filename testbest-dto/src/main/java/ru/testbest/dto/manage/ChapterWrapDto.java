package ru.testbest.dto.manage;

import java.util.UUID;
import lombok.Data;
import ru.testbest.dto.BaseDTO;

@Data
public class ChapterWrapDto implements BaseDTO {

  private UUID id;
  private String name;
  private String description;
}
