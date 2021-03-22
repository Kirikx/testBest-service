package ru.testbest.dto.admin;

import java.util.Set;
import java.util.UUID;

import lombok.Data;

@Data
public class UserDto {

  private UUID id;
  private String firstName;
  private String lastName;
  private String username;
  private String password;
  private String repeatPassword;
  private String phone;
  private String email;
  private Boolean isDeleted;
  private Set<RoleDto> roles;


}
