package ru.testbest.service.impl.admin;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.testbest.converter.impl.admin.RoleConverter;
import ru.testbest.dto.admin.RoleDto;
import ru.testbest.persistence.dao.RoleDao;
import ru.testbest.service.RoleService;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

  private final RoleDao roleDao;
  private final RoleConverter roleConverter;

  @Override
  public List<RoleDto> getRoles() {
    return roleDao.findAll().stream()
        .map(roleConverter::convertToDto)
        .collect(Collectors.toList());
  }

  @Override
  public RoleDto getRoleById(String uuid) {
    return roleDao.findById(UUID.fromString(uuid))
        .map(roleConverter::convertToDto)
        .orElse(null);
  }
}
