package com.edusenior.project.JpaRepositories.classes;

import com.edusenior.project.entities.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolClassJpaRepository extends JpaRepository<SchoolClass,String> {
}
