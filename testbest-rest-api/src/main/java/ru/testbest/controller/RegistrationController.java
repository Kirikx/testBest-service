package ru.testbest.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.testbest.dto.admin.UserDto;
import ru.testbest.service.UserService;


@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(UserDto user, Map<String, Object> model) {

        if (userService.getUserById(user.getId().toString()) != null) {
            model.put("message", "User exists!");
            return "registration";
        }
        return "redirect:/login";
    }
}

