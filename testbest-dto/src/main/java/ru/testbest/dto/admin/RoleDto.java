package ru.testbest.dto.admin;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class RoleDto implements GrantedAuthority {

  private String id;
  private String name;

  @Override
  public String getAuthority() {
    return name;
  }
}
