package com.hvost.dao;

import con.hvost.model.Category;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by kseniaselezneva on 27/01/15.
 */
public class CategoryDAOImpl implements CategoryDAO {

    private SessionFactory sessionFactory;

    public CategoryDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Category> list() {
        List<Category> listCategory = sessionFactory.getCurrentSession()
                .createCriteria(Category.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        return listCategory;
    }
}
