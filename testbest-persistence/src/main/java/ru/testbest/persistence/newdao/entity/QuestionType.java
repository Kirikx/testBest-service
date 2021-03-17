package ru.testbest.persistence.newdao.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Table(name = "question_type")
public class QuestionType {

    @Id
    private String id;

    @Column
    private String name;

    public QuestionType() {
        id = UUID.randomUUID().toString();
    }
}
