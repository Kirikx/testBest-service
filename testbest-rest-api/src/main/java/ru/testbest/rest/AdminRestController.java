package ru.testbest.rest;

import org.springframework.web.bind.annotation.*;
import ru.testbest.dto.admin.UserDto;
import ru.testbest.service.impl.admin.UserServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
public class AdminRestController {

    private final UserServiceImpl userService;

    public AdminRestController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/users/all")
    public List<UserDto> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/users/{id}")
    public UserDto getUser(@PathVariable("id") String id){
        return userService.getUserById(id);
    }

}
