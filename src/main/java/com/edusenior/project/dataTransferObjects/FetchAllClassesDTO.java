package com.edusenior.project.dataTransferObjects;

import java.util.ArrayList;

public class FetchAllClassesDTO {

    private ArrayList<ClassDTO> allClasses;

    public ArrayList<ClassDTO> getAllClasses() {
        return allClasses;
    }

    public void setAllClasses(ArrayList<ClassDTO> allClasses) {
        this.allClasses = allClasses;
    }
}
