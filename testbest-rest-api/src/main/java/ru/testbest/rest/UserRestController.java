package ru.testbest.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

    @GetMapping("/topics")
    public String getTopics(){
        return topicRepository.findAll();
    }

    @GetMapping("/topic/{id}")
    public String getTopic(@PathVariable("id") Long id){
        return topicRepository.findById(id);
    }

    @GetMapping("/topic/{id}/question/{id}")
    public String getTopicQuestion(@PathVariable("id") Long id){
        return questionRepository.findById(id);
    }

    @GetMapping("/topic/{id}/questions")
    public String getTopicQuestions(){
        return questionRepository.findAll();
    }

    @GetMapping("/topic/{id}/question/{id}/answer/{id}")
    public String getTopicQuestionAnswer(@PathVariable("id") Long id){
        return answerRepository.findById(id);
    }

    @PostMapping("/topic/{id}/question/{id}/answer/{id}/edit")
    public String getTopicQuestionAnswerEdit(@PathVariable("id") Long id){
        return answerRepository.findById(id);
    }

    @GetMapping("/answers")
    public String getAnswers(){
        return answerRepository.findAll();
    }
}
