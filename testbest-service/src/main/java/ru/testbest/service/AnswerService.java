package ru.testbest.service;

import java.util.List;
import java.util.UUID;

import ru.testbest.dto.test.AnswerDto;

public interface AnswerService {

  List<AnswerDto> getAnswers();

  AnswerDto getAnswerById(String uuid);

  AnswerDto createAnswer(AnswerDto answerDto);

  AnswerDto editAnswer(AnswerDto answerDto);

  void deleteAnswerById(String uuid);

}
