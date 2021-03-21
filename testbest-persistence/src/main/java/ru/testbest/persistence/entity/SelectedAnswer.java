package ru.testbest.persistence.entity;

import java.util.UUID;
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
@Table(name = "selected_answer")
public class SelectedAnswer {

    @Id
    @Type(type = "char")
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
