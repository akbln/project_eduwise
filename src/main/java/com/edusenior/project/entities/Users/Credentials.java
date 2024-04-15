package com.edusenior.project.entities.Users;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "credentials", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class Credentials {

    @Id
    @Column(name = "user_id")
    private String id;


    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String hash;

    @Column(name = "failed_login_attempts")
    private int failed;

    @Column (name = "locked_out_till")
    private Timestamp lockout;

    @Column(name = "role")
    private String role;
    
    public Credentials(boolean setDefaultLockout) {
        if(setDefaultLockout){
            this.lockout = new Timestamp(System.currentTimeMillis()-1);
        }
    }
    public Credentials(){

    }

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }
    public int addToFailed(){
        failed++;
        return failed;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
