package ru.testbest.converter.impl.test;

import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import ru.testbest.converter.ConverterTest;
import ru.testbest.dto.test.QuestionDto;
import ru.testbest.persistence.entity.Question;

@Component
public class QuestionConverter implements ConverterTest<Question, QuestionDto> {

  private static final AnswerConverter answerConverter = new AnswerConverter();

  @Override
  public QuestionDto convertToDto(Question entity) {
    QuestionDto questionDto = new QuestionDto();
    questionDto.setId(entity.getId());
    questionDto.setQuestionText(entity.getQuestion());
    questionDto.setTopicId(entity.getTopic().getId());
    questionDto.setQuestionTypeId(entity.getQuestionType().getId());
    questionDto.setQuestionAnswers(entity.getAnswers().stream()
        .map(answerConverter::convertToDto)
        .collect(Collectors.toSet()));
    return questionDto;

  }

  @Override
  public Question convertToEntity(QuestionDto dto) {
    throw new UnsupportedOperationException();
  }
}
