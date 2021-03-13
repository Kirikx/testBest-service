package ru.testbest.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

    @GetMapping("/topic")
    public String getTipic(){
        return tipicRepository.findAll();
    }

    @GetMapping("/topic/{id}/question")
    public String getTipicQuestion(){
        return questionRepository.findAll();
    }
}
