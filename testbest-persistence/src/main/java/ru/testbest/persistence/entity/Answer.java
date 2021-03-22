package ru.testbest.persistence.entity;

import java.util.UUID;
import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "answer")
public class Answer {

  @Id
  @GeneratedValue
  @Column(columnDefinition = "BINARY(16)")
  private UUID id;

  @Column
  private String answer;

  @Column(name = "deleted",
      columnDefinition = "TINYINT")
  private Boolean isDeleted;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "question_id")
  private Question question;
}
