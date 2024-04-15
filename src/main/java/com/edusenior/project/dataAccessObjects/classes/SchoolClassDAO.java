package com.edusenior.project.dataAccessObjects.classes;

import com.edusenior.project.entities.SchoolClass;

public interface SchoolClassDAO {
    public SchoolClass getClassById(String id);
    public void persistClass(SchoolClass c);
}
