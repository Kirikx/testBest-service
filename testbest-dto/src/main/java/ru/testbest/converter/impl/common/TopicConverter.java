package ru.testbest.converter.impl.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.testbest.converter.impl.AbstractMapper;
import ru.testbest.dto.common.TopicDto;
import ru.testbest.persistence.entity.Topic;

@Component
public class TopicConverter extends AbstractMapper<Topic, TopicDto> {

  @Autowired
  public TopicConverter() {
    super(Topic.class, TopicDto.class);
  }
}
