package com.edusenior.project.JpaRepositories;

import com.edusenior.project.entities.ChapterQuestionSubmissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChapterQuestionSubmissionsJpaRepo extends JpaRepository<ChapterQuestionSubmissions,String> {
    @Query("SELECT c FROM ChapterQuestionSubmissions c WHERE c.question.chapter.id = :chapterId AND c.student.id = :studentId")
    List<ChapterQuestionSubmissions> findByChapterIdAndStudentId(@Param("chapterId") String chapterId, @Param("studentId") String studentId);
}
