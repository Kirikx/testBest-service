package ru.testbest.converter.impl.test;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.testbest.converter.impl.AbstractMapper;
import ru.testbest.dto.test.ChapterDto;
import ru.testbest.dto.test.TestDto;
import ru.testbest.persistence.entity.Test;

@Component
public class TestConverter extends AbstractMapper<Test, TestDto> {

  private final ModelMapper mapper;
  private final ChapterConverter chapterConverter;

  @Autowired
  public TestConverter(
      ModelMapper mapper,
      ChapterConverter chapterConverter
  ) {
    super(Test.class, TestDto.class);
    this.mapper = mapper;
    this.chapterConverter = chapterConverter;
  }

  @PostConstruct
  public void setupMapper() {
    mapper.createTypeMap(Test.class, TestDto.class)
        .addMappings(m -> m.skip(TestDto::setAuthorId))
        .addMappings(m -> m.skip(TestDto::setTopicId))
        .addMappings(m -> m.skip(TestDto::setChapters))
        .setPostConverter(toDtoConverter());
  }

  @Override
  public void mapSpecificFields(Test source, TestDto destination) {
    destination.setAuthorId(getAuthorId(source));
    destination.setTopicId(getTopicId(source));
    destination.setChapters(getChaptersIsNotDelete(source));
  }

  private UUID getAuthorId(Test source) {
    return Objects.isNull(source) || Objects.isNull(source.getAuthor()) ? null
        : source.getAuthor().getId();
  }

  private UUID getTopicId(Test source) {
    return Objects.isNull(source) || Objects.isNull(source.getTopic()) ? null
        : source.getTopic().getId();
  }

  private Set<ChapterDto> getChaptersIsNotDelete(Test source) {
    return Objects.isNull(source) || Objects.isNull(source.getChapters()) ? null
        : source.getChapters().stream()
            .filter(ch -> !ch.getIsDeleted())
            .map(chapterConverter::convertToDto)
            .collect(Collectors.toSet());
  }

  @Override
  public Test convertToEntity(TestDto dto) {
    throw new UnsupportedOperationException("This converter not supported convertToEntity");
  }
}
