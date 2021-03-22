package ru.testbest.converter.impl.test;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.testbest.converter.ConverterTest;
import ru.testbest.dto.test.AnswerFullDto;
import ru.testbest.persistence.dao.QuestionDao;
import ru.testbest.persistence.entity.Answer;

@Component
@RequiredArgsConstructor
public class AnswerFullConverter implements ConverterTest<Answer, AnswerFullDto> {

  private final QuestionDao questionDao;

  @Override
  public AnswerFullDto convertToDto(Answer entity) {
    AnswerFullDto answerFullDto = new AnswerFullDto();
    answerFullDto.setId(entity.getId());
    answerFullDto.setAnswerText(entity.getAnswer());
//    answerFullDto.setIsCorrect(entity.getIsCorrect());
    answerFullDto.setQuestionId(Optional.ofNullable(entity.getQuestion().getId())
        .orElseThrow(() -> new RuntimeException("Answer is not contains question link")));
    return answerFullDto;
  }

  @Override
  public Answer convertToEntity(AnswerFullDto dto) {
    Answer answer = new Answer();
    answer.setId(dto.getId());
    answer.setAnswer(dto.getAnswerText());
//    answer.setIsCorrect(dto.getIsCorrect());
    answer.setIsDeleted(dto.getIsDeleted());
    answer.setQuestion(questionDao.findById(dto.getQuestionId())
        .orElseThrow(() -> new RuntimeException("Answer is not contains question link")));
    return answer;

  }
}
