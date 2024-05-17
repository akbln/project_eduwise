package com.edusenior.project.dataTransferObjects;

import com.edusenior.project.entities.SchoolClass;

import java.util.ArrayList;

public class FetchAllStudentClassesDTO {

    private ArrayList<ClassDTO> allClasses;

    public ArrayList<ClassDTO> getAllClasses() {
        return allClasses;
    }

    public void setAllClasses(ArrayList<ClassDTO> allClasses) {
        this.allClasses = allClasses;
    }
}
