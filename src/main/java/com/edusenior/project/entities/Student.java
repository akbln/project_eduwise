package com.edusenior.project.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Blob;
import java.util.UUID;

@Entity
@Table(name = "student_table", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class Student {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "student_id", nullable = false,updatable = false)
    private String id;

    @Column(length = 100,nullable = false,updatable = false)
    private String name;

    @Column(nullable = false,updatable = false)
    private int age;

    @Column(length = 1,nullable = false,updatable = false)
    private char gender;

    @Lob
    @Column(name = "profile_picture")
    private Blob profilePicture;

    @Column(name = "educational_level",length = 50,nullable = false,updatable = true)
    private String level;

    @OneToOne(mappedBy = "student",cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private Credentials credentials;

    public Student(){}
    public Student(String name, int age, char gender, Blob profilePicture, String level) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.profilePicture = profilePicture;
        this.level = level;
    }

    public Student(String name, int age, char gender, String level) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.level = level;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public Blob getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Blob profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", profilePicture=" + profilePicture +
                ", level='" + level + '\'' +
                '}';
    }
}
