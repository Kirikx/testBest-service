package ru.testbest.service.impl.common;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.testbest.converter.impl.manage.QuestionFullConverter;
import ru.testbest.converter.impl.test.QuestionConverter;
import ru.testbest.dto.test.QuestionDto;
import ru.testbest.dto.test.QuestionFullDto;
import ru.testbest.persistence.dao.QuestionDao;
import ru.testbest.persistence.entity.Question;
import ru.testbest.service.QuestionService;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

  private final QuestionDao questionDao;
  private final QuestionConverter questionConverter;
  private final QuestionFullConverter questionFullConverter;

  @Override
  public List<QuestionDto> getQuestions() {
    return questionDao.findAllByIsDeletedFalse().stream()
        .map(questionConverter::convertToDto)
        .collect(Collectors.toList());
  }

  @Override
  public List<QuestionFullDto> getFullQuestions() {
    return questionDao.findAllByIsDeletedFalse().stream()
        .map(questionFullConverter::convertToDto)
        .collect(Collectors.toList());
  }

  @Override
  public QuestionDto getQuestionById(UUID uuid) {
    return questionDao.findByIdAndIsDeletedFalse(uuid)
        .map(questionConverter::convertToDto)
        .orElse(null);
  }

  @Override
  public QuestionFullDto getQuestionFullById(UUID uuid) {
    return questionDao.findByIdAndIsDeletedFalse(uuid)
        .map(questionFullConverter::convertToDto)
        .orElse(null);
  }

  @Override
  public QuestionFullDto createQuestion(QuestionFullDto questionDto) {
    return questionFullConverter.convertToDto(
        questionDao.save(
            questionFullConverter.convertToEntity(questionDto)));
  }

  @Override
  public QuestionFullDto editQuestion(QuestionFullDto questionDto) {
    return questionFullConverter.convertToDto(
        questionDao.save(
            questionFullConverter.convertToEntity(questionDto)));
  }

  @Override
  public void deleteQuestionById(UUID uuid) {
    Optional<Question> oQuestion = questionDao.findByIdAndIsDeletedFalse(uuid);
    if (oQuestion.isPresent()) {
      Question question = oQuestion.get();
      question.setIsDeleted(true);
      questionDao.save(question);
    }
  }
}
