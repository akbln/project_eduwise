package com.edusenior.project.entities;

import com.edusenior.project.Exceptions.InvalidOperationException;
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

    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "number_of_students")
    private int numberOfStudents;


    // Getters and Setters

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    // Methods
    // Too slow
    public int incrementNumberOfStudents() {
        if(numberOfStudents == Integer.MAX_VALUE){
            throw new InvalidOperationException("Cannot increment the number students further");
        }
        return ++numberOfStudents;
    };
    public int decrementNumberOfStudents() throws InvalidOperationException {
        if(numberOfStudents <= 0){
            throw new InvalidOperationException("Cannot decrement the number students further");
        }
        return --numberOfStudents;
    };
}
