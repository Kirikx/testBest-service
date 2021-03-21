package ru.testbest.persistence.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "role")
public class Role {

    @Id
    @Column(columnDefinition = "char")
    private String id;

    @Column
    private String name;

    public Role() {
        id = UUID.randomUUID().toString();
    }
}
