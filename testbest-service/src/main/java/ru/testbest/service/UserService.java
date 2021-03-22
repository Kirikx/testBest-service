package ru.testbest.service;

import java.util.List;
import ru.testbest.dto.admin.UserDto;

public interface UserService {

  List<UserDto> getUsers();

  UserDto getUserById(String uuid);

  UserDto createUser(UserDto userDto);

  UserDto editUser(UserDto userDto);

  void deleteUserById(String uuid);

  List<UserDto> getUsersByRoleId(String roleId);

  Boolean existEmailUser(String email);

  Boolean existNameUser(String username);
}
