package com.edusenior.project.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "course_id", nullable = false,updatable = false)
    private String id;



    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<SchoolClass> allSchoolClasses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SchoolClass> getAllSchoolClasses() {
        return allSchoolClasses;
    }

    public void addSchoolClass(SchoolClass schoolClass) {
        if(this.allSchoolClasses == null){
            this.allSchoolClasses = new ArrayList<>();
        }
        allSchoolClasses.add(schoolClass);
    }
}

