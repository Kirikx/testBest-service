package ru.testbest.dto.test;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class UserTestQuestionDto {

  private String id;
  private String freeAnswer;
  private LocalDateTime answered;
  private Boolean isCorrect;
  private String userTestId;
  private String questionId;
  private Set<SelectedAnswerDto> selectedAnswers;
}
