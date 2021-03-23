package ru.testbest.service.impl.common;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
  @Transactional(readOnly = true)
  public List<QuestionTypeDto> getQuestionTypes() {
    return questionTypeDao.findAll().stream()
        .map(questionTypeConverter::convertToDto)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public QuestionTypeDto getQuestionTypeById(UUID uuid) {
    return questionTypeDao.findById(uuid)
        .map(questionTypeConverter::convertToDto)
        .orElse(null);
  }
}


