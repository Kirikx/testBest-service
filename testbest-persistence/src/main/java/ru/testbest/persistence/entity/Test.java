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
import org.hibernate.annotations.Type;

@Data
@Entity
@Table(name = "test")
public class Test {

    @Id
    @Type(type = "char")
    String id;

    @Column
    String name;

    @Column
    String description;

    @Column
    LocalDateTime created;

    @Column
    Short duration;

    @Column(name = "deleted",
        columnDefinition = "TINYINT(1)")
    Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    Topic topic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    User author;

    @OneToMany(mappedBy = "test",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Chapter> chapters;

    public Test() {
        id = UUID.randomUUID().toString();
        chapters = new HashSet<>();
    }

    public void addChapter(Chapter chapter) {
        chapters.add(chapter);
        chapter.setTest(this);
    }

    public void removeChapter(Chapter chapter) {
        chapters.remove(chapter);
        chapter.setTest(null);
    }
}
