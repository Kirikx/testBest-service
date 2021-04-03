package ru.testbest.converter.impl.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.testbest.converter.impl.AbstractMapper;
import ru.testbest.dto.admin.RoleDto;
import ru.testbest.persistence.entity.Role;

@Component
public class RoleConverter extends AbstractMapper<Role, RoleDto> {

  @Autowired
  public RoleConverter() {
    super(Role.class, RoleDto.class);
  }
}
