package com.hvost.admin;

import com.hvost.blog.Post;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

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
    public void addArticle(Post a){
        em.persist(a);
    }

    @Transactional
    public List<Post> getAll() {
      List<Post> result = em.createQuery("SELECT a FROM Article a", Post.class).getResultList();

      for(Post p : result)
        System.out.println("a -> " + p);

      return result;
    }
}
