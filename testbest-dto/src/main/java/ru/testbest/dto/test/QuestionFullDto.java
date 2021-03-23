package ru.testbest.dto.test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Data;

@Data
public class QuestionFullDto {

  private UUID id;
  private String questionText;
  private UUID topicId;
  private UUID questionTypeId;
  private Set<AnswerFullDto> questionAnswers = new HashSet<>();
  private Boolean isDeleted;
  private Set<ChapterWrapDto> chapters;
}
