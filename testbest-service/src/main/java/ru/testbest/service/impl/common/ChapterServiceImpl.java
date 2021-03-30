package ru.testbest.service.impl.common;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.testbest.converter.impl.test.ChapterConverter;
import ru.testbest.dto.test.ChapterDto;
import ru.testbest.persistence.dao.ChapterDao;
import ru.testbest.service.ChapterService;

@Service
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService {

  private final ChapterDao chapterDao;
  private final ChapterConverter chapterConverter;

  @Override
  @Transactional(readOnly = true)
  public List<ChapterDto> getChapters() {
    return chapterDao.findAll().stream()
        .map(chapterConverter::convertToDto)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public ChapterDto getChapterById(UUID uuid) {
    return chapterDao.findById(uuid)
        .map(chapterConverter::convertToDto)
        .orElse(null);
  }

  @Override
  @Transactional
  public ChapterDto createChapter(ChapterDto chapterDto) {
    if (chapterDto.getId() != null) {
      throw new RuntimeException();
    }
    return chapterConverter.convertToDto(
        chapterDao.save(
            chapterConverter.convertToEntity(chapterDto)));
  }

  @Override
  @Transactional
  public ChapterDto editChapter(ChapterDto chapterDto) {
    Optional.ofNullable(chapterDto.getId())
        .orElseThrow(RuntimeException::new);
    return chapterConverter.convertToDto(
        chapterDao.save(
            chapterConverter.convertToEntity(chapterDto)));
  }

  @Override
  @Transactional
  public void deleteChapterById(UUID uuid) {
    chapterDao.deleteById(uuid);
  }
}


