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
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/topics")
public class TopicRestController {

    private final TopicServiceImpl topicService;

    @GetMapping("/")
    public List<TopicDto> getTopics() {
        log.info("Get all topics");
        return topicService.getTopics();
    }

    @GetMapping("/{id}")
    public TopicDto getTopic(@PathVariable("id") String id) {
        log.info("Get topic by id {}", id);
        return topicService.getTopicById(UUID.fromString(id));
    }

    @PutMapping("/")
    public TopicDto editTopics(@RequestBody TopicDto topicDto) {
        log.info("Edit topic {}", topicDto);
        return topicService.editTopic(topicDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTopics(@PathVariable("id") String id) {
        log.info("Delete topic by id {}", id);
        topicService.deleteTopicById(UUID.fromString(id));
    }

    @PostMapping("/create")
    public TopicDto createTopics(@RequestBody TopicDto topicDto) {
        log.info("Create topic {}", topicDto);
        return topicService.createTopic(topicDto);
    }

}
