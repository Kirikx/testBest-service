package ru.testbest.dto.test;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class UserTestDto {

  private String id;
  private LocalDateTime started;
  private LocalDateTime finished;
  private Short score;
  private Boolean isPassed;
  private String testId;
  private String userId;
  private Set<UserTestQuestionDto> userTestQuestions;
}
