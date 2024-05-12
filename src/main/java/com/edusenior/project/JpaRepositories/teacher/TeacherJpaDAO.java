package com.edusenior.project.JpaRepositories.teacher;

import com.edusenior.project.entities.Users.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeacherJpaDAO extends JpaRepository<Teacher,String> {
}
