package com.hvost.service;

import com.hvost.model.Category;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by kseniaselezneva on 29/01/15.
 */
@Service
public class CategoryService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public List<Category> getAll(){
        List<Category> result = em.createQuery("SELECT c FROM Category c", Category.class).getResultList();

        for (Category r: result)
            System.out.println("r->" + r);

        return result;
    }

    @Transactional
    public void add(Category c){
        em.persist(c);
    }
}
