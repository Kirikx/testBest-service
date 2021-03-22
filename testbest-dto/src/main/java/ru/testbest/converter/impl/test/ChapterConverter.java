package ru.testbest.converter.impl.test;

import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.testbest.converter.ConverterTest;
import ru.testbest.dto.test.ChapterDto;
import ru.testbest.persistence.dao.TestDao;
import ru.testbest.persistence.entity.Chapter;

@Component
@RequiredArgsConstructor
public class ChapterConverter implements ConverterTest<Chapter, ChapterDto> {

  private final TestDao testDao;
  private final QuestionConverter questionConverter;

  @Override
  public ChapterDto convertToDto(Chapter entity) {
    ChapterDto chapterDto = new ChapterDto();
    chapterDto.setId(entity.getId());
    chapterDto.setName(entity.getName());
    chapterDto.setDescription(entity.getDescription());
    chapterDto.setIsDeleted(entity.getIsDeleted());
    chapterDto.setTestId(entity.getTest() == null ? null : entity.getTest().getId());
    chapterDto.setQuestions(entity.getQuestions().stream()
        .map(questionConverter::convertToDto)
        .collect(Collectors.toSet()));
    return chapterDto;

  }

  @Override
  public Chapter convertToEntity(ChapterDto dto) {
    Chapter chapter = new Chapter();
    Optional.ofNullable(dto.getId())
        .ifPresent(chapter::setId);
    chapter.setName(dto.getName());
    chapter.setDescription(dto.getDescription());
    chapter.setIsDeleted(dto.getIsDeleted());
    chapter.setTest(testDao.findById(dto.getTestId()).orElse(null));
    chapter.setQuestions(dto.getQuestions().stream()
        .map(questionConverter::convertToEntity)
        .collect(Collectors.toSet()));
    return chapter;

  }
}
