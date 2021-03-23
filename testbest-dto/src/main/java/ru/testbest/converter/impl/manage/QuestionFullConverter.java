package ru.testbest.converter.impl.manage;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.testbest.converter.ConverterTest;
import ru.testbest.converter.impl.test.ChapterConverter;
import ru.testbest.dto.test.QuestionFullDto;
import ru.testbest.persistence.dao.QuestionTypeDao;
import ru.testbest.persistence.dao.TopicDao;
import ru.testbest.persistence.entity.Question;

@Component
@RequiredArgsConstructor
public class QuestionFullConverter implements ConverterTest<Question, QuestionFullDto> {

  private final TopicDao topicDao;
  private final QuestionTypeDao questionTypeDao;
  private final AnswerFullConverter answerFullConverter;
  private final ChapterConverter chapterConverter;

  @Override
  public QuestionFullDto convertToDto(Question entity) {
    QuestionFullDto questionFullDto = new QuestionFullDto();
    questionFullDto.setId(entity.getId());
    questionFullDto.setQuestionText(entity.getQuestion());
    questionFullDto.setTopicId(entity.getTopic().getId());
    questionFullDto.setQuestionTypeId(entity.getQuestionType().getId());
    questionFullDto.setQuestionAnswers(entity.getAnswers().stream()
        .map(answerFullConverter::convertToDto)
        .collect(Collectors.toSet()));
    questionFullDto.setIsDeleted(entity.getIsDeleted());
    questionFullDto.setChapters(entity.getChapters().stream()
        .map(chapterConverter::wrapDto)
        .collect(Collectors.toSet()));
    return questionFullDto;
  }

  @Override
  public Question convertToEntity(QuestionFullDto dto) {
    Question question = new Question();
    question.setId(dto.getId());
    question.setQuestion(dto.getQuestionText());
    question.setIsDeleted(dto.getIsDeleted());
    question.setTopic(topicDao.findById(dto.getTopicId()).orElse(null));
    question.setQuestionType(questionTypeDao.findById(dto.getQuestionTypeId()).orElse(null));
    question.setAnswers(dto.getQuestionAnswers().stream()
        .map(answerFullConverter::convertToEntity)
        .collect(Collectors.toSet()));
    question.setChapters(dto.getChapters().stream()
        .map(chapterConverter::unwrapDto)
        .collect(Collectors.toSet()));
    return question;
  }
}
