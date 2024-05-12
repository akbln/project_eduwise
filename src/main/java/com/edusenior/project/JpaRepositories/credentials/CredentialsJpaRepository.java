package com.edusenior.project.JpaRepositories.credentials;

import com.edusenior.project.entities.Users.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;


public interface CredentialsJpaRepository extends JpaRepository<Credentials, String> {

    @Query("SELECT u.id FROM Credentials u WHERE u.email = ?1")
    String findUserIdByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Credentials> findByEmail(String email);

    @Query("SELECT u.role FROM Credentials u WHERE u.email = ?1")
    String getRoleByEmail(String email);

    @Query("SELECT c.id FROM Credentials c WHERE c.email IN :emails AND c.role = :role")
    List<String> findUserIdsByEmailsAndRole(@Param("emails") List<String> emails, @Param("role") String role);

    @Query("Select c.email FROM Credentials c WHERE c.email IN :emails AND c.role = 'student'")
    HashSet<String> findExistingStudentEmailsByEmails(List<String> emails);
}


