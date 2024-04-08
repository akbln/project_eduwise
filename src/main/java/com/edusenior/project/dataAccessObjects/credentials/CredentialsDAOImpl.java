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
    public String fetchPasswordHash(String id) {
        return em.find(Credentials.class,id).getHash();
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
