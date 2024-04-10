package com.edusenior.project.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "teacher_table", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})},schema = "edusenior")
public class Teacher extends User{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "teacher_id", nullable = false,updatable = false)
    private String id;


    public Teacher(){
        super();
        this.role = "teacher";
    }

    @Column(name = "role",length = 50,nullable = false,updatable = true)
    private String role;

    public String getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
