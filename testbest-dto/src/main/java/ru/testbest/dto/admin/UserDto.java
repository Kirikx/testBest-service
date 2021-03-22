package ru.testbest.dto.admin;

import java.util.Set;

import lombok.Data;

@Data
public class UserDto {
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private Boolean isDeleted;
    private Set<RoleDto> roles;
}
