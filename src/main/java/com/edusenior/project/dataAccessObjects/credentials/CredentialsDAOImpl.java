package com.edusenior.project.dataAccessObjects.credentials;

import com.edusenior.project.entities.Credentials;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CredentialsDAOImpl implements CredentialsDAO{
    private EntityManager em;

    @Override
    public Credentials fetchCredential(String email) {
        return (Credentials) em.createQuery("SELECT c FROM Credentials c WHERE c.email = :email")
                .setParameter("email", email)
                .getSingleResult();
    }

    @Override
    @Transactional
    public void createCredential(Credentials credentials) {
        if (credentials != null) {
            em.persist(credentials);
        }
    }
    @Autowired
    public CredentialsDAOImpl(EntityManager em) {
        this.em = em;
    }

}
