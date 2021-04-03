package ru.testbest.converter.impl.manage;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.testbest.converter.impl.AbstractMapper;
import ru.testbest.dto.manage.ChapterWrapDto;
import ru.testbest.persistence.dao.ChapterDao;
import ru.testbest.persistence.entity.Chapter;

@Component
public class ChapterWrapConverter extends AbstractMapper<Chapter, ChapterWrapDto> {

  private final ChapterDao chapterDao;

  @Autowired
  public ChapterWrapConverter(
      ChapterDao chapterDao
  ) {
    super(Chapter.class, ChapterWrapDto.class);
    this.chapterDao = chapterDao;
  }

  @Override
  public Chapter convertToEntity(ChapterWrapDto dto) {
    return Optional.ofNullable(dto.getId()).flatMap(chapterDao::findById)
        .orElse(null);
  }
}
