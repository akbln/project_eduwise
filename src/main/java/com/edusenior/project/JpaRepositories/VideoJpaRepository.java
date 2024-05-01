package com.edusenior.project.JpaRepositories;

import com.edusenior.project.entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoJpaRepository extends JpaRepository<Video,String> {

}
