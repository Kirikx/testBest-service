package ru.testbest.rest;

import org.springframework.web.bind.annotation.*;
import ru.testbest.dto.common.TopicDto;
import ru.testbest.service.impl.common.TopicServiceImpl;

import java.util.List;

@RestController
public class TopicRestController {

    private final TopicServiceImpl topicService;

    public TopicRestController(TopicServiceImpl topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/topics")
    public List<TopicDto> getTopics(){
        return topicService.getTopics();
    }

    @GetMapping("/topics/{id}")
    public TopicDto getTopic(@PathVariable("id") String id){
        return topicService.getTopicById(id);
    }

    @PutMapping("/topics/{id}")
    public TopicDto editTopics(@RequestBody TopicDto topicDto){
        return topicService.editTopic(topicDto);
    }

    @DeleteMapping("/topics/{id}")
    public void deleteTopics(@PathVariable("id") String id){
        topicService.deleteTopicById(id);
    }

    @PostMapping("/topics/create")
    public TopicDto createTopics(@RequestBody TopicDto topicDto){
        return topicService.createTopic(topicDto);
    }

}
