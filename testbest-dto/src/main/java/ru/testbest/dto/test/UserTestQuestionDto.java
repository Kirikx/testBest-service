package ru.testbest.dto.test;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import lombok.Data;

@Data
public class UserTestQuestionDto {

  private UUID id;
  private String freeAnswer;
  private LocalDateTime answered;
  private Boolean isCorrect;
  private UUID userTestId;
  private UUID questionId;
//  private Set<SelectedAnswerDto> selectedAnswers;
  private Set<AnswerDto> answers;
}
