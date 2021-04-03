package ru.testbest.converter.impl.manage;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.testbest.converter.impl.AbstractMapper;
import ru.testbest.dto.manage.ChapterFullDto;
import ru.testbest.persistence.dao.TestDao;
import ru.testbest.persistence.entity.Chapter;

@Component
public class ChapterFullConverter extends AbstractMapper<Chapter, ChapterFullDto> {

  private final ModelMapper mapper;
  private final TestDao testDao;

  @Autowired
  public ChapterFullConverter(
      ModelMapper mapper,
      TestDao testDao
  ) {
    super(Chapter.class, ChapterFullDto.class);
    this.mapper = mapper;
    this.testDao = testDao;
  }

  @PostConstruct
  public void setupMapper() {
    mapper.createTypeMap(Chapter.class, ChapterFullDto.class)
        .addMappings(m -> m.skip(ChapterFullDto::setTestId))
        .setPostConverter(toDtoConverter());
    mapper.createTypeMap(ChapterFullDto.class, Chapter.class)
        .addMappings(m -> m.skip(Chapter::setTest))
        .setPostConverter(toEntityConverter());
  }

  @Override
  public void mapSpecificFields(Chapter source, ChapterFullDto destination) {
    destination.setTestId(getTestId(source));
  }

  private UUID getTestId(Chapter source) {
    return Objects.isNull(source) || Objects.isNull(source.getTest()) ? null
        : source.getTest().getId();
  }

  @Override
  public void mapSpecificFields(ChapterFullDto source, Chapter destination) {
    Optional.ofNullable(source.getTestId()).ifPresent(id ->
        destination.setTest(testDao.findById(id).orElse(null))
    );
  }
}
