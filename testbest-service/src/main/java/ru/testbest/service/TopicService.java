package ru.testbest.service;

import java.util.List;
import ru.testbest.dto.common.TopicDto;

public interface TopicService {

  List<TopicDto> getTopics();

  TopicDto getTopicById(String uuid);

  TopicDto createTopic(TopicDto topicDto);

  TopicDto editTopic(TopicDto topicDto);

  void deleteTopicById(String uuid);
}
