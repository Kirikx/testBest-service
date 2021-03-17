package ru.testbest.persistence.newdao.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "role")
public class Role {

    @Id
    private String id;

    @Column
    private String name;

    public Role() {
        id = UUID.randomUUID().toString();
    }
}
