package com.hvost.admin;

import com.hvost.article.Article;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: Dmitry.Bochkanov
 * Date: 2/2/15
 * Time: 8:11 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class AdminService {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void addArticle(Article a){
        em.persist(a);
    }
}
