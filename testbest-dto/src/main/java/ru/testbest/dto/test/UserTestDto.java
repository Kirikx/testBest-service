package ru.testbest.dto.test;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import lombok.Data;

@Data
public class UserTestDto {

  private UUID id;
  private LocalDateTime started;
  private LocalDateTime finished;
  private Short score;
  private Boolean isPassed;
  private UUID testId;
  private UUID userId;
  private Set<UserTestQuestionDto> userTestQuestions;
}
