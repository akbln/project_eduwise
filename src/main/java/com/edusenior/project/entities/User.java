package com.edusenior.project.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Blob;
import java.sql.Timestamp;

@MappedSuperclass
public class User {

    @Column(length = 100,nullable = false,updatable = false)
    private String name;

    @Column(nullable = false,updatable = false)
    private int age;

    @Column(length = 1,nullable = false,updatable = false)
    private char gender;

    @Lob
    @Column(name = "profile_picture")
    private Blob profilePicture;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String hash;

    @Column(name = "failed_login_attempts")
    private int failed;

    @Column (name = "locked_out_till")
    private Timestamp lockout;

    public User() {
        lockout = new Timestamp(System.currentTimeMillis()-1);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getFailed() {
        return failed;
    }

    public void setFailed(int failed) {
        this.failed = failed;
    }

    public Timestamp getLockout() {
        return lockout;
    }

    public void setLockout(Timestamp lockout) {
        this.lockout = lockout;
    }
}
