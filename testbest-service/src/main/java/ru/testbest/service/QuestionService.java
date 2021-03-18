package ru.testbest.service;

import java.util.List;
import ru.testbest.dto.test.QuestionDto;

public interface QuestionService {

  List<QuestionDto> getQuestions();

  QuestionDto getQuestionById(String uuid);

  QuestionDto createQuestion(QuestionDto questionDto);

  QuestionDto editQuestion(QuestionDto questionDto);

  void deleteQuestionById(String uuid);
}
