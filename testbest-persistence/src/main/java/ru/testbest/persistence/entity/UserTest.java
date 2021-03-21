package ru.testbest.persistence.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;

import lombok.Data;
import org.hibernate.annotations.Type;

@Data
@Entity
@Table(name = "user_test")
public class UserTest {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private LocalDateTime started;

    @Column
    private LocalDateTime finished;

    @Column
    private Short score;

    @Column(name = "passed",
        columnDefinition = "TINYINT(1)")
    private Boolean isPassed;

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
