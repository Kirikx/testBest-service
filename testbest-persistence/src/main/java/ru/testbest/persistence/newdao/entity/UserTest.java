package ru.testbest.persistence.newdao.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getStarted() {
        return started;
    }

    public void setStarted(LocalDateTime started) {
        this.started = started;
    }

    public LocalDateTime getFinished() {
        return finished;
    }

    public void setFinished(LocalDateTime finished) {
        this.finished = finished;
    }

    public Short getScore() {
        return score;
    }

    public void setScore(Short score) {
        this.score = score;
    }

    public Boolean getPassed() {
        return isPassed;
    }

    public void setPassed(Boolean passed) {
        isPassed = passed;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
