package ru.testbest.converter.impl.admin;

import org.springframework.stereotype.Component;
import ru.testbest.converter.ConverterTest;
import ru.testbest.dto.admin.RoleDto;
import ru.testbest.persistence.entity.Role;

@Component
public class RoleConverter implements ConverterTest<Role, RoleDto> {

  @Override
  public RoleDto convertToDto(Role entity) {
    RoleDto roleDto = new RoleDto();
    roleDto.setId(entity.getId());
    roleDto.setName(entity.getName());
    return roleDto;
  }

  @Override
  public Role convertToEntity(RoleDto dto) {
    Role role = new Role();
    role.setId(dto.getId());
    role.setName(dto.getName());
    return role;
  }
}
