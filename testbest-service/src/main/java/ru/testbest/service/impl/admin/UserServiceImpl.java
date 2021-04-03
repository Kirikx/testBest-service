package ru.testbest.service.impl.admin;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.testbest.converter.impl.admin.UserConverter;
import ru.testbest.dto.admin.UserDto;
import ru.testbest.dto.admin.security.UserDetailsImpl;
import ru.testbest.persistence.dao.UserDao;
import ru.testbest.persistence.entity.User;
import ru.testbest.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

  private final UserDao userDao;
  private final UserConverter userConverter;

  @Override
  @Transactional(readOnly = true)
  public List<UserDto> getUsers() {
    return userDao.findAllByIsDeletedFalse().stream()
        .map(userConverter::convertToDto)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public UserDto getUserById(UUID uuid) {
    return userDao.findByIdAndIsDeletedFalse(uuid)
        .map(userConverter::convertToDto)
        .orElse(null);
  }

  @Override
  @Transactional
  public UserDto createUser(UserDto userDto) {
    if (userDto.getId() != null) {
      throw new RuntimeException();
    }
    return userConverter.convertToDto(
        userDao.save(
            userConverter.convertToEntity(userDto)));
  }

  @Override
  @Transactional
  public UserDto editUser(UserDto userDto) {
    return userConverter.convertToDto(
        userDao.save(
            userConverter.convertToEntity(userDto)));
  }

  @Override
  @Transactional
  public void deleteUserById(UUID uuid) {
    Optional<User> oUser = userDao.findByIdAndIsDeletedFalse(uuid);
    if (oUser.isPresent()) {
      User user = oUser.get();
      user.setIsDeleted(true);
      userDao.save(user);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public List<UserDto> getUsersByRoleId(UUID roleId) {
//    return userDao.findByRoleId(roleId).stream()
//        .map(userConverter::convertToDto)
//        .collect(Collectors.toList());
    return null;
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
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    return UserDetailsImpl.build(user);
  }
}
