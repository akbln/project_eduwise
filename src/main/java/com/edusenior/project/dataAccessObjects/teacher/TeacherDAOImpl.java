package com.edusenior.project.dataAccessObjects.teacher;

import com.edusenior.project.dataTransferObjects.CredentialsDTO;
import com.edusenior.project.entities.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TeacherDAOImpl implements TeacherDAO {
    private EntityManager em;

    @Autowired
    public TeacherDAOImpl(EntityManager em) {
        this.em = em;
    }
    @Override
    public Teacher fetchTeacherById(String id) {
        return em.find(Teacher.class, id);
    }
    @Override
    public Teacher fetchTeacherByEmail(String email) {
        return em.createQuery("Select t from Teacher t where t.email = :email", Teacher.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    @Override
    public void createTeacher(Teacher t) {
        em.persist(t);
    }
    @Override
    public CredentialsDTO fetchCredentials(String email) {
        List<CredentialsDTO> results = em.createQuery("SELECT new com.edusenior.project.dataTransferObjects.CredentialsDTO(t.email, t.hash, t.lockout, t.failed)" +
                        "FROM Teacher t WHERE s.email = :email", CredentialsDTO.class)
                .setParameter("email", email)
                .getResultList();
        return results.isEmpty() ? null : results.getFirst();
    }
    @Override
    public boolean checkExistingEmail(String email) {
        try {
            final String queryStr = "SELECT t.email FROM Teacher t WHERE t.email = :email";
            em.createQuery(queryStr)
                    .setParameter("email", email)
                    .getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }
}
