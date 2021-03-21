package ru.testbest.persistence.entity;

import java.util.UUID;
import javax.persistence.*;

import lombok.Data;
import org.hibernate.annotations.Type;

@Data
@Entity
@Table(name = "topic")
public class Topic {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private String name;

    @Column
    private String description;
}
