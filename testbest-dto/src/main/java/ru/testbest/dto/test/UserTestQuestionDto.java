package ru.testbest.dto.test;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.Data;
import ru.testbest.dto.BaseDTO;

@Data
public class UserTestQuestionDto implements BaseDTO {

  private UUID id;
  private String freeAnswer;
  private LocalDateTime answered;
  private Boolean isCorrect;
  private UUID userTestId;
  private UUID questionId;
  private Set<AnswerDto> answers;
}
