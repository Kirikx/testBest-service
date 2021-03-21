package ru.testbest.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.testbest.dto.common.TopicDto;
import ru.testbest.service.impl.common.TopicServiceImpl;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TopicRestController {

    private final TopicServiceImpl topicService;

    @GetMapping("/topics")
    public List<TopicDto> getTopics(){
        log.info("Get all topics");
        return topicService.getTopics();
    }

    @GetMapping("/topics/{id}")
    public TopicDto getTopic(@PathVariable("id") String id){
        log.info("Get by id topic");
        return topicService.getTopicById(id);
    }

    @PutMapping("/topics/{id}")
    public TopicDto editTopics(@RequestBody TopicDto topicDto){
        log.info("Edit by id topic");
        return topicService.editTopic(topicDto);
    }

    @DeleteMapping("/topics/{id}")
    public void deleteTopics(@PathVariable("id") String id){
        log.info("Delete by id topic");
        topicService.deleteTopicById(id);
    }

    @PostMapping("/topics/create")
    public TopicDto createTopics(@RequestBody TopicDto topicDto){
        log.info("Create topic");
        return topicService.createTopic(topicDto);
    }

}
