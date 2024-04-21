package com.edusenior.project.dataAccessObjects.classes;

import com.edusenior.project.entities.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolClassDAO extends JpaRepository<SchoolClass,String> {
    public SchoolClass getClassById(String id);
    public void persistClass(SchoolClass c);
}
