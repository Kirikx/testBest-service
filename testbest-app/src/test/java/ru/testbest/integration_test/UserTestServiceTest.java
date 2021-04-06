package ru.testbest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.testbest.SpringBootSecurityJwtApplication;
import ru.testbest.converter.impl.manage.TestFullConverter;
import ru.testbest.converter.impl.test.TestConverter;
import ru.testbest.dto.manage.AnswerFullDto;
import ru.testbest.persistence.dao.*;
import ru.testbest.persistence.entity.Answer;
import ru.testbest.persistence.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
public class UserTestServiceTest {

  @Autowired
  ObjectMapper mapper;

  @Autowired
  AnswerService answerService;
  @Autowired
  TestService testService;
  @Autowired
  QuestionService questionService;
  @Autowired
  ChapterService chapterService;
  @Autowired
  TopicService topicService;

  @Autowired
  private TestDao testRepository;

  @Autowired
  private TestFullConverter testFullConverter;

  @Autowired
  private TestConverter testConverter;

  @Test
  public void when_then() {

    String testId = "dec15719-95f2-46a1-931a-865617972d70";

    Optional<ru.testbest.persistence.entity.Test> expectedTest =
      testRepository.findById(UUID.fromString(testId));

    List<AnswerFullDto> answers = answerService.getAnswersFull();

    answers.stream().map(x -> x.getAnswer()).forEach(System.out::println);
  }

}
