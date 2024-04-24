package com.edusenior.project.entities.Users;

import com.edusenior.project.entities.SchoolClass;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
@PrimaryKeyJoinColumn(name = "user_id")
public class Student extends User {

    @Column(name = "educational_level", length = 50, nullable = false, updatable = true)
    private String level;



    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinTable(
            name = "classes_students_junction",
            joinColumns=@JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "class_id")
    )

    private List<SchoolClass> enrolledClasses;

    public Student() {
        super();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<SchoolClass> getEnrolledClasses() {
        return enrolledClasses;
    }
    public void addMultipleClasses(List<SchoolClass> SchoolClasses){
        if(enrolledClasses == null){
            enrolledClasses = new ArrayList<>();
        }
        enrolledClasses.addAll(SchoolClasses);
    }

}
