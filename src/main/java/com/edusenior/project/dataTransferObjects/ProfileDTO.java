package com.edusenior.project.dataTransferObjects;

import java.sql.Blob;
import java.sql.SQLException;

public class ProfileDTO {
    private String email;
    private String name;
    private byte[] profilePicture; // Change to byte array

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

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    // Convenience method to set Blob directly
    public void setProfilePicture(Blob profilePicture) {
        try {
            this.profilePicture = profilePicture.getBytes(1, (int) profilePicture.length());
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQLException
        }
    }
}
