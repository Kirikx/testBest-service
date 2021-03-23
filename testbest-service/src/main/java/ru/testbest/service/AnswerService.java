package ru.testbest.service;

import java.util.List;
import java.util.UUID;
import ru.testbest.dto.test.AnswerDto;
import ru.testbest.dto.test.AnswerFullDto;

public interface AnswerService {

  List<AnswerDto> getAnswers();

  AnswerDto getAnswerById(UUID uuid);

  List<AnswerFullDto> getAnswersFull();

  AnswerFullDto getAnswerFullById(UUID uuid);

  AnswerFullDto createAnswer(AnswerFullDto answerDto);

  AnswerFullDto editAnswer(AnswerFullDto answerDto);

  void deleteAnswerById(UUID uuid);

}
