package ru.testbest.dto.admin;

import java.util.UUID;
import lombok.Data;
import ru.testbest.dto.BaseDTO;

@Data
public class RoleDto implements BaseDTO {

  private UUID id;
  private String name;
}
