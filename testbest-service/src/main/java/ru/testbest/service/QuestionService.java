package ru.testbest.service;

import java.util.List;
import java.util.UUID;
import ru.testbest.dto.manage.QuestionFullDto;
import ru.testbest.dto.test.QuestionDto;

public interface QuestionService {

  List<QuestionDto> getQuestions();

  List<QuestionFullDto> getFullQuestions();

  QuestionDto getQuestionById(UUID uuid);

  QuestionFullDto getQuestionFullById(UUID uuid);

  QuestionFullDto createQuestion(QuestionFullDto questionDto);

  QuestionFullDto editQuestion(QuestionFullDto questionDto);

  void deleteQuestionById(UUID uuid);

}
