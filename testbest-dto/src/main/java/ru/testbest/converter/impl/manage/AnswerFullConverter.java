package ru.testbest.converter.impl.manage;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.testbest.converter.impl.AbstractMapper;
import ru.testbest.dto.manage.AnswerFullDto;
import ru.testbest.persistence.dao.QuestionDao;
import ru.testbest.persistence.entity.Answer;

@Component
public class AnswerFullConverter extends AbstractMapper<Answer, AnswerFullDto> {

  private final ModelMapper mapper;
  private final QuestionDao questionDao;

  @Autowired
  public AnswerFullConverter(
      ModelMapper mapper,
      QuestionDao questionDao
  ) {
    super(Answer.class, AnswerFullDto.class);
    this.mapper = mapper;
    this.questionDao = questionDao;
  }

  @PostConstruct
  public void setupMapper() {
    mapper.createTypeMap(Answer.class, AnswerFullDto.class)
        .addMappings(m -> m.skip(AnswerFullDto::setQuestionId))
        .setPostConverter(toDtoConverter());
    mapper.createTypeMap(AnswerFullDto.class, Answer.class)
        .addMappings(m -> m.skip(Answer::setQuestion))
        .setPostConverter(toEntityConverter());
  }

  @Override
  public void mapSpecificFields(Answer source, AnswerFullDto destination) {
    destination.setQuestionId(getQuestionId(source));
  }

  private UUID getQuestionId(Answer source) {
    return Objects.isNull(source) || Objects.isNull(source.getQuestion()) ? null
        : source.getQuestion().getId();
  }

  @Override
  public void mapSpecificFields(AnswerFullDto source, Answer destination) {
    Optional.ofNullable(source.getQuestionId()).ifPresent(id ->
        destination.setQuestion(questionDao.findById(id).orElse(null))
    );
  }
}
