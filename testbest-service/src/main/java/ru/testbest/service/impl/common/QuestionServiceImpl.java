package ru.testbest.service.impl.common;

import java.util.List;
import java.util.Optional;
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
    return questionDao.findAll().stream()
        .map(questionConverter::convertToDto)
        .collect(Collectors.toList());
  }

  @Override
  public QuestionDto getQuestionById(String uuid) {
    Question question = questionDao.findById(uuid).orElseThrow(RuntimeException::new);
    return questionConverter.convertToDto(question);
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
    Optional<Question> oQuestion = questionDao.findById(uuid);
    if (oQuestion.isPresent()) {
      Question question = oQuestion.get();
      question.setDeleted(true);
      questionDao.save(question);
    }
  }
}
