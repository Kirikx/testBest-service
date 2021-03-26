package ru.testbest.persistence.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_test_question")
public class UserTestQuestion {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "user_answer")
    private String freeAnswer;

    @Column
    private LocalDateTime answered;

    @Column(name = "correct",
        columnDefinition = "TINYINT(1)")
    private Boolean isCorrect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_test_id")
    private UserTest userTest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToMany
    @JoinTable(
            name = "selected_answer",
            joinColumns = @JoinColumn(name = "test_question_id"),
            inverseJoinColumns = @JoinColumn(name = "answer_id"))
    private Set<Answer> answers;

    public UserTestQuestion() {
        answers = new HashSet<>();
    }

    public void addSelectedAnswer(Answer answer) {
        answers.add(answer);
    }

    public void removeSelectedAnswer(Answer answer) {
        answers.remove(answer);
    }
}
