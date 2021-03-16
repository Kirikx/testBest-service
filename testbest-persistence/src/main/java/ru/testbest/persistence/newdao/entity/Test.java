package ru.testbest.persistence.newdao.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Short getDuration() {
        return duration;
    }

    public void setDuration(Short duration) {
        this.duration = duration;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Set<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(Set<Chapter> chapters) {
        this.chapters = chapters;
    }
}
