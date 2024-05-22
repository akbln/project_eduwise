package com.edusenior.project.JpaRepositories;

import com.edusenior.project.entities.CompSubmissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompSubmissionsJPA extends JpaRepository<CompSubmissions,String> {
    @Query(value = "SELECT COUNT(*) FROM comp_submissions WHERE question_id = :questionId and student_id = :studentId and comp_id = :compId", nativeQuery = true)
    int getNumberOfSubmissionsOfStudentForQuestion(@Param("questionId") String questionId,@Param("studentId") String studentId,@Param("compId") String compId);
}
