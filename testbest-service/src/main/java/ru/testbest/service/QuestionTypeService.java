package ru.testbest.service;

import java.util.List;
import ru.testbest.dto.common.QuestionTypeDto;

public interface QuestionTypeService {

  List<QuestionTypeDto> getQuestionTypes();

  QuestionTypeDto getQuestionTypeById(String uuid);
}
