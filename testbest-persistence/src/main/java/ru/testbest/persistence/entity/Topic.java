package ru.testbest.persistence.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "topic")
public class Topic {

    @Id
    @Column(columnDefinition = "char")
    private String id;

    @Column
    private String name;

    @Column
    private String description;

    public Topic() {
        id = UUID.randomUUID().toString();
    }
}
