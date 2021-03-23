package ru.testbest.service.impl.common;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.testbest.converter.impl.test.ChapterConverter;
import ru.testbest.dto.common.TopicDto;
import ru.testbest.dto.test.ChapterDto;
import ru.testbest.persistence.dao.ChapterDao;
import ru.testbest.service.ChapterService;

@Service
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService {

  private final ChapterDao chapterDao;
  private final ChapterConverter chapterConverter;

  @Override
  public List<ChapterDto> getChapters() {
    return chapterDao.findAll().stream()
        .map(chapterConverter::convertToDto)
        .collect(Collectors.toList());
  }

  @Override
  public ChapterDto getChapterById(UUID uuid) {
    return chapterDao.findById(uuid)
        .map(chapterConverter::convertToDto)
        .orElse(null);
  }

  @Override
  public ChapterDto createChapter(ChapterDto topicDto) {
    return chapterConverter.convertToDto(
        chapterDao.save(
            chapterConverter.convertToEntity(topicDto)));
  }

  @Override
  public ChapterDto editChapter(ChapterDto topicDto) {
    return chapterConverter.convertToDto(
        chapterDao.save(
            chapterConverter.convertToEntity(topicDto)));
  }

  @Override
  public void deleteChapterById(UUID uuid) {
    chapterDao.deleteById(uuid);
  }
}


