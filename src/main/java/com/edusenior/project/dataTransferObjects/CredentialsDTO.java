package com.edusenior.project.dataTransferObjects;

import java.sql.Timestamp;

public class CredentialsDTO {
    private String email;
    private String hash;
    private Timestamp locked;
    private int failed;



    public CredentialsDTO() {}

    public CredentialsDTO(String email, String password, Timestamp locked, int failed) {
        this.email = email;
        this.hash = password;
        this.locked = locked;
        this.failed = failed;
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

    public Timestamp getLocked() {
        return locked;
    }

    public void setLocked(Timestamp locked) {
        this.locked = locked;
    }
}
