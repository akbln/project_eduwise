package com.edusenior.project.dataAccessObjects.credentials;

import com.edusenior.project.entities.Users.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CredentialsJpaRepository extends JpaRepository<Credentials, String> {

    @Query("SELECT u.id FROM Credentials u WHERE u.email = ?1")
    String findUserIdByEmail(String email);

    boolean existsByEmail(String email);

    Credentials findByEmail(String email);

    @Query("SELECT u.role FROM Credentials u WHERE u.email = ?1")
    String getRoleByEmail(String email);

    @Query("SELECT c.id FROM Credentials c WHERE c.email IN :emails AND c.role = :role")
    List<String> findUserIdsByEmailsAndRole(@Param("emails") List<String> emails, @Param("role") String role);
}


