package ru.testbest.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminRestController {

    //Подключенные сервисы

    @GetMapping("/topic")
    public String getTopic(){
        return tipicRepository.findAll();
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

    @GetMapping("/topic/{id}/question")
    public String getTipicQuestion(){
        return questionRepository.findAll();
    }

    @PutMapping ("/topic/{id}/question/{id}/edit")
    public String topicEdit(@PathVariable("id") Long id){
        return questionRepository.findById(id);
    }

    @DeleteMapping("/topic/question/{id}")
    public String questionDelete(@PathVariable("id") Long id){
        return questionRepository.DeleteById(id);
    }

    @PostMapping("/topic/{id}/question/{id}/create")
    public Question questionCreate(){
        return new Question;
    }

    @GetMapping("allusers")
    public String getUsers(){
        return userRepository.findAll();
    }

    @GetMapping("users/{id}")
    public String getUser(@PathVariable("id") Long id){
        return userRepository.findById(id);
    }
}
