package ru.testbest.persistence.newdao.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "chapter")
public class Chapter {

    @Id
    String id;

    @Column
    String name;

    @Column
    String description;

    @Column(name = "deleted")
    Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id")
    Test test;

    @ManyToMany
    @JoinTable(
            name = "question_chapter",
            joinColumns = @JoinColumn(name = "chapter_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id"))
    Set<Question> questions;

    public Chapter() {
        id = UUID.randomUUID().toString();
        questions = new HashSet<>();
    }

    public void addQuestion(Question question) {
        questions.add(question);
        if (!question.hasChapters(this)) {
            question.addChapter(this);
        }
    }

    public void removeQuestion(Question question) {
        questions.remove(question);
        if (question.hasChapters(this)) {
            question.removeChapter(this);
        }
    }

    boolean hasQuestion(Question question) {
        return questions.contains(question);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }
}
