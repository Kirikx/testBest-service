package ru.testbest.service.impl.common;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.testbest.converter.impl.manage.ChapterFullConverter;
import ru.testbest.converter.impl.test.ChapterConverter;
import ru.testbest.dto.manage.ChapterFullDto;
import ru.testbest.dto.test.ChapterDto;
import ru.testbest.persistence.dao.ChapterDao;
import ru.testbest.persistence.entity.Chapter;
import ru.testbest.service.ChapterService;

@Service
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService {

  private final ChapterDao chapterDao;
  private final ChapterConverter chapterConverter;
  private final ChapterFullConverter chapterFullConverter;

  @Override
  @Transactional(readOnly = true)
  public List<ChapterDto> getChapters() {
    return chapterDao.findAll().stream()
      .map(chapterConverter::convertToDto)
      .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public List<ChapterFullDto> getFullChapters() {
    return chapterDao.findAll().stream()
      .map(chapterFullConverter::convertToDto)
      .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public ChapterDto getChapterById(UUID uuid) {
    return chapterDao.findByIdAndIsDeletedFalse(uuid)
      .map(chapterConverter::convertToDto)
      .orElse(null);
  }

  @Override
  @Transactional(readOnly = true)
  public ChapterFullDto getChapterFullById(UUID uuid) {
    return chapterDao.findById(uuid)
      .map(chapterFullConverter::convertToDto)
      .orElse(null);
  }

  @Override
  @Transactional
  public ChapterFullDto createChapter(ChapterFullDto chapterDto) {
    if (chapterDto.getId() != null) {
      throw new RuntimeException();
    }
    return chapterFullConverter.convertToDto(
      chapterDao.save(
        chapterFullConverter.convertToEntity(chapterDto)));
  }

  @Override
  @Transactional
  public ChapterFullDto editChapter(ChapterFullDto chapterDto) {
    Optional.ofNullable(chapterDto.getId())
      .orElseThrow(RuntimeException::new);
    return chapterFullConverter.convertToDto(
      chapterDao.save(
        chapterFullConverter.convertToEntity(chapterDto)));
  }

  @Override
  @Transactional
  public void deleteChapterById(UUID uuid) {
    Optional<Chapter> oChapter = chapterDao.findByIdAndIsDeletedFalse(uuid);
    if (oChapter.isPresent()) {
      Chapter chapter = oChapter.get();
      chapter.setIsDeleted(true);
      chapterDao.save(chapter);
    }
  }
}


