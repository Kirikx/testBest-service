package ru.testbest.rest;

import org.springframework.web.bind.annotation.*;
import ru.testbest.dto.Question;

import java.util.List;

@RestController
public class QuestionRestController {


    @GetMapping("/questions")
    public List<Question> getQuestions(){
        return null;
    }

    @GetMapping("/questions/{id}")
    public Question getTopicQuestion(@PathVariable("id") Long id){
        return null;
    }

    @PutMapping("/questions/{id}/edit")
    public Question topicEdit(@PathVariable("id") Long id){
        return null;
    }

    @DeleteMapping("/questions/{id}")
    public void questionDelete(@PathVariable("id") Long id){
    }

    @PostMapping("/questions/create")
    public Question questionCreate(){
        return new Question;
    }

}
