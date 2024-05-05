package com.edusenior.project.JpaRepositories;

import com.edusenior.project.entities.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChapterJpaRepository extends JpaRepository<Chapter,String> {
    @Query("SELECT ch FROM Chapter ch WHERE ch.course.name = :name and ch.number = :number")
    Optional<Chapter> findByCourseName(@Param("name") String name , @Param("number") int number);
}
