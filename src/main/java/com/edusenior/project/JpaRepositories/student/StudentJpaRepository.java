package com.edusenior.project.JpaRepositories.student;



import com.edusenior.project.entities.Question;
import com.edusenior.project.entities.Users.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface StudentJpaRepository extends JpaRepository<Student,String> {
    @Query("SELECT s FROM Student s WHERE s.id IN :studentIds")
    List<Student> findAllById(List<String> studentIds);

    @Query("SELECT q FROM Question q WHERE q.chapter.id = :chapterId AND q.id NOT IN (SELECT sb.question.id FROM ChapterQuestionSubmissions sb WHERE sb.student.id = :sid)")
    Set<Question> findUnsubmittedQuestionsByChapterAndStudent(@Param("chapterId") String chapterId, @Param("sid") String studentId);

}
