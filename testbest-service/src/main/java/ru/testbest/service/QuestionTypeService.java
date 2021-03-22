package ru.testbest.service;

import java.util.List;
import java.util.UUID;
import ru.testbest.dto.common.QuestionTypeDto;

public interface QuestionTypeService {

  List<QuestionTypeDto> getQuestionTypes();

  QuestionTypeDto getQuestionTypeById(UUID uuid);
}
