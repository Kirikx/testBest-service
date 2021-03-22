package ru.testbest.service;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import ru.testbest.dto.admin.RoleDto;

public interface RoleService {

  List<RoleDto> getRoles();

  RoleDto getRoleById(UUID uuid);

  Optional<RoleDto> getRoleByName(String name);
}
