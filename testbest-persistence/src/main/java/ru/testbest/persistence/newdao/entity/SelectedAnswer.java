package ru.testbest.persistence.newdao.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "selected_answer")
public class SelectedAnswer {

    @Id
    String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_question_id")
    UserTestQuestion userTestQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    Answer answer;

    public SelectedAnswer() {
        id = UUID.randomUUID().toString();
    }
}
