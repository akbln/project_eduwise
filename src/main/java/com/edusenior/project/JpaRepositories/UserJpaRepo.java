package com.edusenior.project.JpaRepositories;

import com.edusenior.project.entities.Users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepo extends JpaRepository<User,String> {
}
