package ru.testbest.persistence.entity;

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
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.testbest.persistence.BaseEntity;

@Getter
@Setter
@Entity
@Table(name = "chapter")
public class Chapter implements BaseEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @NotNull
    @Column
    private String name;

    @Column
    private String description;

    @NotNull
    @Column(name = "deleted",
        columnDefinition = "TINYINT")
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id")
    private Test test;

    @ManyToMany
    @JoinTable(
        name = "question_chapter",
        joinColumns = @JoinColumn(name = "chapter_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id"))
    private Set<Question> questions;

    public Chapter() {
        questions = new HashSet<>();
    }

    public void addQuestion(Question question) {
        questions.add(question);
        if (!question.hasChapters(this)) {
            question.addChapter(this);
        }
    }

    public void removeQuestion(Question question) {
        System.out.println(questions.remove(question));
        if (question.hasChapters(this)) {
            question.removeChapter(this);
        }
    }

    boolean hasQuestion(Question question) {
        return questions.contains(question);
    }

    @Override
    public String toString() {
        return "Chapter{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", isDeleted=" + isDeleted +
            ", test=" + test +
            '}';
    }
}
