package ru.testbest.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.testbest.dto.admin.UserDto;
import ru.testbest.service.impl.admin.UserServiceImpl;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AdminRestController {

    private final UserServiceImpl userService;

    @GetMapping("/users")
    public List<UserDto> getUsers(){
        log.info("Get all users");
        return userService.getUsers();
    }

    @GetMapping("/users/{id}")
    public UserDto getUser(@PathVariable("id") String id){
        log.info("Get by id users");
        return userService.getUserById(id);
    }

}
