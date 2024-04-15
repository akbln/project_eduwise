package com.edusenior.project.entities;

import com.edusenior.project.entities.Users.Teacher;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "classes")
public class SchoolClass {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "class_id", nullable = false,updatable = false)
    private String id;

//    @Column(name = "teacher_id")
//    private String teacherId;

    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST})
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getTeacherId() {
//        return teacherId;
//    }
//
//    public void setTeacherId(String teacherId) {
//        this.teacherId = teacherId;
//    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
