package ru.testbest.service.impl.admin;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
  @Transactional(readOnly = true)
  public List<RoleDto> getRoles() {
    return roleDao.findAll().stream()
        .map(roleConverter::convertToDto)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public RoleDto getRoleById(UUID uuid) {
    return roleDao.findById(uuid)
        .map(roleConverter::convertToDto)
        .orElse(null);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<RoleDto> getRoleByName(String name) {
    return roleDao.findByName(name)
            .map(roleConverter::convertToDto);
  }
}
