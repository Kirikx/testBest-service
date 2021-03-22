package ru.testbest.service;

import java.util.List;
import java.util.UUID;
import ru.testbest.dto.test.QuestionDto;

public interface QuestionService {

  List<QuestionDto> getQuestions();

  QuestionDto getQuestionById(UUID uuid);

  QuestionDto createQuestion(QuestionDto questionDto);

  QuestionDto editQuestion(QuestionDto questionDto);

  void deleteQuestionById(UUID uuid);
}
