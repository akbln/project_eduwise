package com.edusenior.project.JpaRepositories;

import com.edusenior.project.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionJpaRepository extends JpaRepository<Question,String> {

}
