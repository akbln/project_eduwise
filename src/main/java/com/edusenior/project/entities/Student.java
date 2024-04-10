package com.edusenior.project.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "student_table", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class Student extends User{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "student_id", nullable = false,updatable = false)
    private String id;

    @Column(name = "educational_level",length = 50,nullable = false,updatable = true)
    private String level;

    public Student(){
        super();
    }


    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getId() {
        return id;
    }

}
