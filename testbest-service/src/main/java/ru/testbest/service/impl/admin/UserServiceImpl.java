package ru.testbest.service.impl.admin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.testbest.converter.impl.admin.UserConverter;
import ru.testbest.dto.admin.UserDto;
import ru.testbest.persistence.dao.UserDao;
import ru.testbest.persistence.entity.User;
import ru.testbest.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

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
  public UserDto getUserById(String uuid) {
    return userDao.findByIdAndIsDeletedFalse(uuid)
        .map(userConverter::convertToDto)
        .orElse(null);
  }

  @Override
  @Transactional
  public UserDto createUser(UserDto userDto) {
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
  public void deleteUserById(String uuid) {
    Optional<User> oUser = userDao.findByIdAndIsDeletedFalse(uuid);
    if (oUser.isPresent()) {
      User user = oUser.get();
      user.setIsDeleted(true);
      userDao.save(user);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public List<UserDto> getUsersByRoleId(String roleId) {
//    return userDao.findByRoleId(roleId).stream()
//        .map(userConverter::convertToDto)
//        .collect(Collectors.toList());
    return null;
  }

  @Override
  public Boolean existEmailUser(String email) {
    return userDao.existsByEmailAndIsDeletedFalse(email);
  }

  @Override
  public Boolean existNameUser(String username) {
    return userDao.existsByUsernameAndIsDeletedFalse(username);
  }
}
