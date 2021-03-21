package ru.testbest.persistence.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Type;

@Data
@Entity
@Table(name = "answer")
public class Answer {

  @Id
  @Type(type = "char")
  String id;

  @Column
  String answer;

  @Column(name = "deleted",
      columnDefinition = "TINYINT(1)")
  Boolean isDeleted;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "question_id")
  Question question;

  public Answer() {
    id = UUID.randomUUID().toString();
  }
}
