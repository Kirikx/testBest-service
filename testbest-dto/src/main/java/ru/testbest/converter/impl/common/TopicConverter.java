package ru.testbest.converter.impl.common;

import org.springframework.stereotype.Component;
import ru.testbest.converter.ConverterTest;
import ru.testbest.dto.common.TopicDto;
import ru.testbest.persistence.entity.Topic;

@Component
public class TopicConverter implements ConverterTest<Topic, TopicDto> {


  @Override
  public TopicDto convertToDto(Topic entity) {
    TopicDto topicDto = new TopicDto();
    topicDto.setId(entity.getId());
    topicDto.setName(entity.getName());
    topicDto.setDescription(entity.getDescription());
    return topicDto;
  }

  @Override
  public Topic convertToEntity(TopicDto dto) {
    Topic topic = new Topic();
    topic.setId(dto.getId());
    topic.setName(dto.getName());
    topic.setDescription(dto.getDescription());
    return topic;
  }
}
