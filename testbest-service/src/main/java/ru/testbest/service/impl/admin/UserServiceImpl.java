package ru.testbest.service.impl.admin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
  public List<UserDto> getUsers() {
    return userDao.findAll().stream()
        .map(userConverter::convertToDto)
        .collect(Collectors.toList());
  }

  @Override
  public UserDto getUserById(String uuid) {
    User user = userDao.findById(uuid).orElseThrow(RuntimeException::new);
    return userConverter.convertToDto(user);
  }

  @Override
  public UserDto createUser(UserDto userDto) {
    return userConverter.convertToDto(
        userDao.save(
            userConverter.convertToEntity(userDto)));
  }

  @Override
  public UserDto editUser(UserDto userDto) {
    return userConverter.convertToDto(
        userDao.save(
            userConverter.convertToEntity(userDto)));
  }

  @Override
  public void deleteUserById(String uuid) {
    Optional<User> oUser = userDao.findById(uuid);
    if (oUser.isPresent()) {
      User user = oUser.get();
      user.setIsDeleted(true);
      userDao.save(user);
    }
  }

  @Override
  public List<UserDto> getUsersByRoleId(String roleId) {
//    return userDao.findByRoleId(roleId).stream()
//        .map(userConverter::convertToDto)
//        .collect(Collectors.toList());
    return null;
  }
}
