package ru.testbest.persistence.newdao.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "answer")
public class Answer {

    @Id
    String id;

    @Column
    String answer;

    @Column(name = "deleted")
    Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    Question question;

    public Answer() {
        id = UUID.randomUUID().toString();
    }
}
