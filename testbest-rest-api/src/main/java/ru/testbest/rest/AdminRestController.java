package ru.testbest.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminRestController {

    //Подключенные сервисы

    @GetMapping("/topics")
    public String getTopics(){
        return tipicRepository.findAll();
    }

    @GetMapping("/topic")
    public String getTopic(@PathVariable("id") Long id){
        return tipicRepository.findById(id);
    }

    @PutMapping ("/topic/{id}/edit")
    public String topicEdit(@PathVariable("id") Long id){
        return new Topic;
    }

    @DeleteMapping("/topic/{id}")
    public String topicDelete(@PathVariable("id") Long id){
        return tipicRepository.DeleteById(id);
    }

    @PostMapping("/topic/create")
    public Topic topicCreate(){
        return new Topic;
    }

    @GetMapping("/topic/{id}/questions")
    public String getTopicQuestions(){
        return questionRepository.findAll();
    }

    @GetMapping("/topic/{id}/question/{id}")
    public String getTopicQuestion(@PathVariable("id") Long id){
        return questionRepository.findById(id);
    }

    @PutMapping ("/topic/{id}/question/{id}/edit")
    public String topicEdit(@PathVariable("id") Long id){
        return questionRepository.findById(id);
    }

    @DeleteMapping("/topic/question/{id}")
    public String questionDelete(@PathVariable("id") Long id){
        return questionRepository.DeleteById(id);
    }

    @PostMapping("/topic/{id}/question/create")
    public Question questionCreate(){
        return new Question;
    }

    @GetMapping("/allusers")
    public String getUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public String getUser(@PathVariable("id") Long id){
        return userRepository.findById(id);
    }
}
