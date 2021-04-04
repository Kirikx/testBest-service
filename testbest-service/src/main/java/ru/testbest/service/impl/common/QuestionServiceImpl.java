package ru.testbest.service.impl.common;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.testbest.converter.impl.manage.QuestionFullConverter;
import ru.testbest.converter.impl.test.QuestionConverter;
import ru.testbest.dto.manage.ChapterWrapDto;
import ru.testbest.dto.manage.QuestionFullDto;
import ru.testbest.dto.test.QuestionDto;
import ru.testbest.persistence.dao.ChapterDao;
import ru.testbest.persistence.dao.QuestionDao;
import ru.testbest.persistence.entity.Chapter;
import ru.testbest.persistence.entity.Question;
import ru.testbest.service.QuestionService;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

  private final QuestionDao questionDao;
  private final ChapterDao chapterDao;
  private final QuestionConverter questionConverter;
  private final QuestionFullConverter questionFullConverter;

  @Override
  @Transactional(readOnly = true)
  public List<QuestionDto> getQuestions() {
    return questionDao.findAllByIsDeletedFalse().stream()
        .map(questionConverter::convertToDto)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public List<QuestionFullDto> getFullQuestions() {
    return questionDao.findAllByIsDeletedFalse().stream()
        .map(questionFullConverter::convertToDto)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public QuestionDto getQuestionById(UUID uuid) {
    return questionDao.findByIdAndIsDeletedFalse(uuid)
        .map(questionConverter::convertToDto)
        .orElse(null);
  }

  @Override
  @Transactional(readOnly = true)
  public QuestionFullDto getQuestionFullById(UUID uuid) {
    return questionDao.findByIdAndIsDeletedFalse(uuid)
        .map(questionFullConverter::convertToDto)
        .orElse(null);
  }

  @Override
  @Transactional
  public QuestionFullDto createQuestion(QuestionFullDto questionDto) {
    if (questionDto.getId() != null) {
      throw new RuntimeException();
    }
    Question question = questionDao.save(
        questionFullConverter.convertToEntity(questionDto));

    Set<ChapterWrapDto> questionChapters = Optional
        .ofNullable(questionDto.getChapters())
        .orElseThrow(() -> new RuntimeException("Question chapter is not null"));

    questionChapters.stream()
        .map(ChapterWrapDto::getId)
        .filter(Objects::nonNull)
        .map(chapterDao::findByIdAndIsDeletedFalse)
        .map(oChapter -> oChapter.orElse(null))
        .filter(Objects::nonNull)
        .peek(chapter -> chapter.addQuestion(question))
        .forEach(chapterDao::save);

    return getQuestionFullById(question.getId());
  }

  @Override
  @Transactional
  public QuestionFullDto editQuestion(QuestionFullDto questionDto) {
    Optional.ofNullable(questionDto.getId())
        .orElseThrow(RuntimeException::new);

    final Set<UUID> activeChaptersIds = questionDao.findByIdAndIsDeletedFalse(questionDto.getId())
        .orElseThrow(RuntimeException::new).getChapters().stream()
        .map(Chapter::getId)
        .collect(Collectors.toSet());

    final Set<UUID> currentChaptersIds;
    if (Objects.isNull(questionDto.getChapters())) {
      currentChaptersIds = new HashSet<>();
    } else {
      currentChaptersIds = questionDto.getChapters().stream()
          .map(ChapterWrapDto::getId)
          .collect(Collectors.toSet());
    }

    Question question = questionDao.save(
        questionFullConverter.convertToEntity(questionDto));

    if (!activeChaptersIds.isEmpty() || !currentChaptersIds.isEmpty()) {

      if (!activeChaptersIds.isEmpty()) {
        activeChaptersIds.stream()
            .filter(chId -> !currentChaptersIds.contains(chId))
            .map(chapterDao::findByIdAndIsDeletedFalse)
            .map(oChapter -> oChapter.orElse(null))
            .filter(Objects::nonNull)
            .peek(ch -> ch.removeQuestion(question))
            .forEach(chapterDao::save);
      }

      if (!currentChaptersIds.isEmpty()) {
        Set<UUID> skipChapters = activeChaptersIds.stream()
            .filter(currentChaptersIds::contains)
            .collect(Collectors.toSet());

        currentChaptersIds.stream()
            .filter(currCh -> !skipChapters.contains(currCh))
            .map(chapterDao::findByIdAndIsDeletedFalse)
            .map(oChapter -> oChapter.orElse(null))
            .filter(Objects::nonNull)
            .peek(chapter -> chapter.addQuestion(question))
            .forEach(chapterDao::save);
      }
    }

    return getQuestionFullById(question.getId());
  }

  @Override
  @Transactional
  public void deleteQuestionById(UUID uuid) {
    Optional<Question> oQuestion = questionDao.findByIdAndIsDeletedFalse(uuid);
    if (oQuestion.isPresent()) {
      Question question = oQuestion.get();
      question.setIsDeleted(true);
      questionDao.save(question);
    }
  }
}
