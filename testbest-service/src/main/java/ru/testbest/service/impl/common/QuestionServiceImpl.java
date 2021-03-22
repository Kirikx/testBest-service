package ru.testbest.service.impl.common;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.testbest.converter.impl.test.QuestionConverter;
import ru.testbest.dto.test.QuestionDto;
import ru.testbest.persistence.dao.QuestionDao;
import ru.testbest.persistence.entity.Question;
import ru.testbest.service.QuestionService;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

  private final QuestionDao questionDao;
  private final QuestionConverter questionConverter;

  @Override
  public List<QuestionDto> getQuestions() {
    return questionDao.findAllByIsDeletedFalse().stream()
        .map(questionConverter::convertToDto)
        .collect(Collectors.toList());
  }

  @Override
  public QuestionDto getQuestionById(String uuid) {
    return questionDao.findByIdAndIsDeletedFalse(UUID.fromString(uuid))
        .map(questionConverter::convertToDto)
        .orElse(null);
  }

  @Override
  public QuestionDto createQuestion(QuestionDto questionDto) {
    return questionConverter.convertToDto(
        questionDao.save(
            questionConverter.convertToEntity(questionDto)));
  }

  @Override
  public QuestionDto editQuestion(QuestionDto questionDto) {
    return questionConverter.convertToDto(
        questionDao.save(
            questionConverter.convertToEntity(questionDto)));
  }

  @Override
  public void deleteQuestionById(String uuid) {
    Optional<Question> oQuestion = questionDao.findByIdAndIsDeletedFalse(UUID.fromString(uuid));
    if (oQuestion.isPresent()) {
      Question question = oQuestion.get();
      question.setIsDeleted(true);
      questionDao.save(question);
    }
  }
}
