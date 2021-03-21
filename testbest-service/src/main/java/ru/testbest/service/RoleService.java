package ru.testbest.service;

import java.util.List;
import java.util.UUID;

import ru.testbest.dto.admin.RoleDto;

public interface RoleService {

  List<RoleDto> getRoles();

  RoleDto getRoleById(String uuid);
}
