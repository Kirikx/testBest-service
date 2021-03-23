package ru.testbest.service;

import java.util.List;
import java.util.UUID;
import ru.testbest.dto.test.QuestionDto;
import ru.testbest.dto.test.QuestionFullDto;

public interface QuestionService {

  List<QuestionDto> getQuestions();

  QuestionDto getQuestionById(UUID uuid);

  void deleteQuestionById(UUID uuid);

  List<QuestionFullDto> getFullQuestions();

  QuestionFullDto getQuestionFullById(UUID uuid);

  QuestionFullDto createQuestion(QuestionFullDto questionDto);

  QuestionFullDto editQuestion(QuestionFullDto questionDto);

}
