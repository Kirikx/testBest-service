package ru.testbest.persistence.entity;

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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    String question;

    @Column(name = "deleted",
        columnDefinition = "TINYINT")
    Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    Topic topic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_type_id")
    QuestionType questionType;

    @OneToMany(mappedBy = "question",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Answer> answers;

    @ManyToMany(mappedBy = "questions")
    Set<Chapter> chapters;

    public Question() {
        answers = new HashSet<>();
    }

    public void addChapter(Chapter chapter) {
        chapters.add(chapter);
        if (!chapter.hasQuestion(this)) {
            chapter.addQuestion(this);
        }
    }

    public void removeChapter(Chapter chapter) {
        chapters.remove(chapters);
        if (chapter.hasQuestion(this)) {
            chapter.removeQuestion(this);
        }
    }

    boolean hasChapters(Chapter chapter) {
        return chapters.contains(chapter);
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
        answer.setQuestion(this);
    }

    public void removeAnswer(Answer answer) {
        answers.remove(answer);
        answer.setQuestion(null);
    }
}
