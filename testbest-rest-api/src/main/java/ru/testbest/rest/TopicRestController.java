package ru.testbest.rest;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.testbest.dto.common.TopicDto;
import ru.testbest.service.impl.common.TopicServiceImpl;

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
        log.info("Get topic by id {}", id);
        return topicService.getTopicById(id);
    }

    @PutMapping("/topics")
    public TopicDto editTopics(@RequestBody TopicDto topicDto){
        log.info("Edit topic {}", topicDto);
        return topicService.editTopic(topicDto);
    }

    @DeleteMapping("/topics/{id}")
    public void deleteTopics(@PathVariable("id") String id){
        log.info("Delete topic by id {}", id);
        topicService.deleteTopicById(id);
    }

    @PostMapping("/topics/create")
    public TopicDto createTopics(@RequestBody TopicDto topicDto){
        log.info("Create topic {}", topicDto);
        return topicService.createTopic(topicDto);
    }

}
