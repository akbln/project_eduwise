package com.edusenior.project.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "teachers",schema = "edusenior")
@PrimaryKeyJoinColumn(name = "user_id")
public class Teacher extends User{

    public Teacher(){
        super();
        this.role = "teacher";
    }

    @Column(name = "role",length = 50,nullable = false,updatable = true)
    private String role;


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
