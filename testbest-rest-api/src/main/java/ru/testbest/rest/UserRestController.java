package ru.testbest.rest;

import org.springframework.web.bind.annotation.*;
import ru.testbest.dto.test.AnswerDto;
import ru.testbest.service.impl.common.AnswerServiceImpl;

import java.util.List;

@RestController
public class UserRestController {

    private final AnswerServiceImpl answerService;

    public UserRestController(AnswerServiceImpl answerService) {
        this.answerService = answerService;
    }

    @PostMapping("/answers/create")
    public AnswerDto createAnswer(){
        return null;
    }

    @PutMapping("/answers/{id}")
    public AnswerDto editAnswer(@PathVariable("id") Long id){
        return null;
    }

    @GetMapping("/answers")
    public List<AnswerDto> getListAnswers(){
        return null;
    }

    @GetMapping("/answers/{id}")
    public AnswerDto getAnswer(@PathVariable String id){
        return null;
    }
}
