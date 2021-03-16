package ru.testbest.persistence.newdao.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "user_test_question")
public class UserTestQuestion {

    @Id
    String id;

    @Column(name = "user_answer")
    String freeAnswer;

    @Column
    LocalDateTime answered;

    @Column(name = "correct")
    Boolean isCorrect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_test_id")
    UserTest userTest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    Question question;

    @OneToMany(mappedBy = "userTestQuestion",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<SelectedAnswer> selectedAnswers;

    public UserTestQuestion() {
        id = UUID.randomUUID().toString();
        selectedAnswers = new HashSet<>();
    }

    public void addSelectedAnswer(SelectedAnswer answer) {
        selectedAnswers.add(answer);
        answer.setUserTestQuestion(this);
    }

    public void removeSelectedAnswer(SelectedAnswer answer) {
        selectedAnswers.remove(answer);
        answer.setUserTestQuestion(null);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFreeAnswer() {
        return freeAnswer;
    }

    public void setFreeAnswer(String freeAnswer) {
        this.freeAnswer = freeAnswer;
    }

    public LocalDateTime getAnswered() {
        return answered;
    }

    public void setAnswered(LocalDateTime answered) {
        this.answered = answered;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    public UserTest getUserTest() {
        return userTest;
    }

    public void setUserTest(UserTest userTest) {
        this.userTest = userTest;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
