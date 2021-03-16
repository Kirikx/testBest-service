package ru.testbest.persistence.newdao.entity;

import javax.persistence.*;
import java.util.UUID;

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
}
