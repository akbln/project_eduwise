package com.edusenior.project.JpaRepositories;

import com.edusenior.project.entities.Comp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompJpaRepo extends JpaRepository<Comp,String> {
}
