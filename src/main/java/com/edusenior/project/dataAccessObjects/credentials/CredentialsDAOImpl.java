package com.edusenior.project.dataAccessObjects.credentials;

import com.edusenior.project.entities.Users.Credentials;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CredentialsDAOImpl implements CredentialsDAO{

    private EntityManager em;

    @Autowired
    public CredentialsDAOImpl(EntityManager em){
        this.em = em;
    }

    @Override
    public String getUserIdByEmail(String email) {
        try{
            return em.createQuery("SELECT u.id FROM Credentials u WHERE u.email = :email",String.class)
                    .setParameter("email",email)
                    .getSingleResult();
        }
        catch (NoResultException ex){
            return null;
        }
    }
    @Override
    public boolean checkIfEmailExists(String email) {
        try {
            final String queryStr = "SELECT u.email FROM Credentials u WHERE u.email = :email";
            em.createQuery(queryStr)
                    .setParameter("email", email)
                    .getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }
    @Override
    public Credentials getByEmail(String email){
        try{
            return em.createQuery("SELECT c FROM Credentials c WHERE c.email = :email",Credentials.class)
                    .setParameter("email",email)
                    .getSingleResult();
        }catch (NoResultException ex){
            return null;
        }
    }
    @Override
    public void persistChange(Credentials credentials){
        em.persist(credentials);
    }
    public String getRoleByEmail(String email){
        try{
            return em.createQuery("SELECT c.role FROM Credentials c WHERE c.email = :email",String.class)
                    .setParameter("email",email)
                    .getSingleResult();
        }catch (NoResultException ex){
            return null;
        }
    }

}
