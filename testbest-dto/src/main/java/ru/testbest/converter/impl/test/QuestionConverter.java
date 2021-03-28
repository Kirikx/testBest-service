package ru.testbest.converter.impl.test;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.testbest.converter.impl.AbstractMapper;
import ru.testbest.dto.test.QuestionDto;
import ru.testbest.persistence.dao.QuestionTypeDao;
import ru.testbest.persistence.dao.TopicDao;
import ru.testbest.persistence.entity.Question;

@Component
public class QuestionConverter extends AbstractMapper<Question, QuestionDto> {

  private final ModelMapper mapper;
  private final TopicDao topicDao;
  private final QuestionTypeDao questionTypeDao;

  @Autowired
  public QuestionConverter(
      ModelMapper mapper,
      TopicDao topicDao,
      QuestionTypeDao questionTypeDao
  ) {
    super(Question.class, QuestionDto.class);
    this.mapper = mapper;
    this.topicDao = topicDao;
    this.questionTypeDao = questionTypeDao;
  }

  @PostConstruct
  public void setupMapper() {
    mapper.createTypeMap(Question.class, QuestionDto.class)
        .addMappings(m -> m.skip(QuestionDto::setTopicId))
        .addMappings(m -> m.skip(QuestionDto::setQuestionTypeId))
        .setPostConverter(toDtoConverter());
    mapper.createTypeMap(QuestionDto.class, Question.class)
        .addMappings(m -> m.skip(Question::setTopic))
        .addMappings(m -> m.skip(Question::setQuestionType))
        .setPostConverter(toEntityConverter());
  }

  @Override
  public void mapSpecificFields(Question source, QuestionDto destination) {
    destination.setTopicId(getTopicId(source));
    destination.setQuestionTypeId(getQuestionTypeId(source));
  }

  private UUID getTopicId(Question source) {
    return Objects.isNull(source) || Objects.isNull(source.getTopic()) ? null
        : source.getTopic().getId();
  }

  private UUID getQuestionTypeId(Question source) {
    return Objects.isNull(source) || Objects.isNull(source.getQuestionType()) ? null
        : source.getQuestionType().getId();
  }

  @Override
  public void mapSpecificFields(QuestionDto source, Question destination) {
    Optional.ofNullable(source.getTopicId()).ifPresent(id ->
        destination.setTopic(topicDao.findById(id).orElse(null))
    );
    Optional.ofNullable(source.getQuestionTypeId()).ifPresent(id ->
        destination.setQuestionType(questionTypeDao.findById(id).orElse(null))
    );
  }
}
