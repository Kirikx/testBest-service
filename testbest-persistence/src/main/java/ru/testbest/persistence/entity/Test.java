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
@Table(name = "test")
public class Test {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private LocalDateTime created;

    @Column
    private Short duration;

    @Column(name = "deleted",
        columnDefinition = "TINYINT(1)")
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @OneToMany(mappedBy = "test",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Chapter> chapters;

    public Test() {
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
