package com.hvost.indexer.support;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by kseniaselezneva on 06/08/15.
 */
@Service
public class PublishedPostIndexer {

  @PersistenceContext
  EntityManager em;

  public void indexUpdater(){
    FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);

    try {
      fullTextEntityManager.createIndexer().startAndWait();
      System.out.println("PublishedPostIndexer start");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
