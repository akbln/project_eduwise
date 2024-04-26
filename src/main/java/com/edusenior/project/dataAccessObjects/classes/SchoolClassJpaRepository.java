package com.edusenior.project.dataAccessObjects.classes;

import com.edusenior.project.entities.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchoolClassJpaRepository extends JpaRepository<SchoolClass,String> {
}
