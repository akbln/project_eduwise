package com.edusenior.project.dataTransferObjects;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.sql.Blob;

public class CourseDTO {
    @Size(min = 1,max =100,message = "Invalid request")
    @NotNull
    @Pattern( regexp = "^(?! )[a-zA-Z0-9 ]{1,98}[a-zA-Z0-9](?<! )$",message = "Invalid request")
    private String name;

    @Size(min = 0,max =1000,message = "Invalid request")
    @Pattern( regexp = "^(?! )[a-zA-Z0-9 ][a-zA-Z0-9](?<! )$",message = "Invalid request")
    private String description;

    private Blob banner;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Blob getBanner() {
        return banner;
    }

    public void setBanner(Blob banner) {
        this.banner = banner;
    }
}
