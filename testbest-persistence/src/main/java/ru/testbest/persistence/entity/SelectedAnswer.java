package ru.testbest.persistence.entity;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserTestQuestion getUserTestQuestion() {
        return userTestQuestion;
    }

    public void setUserTestQuestion(UserTestQuestion userTestQuestion) {
        this.userTestQuestion = userTestQuestion;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
