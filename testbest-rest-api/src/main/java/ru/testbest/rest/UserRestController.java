package ru.testbest.rest;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.testbest.dto.admin.UserDto;
import ru.testbest.service.impl.admin.UserServiceImpl;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserRestController {

  private final UserServiceImpl userService;

  @GetMapping
  public List<UserDto> getUsers() {
    log.info("Get all users");
    return userService.getUsers();
  }

  @GetMapping("/role/{id}")
  public List<UserDto> getUsersByRoleId(@PathVariable String id) {
    log.info("Get all users by roleId {}", id);
    return userService.getUsersByRoleId(UUID.fromString(id));
  }

  @GetMapping("/{id}")
  public UserDto getUser(@PathVariable("id") String id) {
    log.info("Get user by id {}", id);
    return userService.getUserById(UUID.fromString(id));
  }

}
