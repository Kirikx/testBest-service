package ru.testbest.service;

import java.util.List;
import java.util.UUID;
import ru.testbest.dto.admin.UserDto;

public interface UserService {

  List<UserDto> getUsers();

  UserDto getUserById(UUID uuid);

  UserDto createUser(UserDto userDto);

  UserDto editUser(UserDto userDto);

  void deleteUserById(UUID uuid);

  List<UserDto> getUsersByRoleId(UUID roleId);

  Boolean existEmailUser(String email);

  Boolean existNameUser(String username);
}
