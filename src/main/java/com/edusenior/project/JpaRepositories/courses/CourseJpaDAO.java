package com.edusenior.project.JpaRepositories.courses;

import com.edusenior.project.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CourseJpaDAO extends JpaRepository<Course,String>{
    @Query("SELECT c from Course c WHERE c.name = :name")
    Optional<Course> findByName(@Param("name") String name);
}
