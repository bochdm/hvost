package com.hvost.archive.support;

import com.hvost.activepeople.Answer;
import com.hvost.archive.Archive;
import com.hvost.blog.Post;
import com.hvost.search.SearchResult;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kseniaselezneva on 05/07/15.
 */
@Service
public class ArchiveService {
  private int size = 25;

  @PersistenceContext
  EntityManager em;

  @Autowired
  ArchiveRepository archiveRepository;

  public Page<Archive> getAllArchives(Pageable pageRequest){
    return archiveRepository.findAll(pageRequest);

  }

  public Archive getArchive(Long id){
    Archive archive = archiveRepository.findOne(id);
    if (archive != null){
      return archive;
    }
    return null;
  }

  @Transactional
  public List<SearchResult> getArchiveBySearch(String queryString, int page) {
    FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
    QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Archive.class).get();

    Query luceneQuery = qb.keyword()
        .onFields("title", "content", "summary")
        .matching(queryString)
        .createQuery();

    javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Archive.class);
    List<Archive> result = jpaQuery.getResultList();

    SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<code>", "</code>");
    QueryScorer qs = new QueryScorer(luceneQuery);

    Highlighter highlighter = new Highlighter(formatter, qs);

    List<SearchResult> searchResults = new ArrayList<>();

    for (Archive video : result) {
//      System.out.println("search video -> " + video);
      String findResult = "";
      Analyzer analyzer = new RussianAnalyzer();
      SearchResult res = new SearchResult(video.getId());
      res.setCreatedAt(video.getCreatedAt());
      try {

        findResult = highlighter.getBestFragment(analyzer, "content", video.getContent());
        if (findResult != null && !findResult.isEmpty()) {
          res.setContent(findResult);
        //  continue;
        }

        findResult = highlighter.getBestFragment(analyzer, "summary", video.getSummary());
        if (findResult != null && !findResult.isEmpty()) {
          res.setContent(findResult);
        //  continue;
        }

        findResult = highlighter.getBestFragment(analyzer, "title", video.getTitle());
        if (findResult != null && !findResult.isEmpty()) {
          res.setTitle(findResult);
        } else {
          res.setTitle(video.getTitle());
        }

        System.out.println("res ->" + res);
        searchResults.add(res);
      }catch (InvalidTokenOffsetsException e){
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return searchResults;

  }
}
