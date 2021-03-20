package ru.testbest.rest;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminRestController {

    //Подключенные сервисы

    @GetMapping("/users/all")
    public List<User> getUsers(){
        return null;
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") Long id){
        return null;
    }

}
