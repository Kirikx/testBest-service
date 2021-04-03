package ru.testbest.persistence.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import ru.testbest.persistence.BaseEntity;

@Getter
@Setter
@Entity
@Table(name = "user_test")
public class UserTest implements BaseEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @NotNull
    @Column
    private LocalDateTime started;

    @NotNull
    @Column
    private LocalDateTime finished;

    @Column
    private Short score;

    @NotNull
    @Column(name = "passed",
        columnDefinition = "TINYINT")
    private Boolean isPassed = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id")
    private Test test;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "userTest",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<UserTestQuestion> userTestQuestions;

    public UserTest() {
        userTestQuestions = new HashSet<>();
    }

    public void addUserTestQuestion(UserTestQuestion question) {
        userTestQuestions.add(question);
        question.setUserTest(this);
    }

    public void removeUserTestQuestion(UserTestQuestion question) {
        userTestQuestions.remove(question);
        question.setUserTest(null);
    }
}
