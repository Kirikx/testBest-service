package ru.testbest.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.testbest.dto.test.QuestionDto;
import ru.testbest.service.impl.common.QuestionServiceImpl;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class QuestionRestController {

    private final QuestionServiceImpl questionService;

    @GetMapping("/questions")
    public List<QuestionDto> getQuestions(){
        log.info("Get all question");
        return questionService.getQuestions();
    }

    @GetMapping("/questions/{id}")
    public QuestionDto getQuestion(@PathVariable("id") String id){
        log.info("Get by id question");
        return questionService.getQuestionById(id);
    }

    @PutMapping("/questions/{id}")
    public QuestionDto editQuestion(@RequestBody QuestionDto questionDto){
        log.info("Edit by id question");
        return questionService.editQuestion(questionDto);
    }

    @DeleteMapping("/questions/{id}")
    public void deleteQuestion(@PathVariable("id") String id){
        log.info("Delete by id question");
        questionService.deleteQuestionById(id);
    }

    @PostMapping("/questions/create")
    public QuestionDto createQuestion(@RequestBody QuestionDto questionDto){
        log.info("Create question");
        return questionService.createQuestion(questionDto);
    }

}
