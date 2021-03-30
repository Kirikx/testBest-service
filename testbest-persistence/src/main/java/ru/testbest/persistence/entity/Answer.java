package ru.testbest.persistence.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import ru.testbest.persistence.BaseEntity;

@Getter
@Setter
@Entity
@Table(name = "answer")
public class Answer implements BaseEntity {

  @Id
  @GeneratedValue
  @Column(columnDefinition = "BINARY(16)")
  private UUID id;

  @NotNull
  @Column
  private String answer;

  @NotNull
  @Column(name = "deleted",
      columnDefinition = "TINYINT")
  private Boolean isDeleted = false;

  @NotNull
  @Column(name = "correct",
      columnDefinition = "TINYINT")
  private Boolean isCorrect = false;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "question_id")
  private Question question;
}
