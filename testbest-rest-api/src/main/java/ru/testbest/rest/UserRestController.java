package ru.testbest.rest;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserRestController {


    @PostMapping("/answers/create")
    public Answer createAnswer(){
        return null;
    }

    @PutMapping("/answers/{id}/edit")
    public String editAnswerEdit(@PathVariable("id") Long id){
        return null;
    }

    @GetMapping("/answers")
    public List<Anser> getListAnswers(){
        return null;
    }

    @GetMapping("/answers/{id}")
    public Anser getAnswer(@PathVariable String id){
        return null;
    }
}
