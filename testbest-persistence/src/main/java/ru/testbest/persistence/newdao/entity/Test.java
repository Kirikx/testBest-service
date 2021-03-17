package ru.testbest.persistence.newdao.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "test")
public class Test {

    @Id
    String id;

    @Column
    String name;

    @Column
    String description;

    @Column
    LocalDateTime created;

    @Column
    Short duration;

    @Column(name = "deleted")
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
