package ru.testbest.service;

import java.util.List;
import java.util.UUID;
import ru.testbest.dto.admin.UserDetailsDto;

public interface UserService {

  List<UserDetailsDto> getUsers();

  UserDetailsDto getUserById(UUID uuid);

  UserDetailsDto createUser(UserDetailsDto userDetailsDto);

  UserDetailsDto editUser(UserDetailsDto userDetailsDto);

  void deleteUserById(UUID uuid);

  List<UserDetailsDto> getUsersByRoleId(UUID roleId);

  Boolean existEmailUser(String email);

  Boolean existNameUser(String username);
}
