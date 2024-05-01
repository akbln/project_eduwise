package com.edusenior.project.JpaRepositories.courses;

import com.edusenior.project.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseJpaDAO extends JpaRepository<Course,String>{
}
