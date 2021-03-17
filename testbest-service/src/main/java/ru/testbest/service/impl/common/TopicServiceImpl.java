package ru.testbest.service.impl.common;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.testbest.converter.impl.common.TopicConverter;
import ru.testbest.dto.common.TopicDto;
import ru.testbest.persistence.dao.TopicDao;
import ru.testbest.persistence.entity.Topic;
import ru.testbest.service.TopicService;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

  private final TopicDao topicDao;
  private final TopicConverter topicConverter;

  @Override
  public List<TopicDto> getTopics() {
    return topicDao.findAll().stream()
        .map(topicConverter::convertToDto)
        .collect(Collectors.toList());
  }

  @Override
  public TopicDto getTopicById(String uuid) {
    Topic topic = topicDao.findById(uuid).orElseThrow(RuntimeException::new);
    return topicConverter.convertToDto(topic);
  }

  @Override
  public TopicDto createTopic(TopicDto topicDto) {
    return topicConverter.convertToDto(
        topicDao.save(
            topicConverter.convertToEntity(topicDto)));
  }

  @Override
  public TopicDto editTopic(TopicDto topicDto) {
    return topicConverter.convertToDto(
        topicDao.save(
            topicConverter.convertToEntity(topicDto)));
  }

  @Override
  public void deleteTopicById(String uuid) {
    topicDao.deleteById(uuid);
  }
}


