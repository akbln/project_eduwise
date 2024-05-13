package com.edusenior.project.JpaRepositories;

import com.edusenior.project.entities.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionJpaRepository extends JpaRepository<Question,String> {
    @Query(value = "SELECT * FROM questions WHERE chapter_id = :chapterId",
            countQuery = "SELECT count(*) FROM questions WHERE chapter_id = :chapterId",
            nativeQuery = true)
    Page<Question> findByChapterIdAndIndex(@Param("chapterId") String chapterId, Pageable pageable);

}
