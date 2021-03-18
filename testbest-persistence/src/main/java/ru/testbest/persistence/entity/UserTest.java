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
@Table(name = "user_test")
public class UserTest {

    @Id
    String id;

    @Column
    LocalDateTime started;

    @Column
    LocalDateTime finished;

    @Column
    Short score;

    @Column(name = "passed")
    Boolean isPassed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id")
    Test test;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @OneToMany(mappedBy = "userTest",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<UserTestQuestion> userTestQuestions;

    public UserTest() {
        id = UUID.randomUUID().toString();
        userTestQuestions = new HashSet<>();
    }

    public void addSelectedAnswer(UserTestQuestion question) {
        userTestQuestions.add(question);
        question.setUserTest(this);
    }

    public void removeSelectedAnswer(UserTestQuestion question) {
        userTestQuestions.remove(question);
        question.setUserTest(null);
    }
}
