package ru.testbest.persistence.newdao.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Table(name = "topic")
public class Topic {

    @Id
    private String id;

    @Column
    private String name;

    @Column
    private String description;

    public Topic() {
        id = UUID.randomUUID().toString();
    }
}
