package ru.testbest.service;

import java.util.List;
import java.util.UUID;
import ru.testbest.dto.manage.ChapterFullDto;
import ru.testbest.dto.test.ChapterDto;

public interface ChapterService {

  List<ChapterDto> getChapters();

  List<ChapterFullDto> getFullChapters();

  ChapterDto getChapterById(UUID uuid);

  ChapterFullDto getChapterFullById(UUID uuid);

  ChapterFullDto createChapter(ChapterFullDto topicDto);

  ChapterFullDto editChapter(ChapterFullDto topicDto);

  void deleteChapterById(UUID uuid);
}
