package com.edusenior.project.dataAccessObjects.classes;

import com.edusenior.project.entities.SchoolClass;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SchoolClassDAOImpl implements SchoolClassDAO {

    private EntityManager em;

    @Autowired
    public SchoolClassDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public SchoolClass getClassById(String id) {
        return em.find(SchoolClass.class,id);
    }

    @Override
    public void persistClass(SchoolClass c) {
        em.persist(c);
    }
}
