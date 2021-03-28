package ru.testbest.converter.impl.test;

import java.util.Objects;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.testbest.converter.AbstractMapper;
import ru.testbest.dto.test.TestDto;
import ru.testbest.persistence.dao.TopicDao;
import ru.testbest.persistence.dao.UserDao;
import ru.testbest.persistence.entity.Test;

@Component
public class TestConverter extends AbstractMapper<Test, TestDto> {

  private final ModelMapper mapper;
  private final TopicDao topicDao;
  private final UserDao userDao;

  public TestConverter(
      ModelMapper mapper,
      TopicDao topicDao,
      UserDao userDao
  ) {
    super(Test.class, TestDto.class);
    this.mapper = mapper;
    this.topicDao = topicDao;
    this.userDao = userDao;
  }

  @PostConstruct
  public void setupMapper() {
    mapper.createTypeMap(Test.class, TestDto.class)
        .addMappings(m -> m.skip(TestDto::setAuthorId))
        .addMappings(m -> m.skip(TestDto::setAuthorId))
        .setPostConverter(toDtoConverter());
    mapper.createTypeMap(TestDto.class, Test.class)
        .addMappings(m -> m.skip(Test::setAuthor))
        .addMappings(m -> m.skip(Test::setTopic))
        .setPostConverter(toEntityConverter());
  }

  @Override
  public void mapSpecificFields(Test source, TestDto destination) {
    destination.setAuthorId(getAuthorId(source));
    destination.setTopicId(getTopicId(source));
  }

  private UUID getAuthorId(Test source) {
    return Objects.isNull(source) || Objects.isNull(source.getAuthor()) ? null
        : source.getAuthor().getId();
  }

  private UUID getTopicId(Test source) {
    return Objects.isNull(source) || Objects.isNull(source.getTopic()) ? null
        : source.getTopic().getId();
  }

  @Override
  public void mapSpecificFields(TestDto source, Test destination) {
    destination.setAuthor(userDao.findById(source.getAuthorId()).orElse(null));
    destination.setTopic(topicDao.findById(source.getTopicId()).orElse(null));
  }

}
