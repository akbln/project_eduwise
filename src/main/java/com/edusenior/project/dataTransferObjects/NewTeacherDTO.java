package com.edusenior.project.dataTransferObjects;

import jakarta.validation.constraints.*;

public class NewTeacherDTO {
    //Create input validation for all fields in the dto

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid email")
    @Size(max = 254,message = "Invalid email")
    private String email;

    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d!@#$%^&*_+\\-=]{8,60}$",
            message = "Invalid password")
    private String password;

    @NotNull
    @Size(min = 2, message = "Invalid name")
    @Size(max = 50, message = "Invalid name")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Invalid name")
    private String name;

    @Pattern(regexp = "^[MF]$", message = "Invalid Input")
    private String gender;

    @NotNull
    @Min(value = 5, message = "Invalid age")
    @Max(value = 100, message = "Invalid age")
    private int age;

    public NewTeacherDTO() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

