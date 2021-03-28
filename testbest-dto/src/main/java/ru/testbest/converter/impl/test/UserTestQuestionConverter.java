package ru.testbest.converter.impl.test;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.testbest.converter.impl.AbstractMapper;
import ru.testbest.dto.test.UserTestQuestionDto;
import ru.testbest.persistence.dao.QuestionDao;
import ru.testbest.persistence.dao.UserTestDao;
import ru.testbest.persistence.entity.UserTestQuestion;

@Component
public class UserTestQuestionConverter extends
    AbstractMapper<UserTestQuestion, UserTestQuestionDto> {

  private final ModelMapper mapper;
  private final UserTestDao userTestDao;
  private final QuestionDao questionDao;

  @Autowired
  public UserTestQuestionConverter(
      ModelMapper mapper,
      UserTestDao userTestDao,
      QuestionDao questionDao
  ) {
    super(UserTestQuestion.class, UserTestQuestionDto.class);
    this.mapper = mapper;
    this.userTestDao = userTestDao;
    this.questionDao = questionDao;
  }

  @PostConstruct
  public void setupMapper() {
    mapper.createTypeMap(UserTestQuestion.class, UserTestQuestionDto.class)
        .addMappings(m -> m.skip(UserTestQuestionDto::setUserTestId))
        .addMappings(m -> m.skip(UserTestQuestionDto::setQuestionId))
        .setPostConverter(toDtoConverter());
    mapper.createTypeMap(UserTestQuestionDto.class, UserTestQuestion.class)
        .addMappings(m -> m.skip(UserTestQuestion::setUserTest))
        .addMappings(m -> m.skip(UserTestQuestion::setQuestion))
        .setPostConverter(toEntityConverter());
  }

  @Override
  public void mapSpecificFields(UserTestQuestion source, UserTestQuestionDto destination) {
    destination.setUserTestId(getUserTestId(source));
    destination.setQuestionId(getQuestionId(source));
  }

  private UUID getUserTestId(UserTestQuestion source) {
    return Objects.isNull(source) || Objects.isNull(source.getUserTest()) ? null
        : source.getUserTest().getId();
  }

  private UUID getQuestionId(UserTestQuestion source) {
    return Objects.isNull(source) || Objects.isNull(source.getQuestion()) ? null
        : source.getQuestion().getId();
  }

  @Override
  public void mapSpecificFields(UserTestQuestionDto source, UserTestQuestion destination) {
    Optional.ofNullable(source.getUserTestId()).ifPresent(id ->
        destination.setUserTest(userTestDao.findById(id).orElse(null))
    );
    Optional.ofNullable(source.getQuestionId()).ifPresent(id ->
        destination.setQuestion(questionDao.findById(source.getQuestionId()).orElse(null))
    );
  }
}
