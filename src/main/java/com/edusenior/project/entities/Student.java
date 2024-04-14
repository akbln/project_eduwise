package com.edusenior.project.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "students", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
@PrimaryKeyJoinColumn(name = "user_id")
public class Student extends User {

    @Column(name = "educational_level", length = 50, nullable = false, updatable = true)
    private String level;

    public Student() {
        super();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
