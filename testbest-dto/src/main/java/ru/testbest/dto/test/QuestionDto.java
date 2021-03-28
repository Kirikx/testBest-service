package ru.testbest.dto.test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Data;
import ru.testbest.dto.BaseDTO;

@Data
public class QuestionDto implements BaseDTO {

  private UUID id;
  private String questionText;
  private UUID topicId;
  private UUID questionTypeId;
  private Set<AnswerDto> questionAnswers = new HashSet<>();
}
