package ru.testbest.dto.admin;

import java.util.UUID;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import ru.testbest.dto.BaseDTO;

@Data
public class RoleDto implements BaseDTO, GrantedAuthority {

  private UUID id;
  private String name;

  @Override
  public String getAuthority() {
    return name;
  }
}
