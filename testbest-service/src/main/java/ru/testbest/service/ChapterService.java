package ru.testbest.service;

import java.util.List;
import java.util.UUID;
import ru.testbest.dto.common.TopicDto;
import ru.testbest.dto.test.ChapterDto;

public interface ChapterService {

  List<ChapterDto> getChapters();

  ChapterDto getChapterById(UUID uuid);

  ChapterDto createChapter(ChapterDto topicDto);

  ChapterDto editChapter(ChapterDto topicDto);

  void deleteChapterById(UUID uuid);
}
