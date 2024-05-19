package com.edusenior.project.entities;

import com.edusenior.project.Exceptions.InvalidOperationException;
import com.edusenior.project.entities.Users.Student;
import com.edusenior.project.entities.Users.Teacher;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

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

    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinTable(
            name = "classes_students_junction",
            joinColumns=@JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> enrolledStudents;

    @Column(name = "number_of_students")
    private int numberOfStudents;

    @OneToMany(mappedBy = "schoolClass")
    private List<Comp> comps;

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
    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }


    public List<Comp> getComps() {
        return comps;
    }

    public void setComps(List<Comp> comps) {
        this.comps = comps;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public void setEnrolledStudents(List<Student> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
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
    public void addMultipleStudents(List<Student> students){
        if(enrolledStudents == null){
            enrolledStudents = new ArrayList<>();
        }
        this.enrolledStudents.addAll(students);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        SchoolClass otherClass = (SchoolClass) o;
        return id != null && id.equals(otherClass.getId());
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }}


