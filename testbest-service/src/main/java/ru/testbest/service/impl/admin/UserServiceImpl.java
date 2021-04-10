package ru.testbest.service.impl.admin;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.testbest.converter.impl.admin.UserConverter;
import ru.testbest.dto.admin.UserDetailsDto;
import ru.testbest.exception.custom.CustomBadRequest;
import ru.testbest.exception.custom.CustomNotFoundException;
import ru.testbest.persistence.dao.RoleDao;
import ru.testbest.persistence.dao.UserDao;
import ru.testbest.persistence.entity.Role;
import ru.testbest.persistence.entity.User;
import ru.testbest.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

  private final UserDao userDao;
  private final RoleDao roleDao;
  private final UserConverter userConverter;
  private final PasswordEncoder encoder;

  @Override
  @Transactional(readOnly = true)
  public List<UserDetailsDto> getUsers() {
    return userDao.findAllByIsDeletedFalse().stream()
        .map(userConverter::convertToDto)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public UserDetailsDto getUserById(UUID uuid) {
    return userDao.findByIdAndIsDeletedFalse(uuid)
        .map(userConverter::convertToDto)
        .orElseThrow(CustomNotFoundException::new);
  }

  @Override
  @Transactional
  public UserDetailsDto createUser(UserDetailsDto userDetailsDto) {
    if (userDetailsDto.getId() != null) {
      throw new CustomBadRequest();
    }
    Optional.ofNullable(userDetailsDto.getPassword())
        .orElseThrow(CustomBadRequest::new);
    userDetailsDto.setPassword(encoder.encode(userDetailsDto.getPassword()));
    return userConverter.convertToDto(
        userDao.save(
            userConverter.convertToEntity(userDetailsDto)));
  }

  @Override
  @Transactional
  public UserDetailsDto editUser(UserDetailsDto userDetailsDto) {
    Optional.ofNullable(userDetailsDto.getId())
        .orElseThrow(CustomBadRequest::new);
    User user = userDao.findByIdAndIsDeletedFalse(userDetailsDto.getId())
        .orElseThrow(CustomNotFoundException::new);
    userDetailsDto.setPassword(user.getPassword());
    return userConverter.convertToDto(
        userDao.save(
            userConverter.convertToEntity(userDetailsDto)));
  }

  @Override
  @Transactional
  public void deleteUserById(UUID uuid) {
    User user = userDao.findByIdAndIsDeletedFalse(uuid)
        .orElseThrow(CustomNotFoundException::new);

    user.setIsDeleted(true);
    userDao.save(user);
  }

  @Override
  @Transactional(readOnly = true)
  public List<UserDetailsDto> getUsersByRoleId(UUID roleId) {
    Role role = roleDao.findById(roleId)
        .orElseThrow(CustomNotFoundException::new);

    return userDao.findAllByRolesIsContaining(role).stream()
        .map(userConverter::convertToDto)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public Boolean existEmailUser(String email) {
    return userDao.existsByEmailAndIsDeletedFalse(email);
  }

  @Override
  @Transactional(readOnly = true)
  public Boolean existNameUser(String username) {
    return userDao.existsByUsernameAndIsDeletedFalse(username);
  }

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userDao.findByUsernameAndIsDeletedFalse(username)
        .orElseThrow(
            () -> new UsernameNotFoundException("User Not Found with username: " + username));
    return userConverter.convertToDto(user);
  }

  @Override
  @Transactional
  public UserDetailsDto updatePassword(UserDetailsDto userDetailsDto) {
    Optional.ofNullable(userDetailsDto.getId())
        .orElseThrow(CustomBadRequest::new);
    Optional.ofNullable(userDetailsDto.getPassword())
        .orElseThrow(CustomBadRequest::new);
    User user = userDao.findByIdAndIsDeletedFalse(userDetailsDto.getId())
        .orElseThrow(CustomNotFoundException::new);
    user.setPassword(encoder.encode(userDetailsDto.getPassword()));
    return userConverter.convertToDto(
        userDao.save(user)
    );
  }
}
