package ru.testbest.rest;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TopicRestController {

    @GetMapping("/topics")
    public List<Topic> getTopics(){
        return null;
    }

    @GetMapping("/topics/{id}")
    public Topic getTopic(@PathVariable("id") Long id){
        return null;
    }

    @PutMapping("/topics/{id}/edit")
    public Topic topicEdit(@PathVariable("id") Long id){
        return null;
    }

    @DeleteMapping("/topics/{id}")
    public void topicDelete(@PathVariable("id") Long id){
    }

    @PostMapping("/topics/create")
    public Topic topicCreate(){
        return null;
    }

}
