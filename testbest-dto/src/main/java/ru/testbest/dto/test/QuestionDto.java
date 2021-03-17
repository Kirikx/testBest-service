package ru.testbest.dto.test;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;


@Data
public class QuestionDto {

  private String id;
  private String questionText;
  private String topicId;
  private String questionTypeId;
  private Set<AnswerDto> questionAnswers = new HashSet<>();
}
