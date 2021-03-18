package ru.testbest.persistence.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
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
}
