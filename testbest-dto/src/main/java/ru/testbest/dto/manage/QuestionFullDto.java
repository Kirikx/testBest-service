package ru.testbest.dto.manage;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Data;
import ru.testbest.dto.BaseDTO;

@Data
public class QuestionFullDto implements BaseDTO {

  private UUID id;
  private String question;
  private UUID topicId;
  private UUID questionTypeId;
  private Set<AnswerFullDto> answers = new HashSet<>();
  private Boolean isDeleted;
  private Set<ChapterWrapDto> chapters;
}
