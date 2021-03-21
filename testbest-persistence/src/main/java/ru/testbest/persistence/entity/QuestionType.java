package ru.testbest.persistence.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "question_type")
public class QuestionType {

    @Id
    @Column(columnDefinition = "char")
    private String id;

    @Column
    private String name;

    public QuestionType() {
        id = UUID.randomUUID().toString();
    }
}
