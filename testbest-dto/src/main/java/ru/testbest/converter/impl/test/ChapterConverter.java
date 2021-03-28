package ru.testbest.converter.impl.test;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.testbest.converter.impl.AbstractMapper;
import ru.testbest.dto.test.ChapterDto;
import ru.testbest.persistence.dao.TestDao;
import ru.testbest.persistence.entity.Chapter;

@Component
public class ChapterConverter extends AbstractMapper<Chapter, ChapterDto> {

  private final ModelMapper mapper;
  private final TestDao testDao;

  @Autowired
  public ChapterConverter(
      ModelMapper mapper,
      TestDao testDao
  ) {
    super(Chapter.class, ChapterDto.class);
    this.mapper = mapper;
    this.testDao = testDao;
  }

  @PostConstruct
  public void setupMapper() {
    mapper.createTypeMap(Chapter.class, ChapterDto.class)
        .addMappings(m -> m.skip(ChapterDto::setTestId))
        .setPostConverter(toDtoConverter());
    mapper.createTypeMap(ChapterDto.class, Chapter.class)
        .addMappings(m -> m.skip(Chapter::setTest))
        .setPostConverter(toEntityConverter());
  }

  @Override
  public void mapSpecificFields(Chapter source, ChapterDto destination) {
    destination.setTestId(getTestId(source));
  }

  private UUID getTestId(Chapter source) {
    return Objects.isNull(source) || Objects.isNull(source.getTest()) ? null
        : source.getTest().getId();
  }

  @Override
  public void mapSpecificFields(ChapterDto source, Chapter destination) {
    Optional.ofNullable(source.getTestId()).ifPresent(id ->
        destination.setTest(testDao.findById(id).orElse(null))
    );
  }
}
