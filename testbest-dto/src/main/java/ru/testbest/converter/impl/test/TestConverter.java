package ru.testbest.converter.impl.test;

import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.testbest.converter.ConverterTest;
import ru.testbest.dto.test.TestDto;
import ru.testbest.persistence.dao.TopicDao;
import ru.testbest.persistence.dao.UserDao;
import ru.testbest.persistence.entity.Test;

@Component
@RequiredArgsConstructor
public class TestConverter implements ConverterTest<Test, TestDto> {

  private final TopicDao topicDao;
  private final UserDao userDao;
  private final ChapterConverter chapterConverter;

  @Override
  public TestDto convertToDto(Test entity) {
    TestDto testDto = new TestDto();
    testDto.setId(entity.getId());
    testDto.setName(entity.getName());
    testDto.setDescription(entity.getDescription());
    testDto.setCreated(entity.getCreated());
    testDto.setDuration(entity.getDuration());
    testDto.setIsDeleted(entity.getIsDeleted());
    testDto.setTopicId(entity.getTopic() == null ? null : entity.getTopic().getId());
    testDto.setAuthorId(entity.getAuthor() == null ? null : entity.getAuthor().getId());
    testDto.setChapters(entity.getChapters().stream()
        .map(chapterConverter::convertToDto)
        .collect(Collectors.toSet()));
    return testDto;
  }

  @Override
  public Test convertToEntity(TestDto dto) {
    Test test = new Test();
    Optional.ofNullable(dto.getId())
        .ifPresent(test::setId);
    test.setName(dto.getName());
    test.setDescription(dto.getDescription());
    test.setCreated(dto.getCreated());
    test.setDuration(dto.getDuration());
    test.setIsDeleted(dto.getIsDeleted());
    test.setTopic(
        dto.getTopicId() != null ?
            topicDao.findById(dto.getTopicId()).orElse(null) : null);
    test.setAuthor(
        dto.getAuthorId() != null ?
            userDao.findById(dto.getAuthorId()).orElse(null) : null);
    test.setChapters(dto.getChapters().stream()
        .map(chapterConverter::convertToEntity)
        .collect(Collectors.toSet()));
    return test;
  }
}
