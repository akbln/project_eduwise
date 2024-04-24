package com.edusenior.project.dataAccessObjects.teacher;

import com.edusenior.project.entities.Users.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherJpaDAO extends JpaRepository<Teacher,String> {

}
