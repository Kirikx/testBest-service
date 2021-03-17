package ru.testbest.service.impl.common;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.testbest.converter.impl.common.QuestionTypeConverter;
import ru.testbest.dto.common.QuestionTypeDto;
import ru.testbest.persistence.dao.QuestionTypeDao;
import ru.testbest.persistence.entity.QuestionType;
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
    QuestionType questionType = questionTypeDao.findById(uuid).orElseThrow(RuntimeException::new);
    return questionTypeConverter.convertToDto(questionType);
  }
}


