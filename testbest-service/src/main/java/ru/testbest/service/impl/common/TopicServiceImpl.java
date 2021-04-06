package ru.testbest.service.impl.common;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.testbest.converter.impl.common.TopicConverter;
import ru.testbest.dto.common.TopicDto;
import ru.testbest.exception.custom.CustomBadRequest;
import ru.testbest.exception.custom.CustomNotFoundException;
import ru.testbest.persistence.dao.TopicDao;
import ru.testbest.service.TopicService;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

  private final TopicDao topicDao;
  private final TopicConverter topicConverter;

  @Override
  @Transactional(readOnly = true)
  public List<TopicDto> getTopics() {
    return topicDao.findAll().stream()
        .map(topicConverter::convertToDto)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public TopicDto getTopicById(UUID uuid) {
    return topicDao.findById(uuid)
        .map(topicConverter::convertToDto)
        .orElseThrow(CustomNotFoundException::new);
  }

  @Override
  @Transactional
  public TopicDto createTopic(TopicDto topicDto) {
    if (topicDto.getId() != null) {
      throw new CustomBadRequest();
    }
    return topicConverter.convertToDto(
        topicDao.save(
            topicConverter.convertToEntity(topicDto)));
  }

  @Override
  @Transactional
  public TopicDto editTopic(TopicDto topicDto) {
    Optional.ofNullable(topicDto.getId())
        .orElseThrow(CustomBadRequest::new);
    return topicConverter.convertToDto(
        topicDao.save(
            topicConverter.convertToEntity(topicDto)));
  }

  @Override
  @Transactional
  public void deleteTopicById(UUID uuid) {
    topicDao.deleteById(uuid);
  }
}


