package ru.testbest.dto.common;

import java.util.UUID;
import lombok.Data;
import ru.testbest.dto.BaseDTO;

@Data
public class QuestionTypeDto implements BaseDTO {

  private UUID id;
  private String name;
}
