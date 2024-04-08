package com.edusenior.project.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;

@Entity
@Table(name = "credentials_table",schema = "edusenior")
public class Credentials {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "student_id", nullable = false,updatable = false)
    private String id;

    @Column(name = "email", nullable = false,updatable = false)

    private String email;

    @Column(name = "password_hash", nullable = false)
    private String hash;

    @Column(name = "failed_login_attempts")
    private int failed;


    @Column (name = "locked_out_till")
    private Timestamp lockout;

    @Column(name = "role", nullable = false)
    private String role;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // This tells Hibernate to use the Student ID as both the PK and FK
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    private Student student;

    public Credentials(String email, String hash, int failed, Timestamp lockout) {
        this.lockout = lockout;
        this.failed = failed;
        this.email = email;
        this.hash = hash;
    }

    public Credentials() {
        this.lockout = new Timestamp(System.currentTimeMillis());
        this.failed = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getRole() {
        return role;
    }
}
