package com.edusenior.project.dataAccessObjects.classes;

import com.edusenior.project.entities.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolClassJpaDAO extends JpaRepository<SchoolClass,String> {
}
