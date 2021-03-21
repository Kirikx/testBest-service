package ru.testbest.service.impl.common;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.testbest.converter.impl.common.QuestionTypeConverter;
import ru.testbest.dto.common.QuestionTypeDto;
import ru.testbest.persistence.dao.QuestionTypeDao;
import ru.testbest.service.QuestionTypeService;

@Service
@RequiredArgsConstructor
public class QuestionTypeServiceImpl implements QuestionTypeService {

  private final QuestionTypeDao questionTypeDao;
  private final QuestionTypeConverter questionTypeConverter;

  @Override
  public List<QuestionTypeDto> getQuestionTypes() {
    return questionTypeDao.findAll().stream()
        .map(questionTypeConverter::convertToDto)
        .collect(Collectors.toList());
  }

  @Override
  public QuestionTypeDto getQuestionTypeById(String uuid) {
    return questionTypeDao.findById(UUID.fromString(uuid))
        .map(questionTypeConverter::convertToDto)
        .orElse(null);
  }
}


