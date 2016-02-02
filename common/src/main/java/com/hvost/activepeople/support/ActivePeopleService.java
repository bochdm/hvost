package com.hvost.activepeople.support;

import com.hvost.activepeople.Answer;
import com.hvost.activepeople.Question;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;


/**
 * Created by kseniaselezneva on 04/03/15.
 */
@Service
public class ActivePeopleService {

  private int size = 25;

  @Autowired
  QuestionRepository questionRepository;

  @Autowired
  AnswerRepository answerRepository;

  @PersistenceContext
  EntityManager em;

  @Async
  public Future<Page<Question>> getAllUnaswered(){
    PageRequest pageNum = new PageRequest(0, 30, Sort.Direction.DESC, "date");

    Page<Question> allUnswered = questionRepository.findAllUnswered(pageNum);
    return new AsyncResult<>(allUnswered);
  }

  @Async
  public Future<Page<Answer>> getAnswers(){
    PageRequest pageNum = new PageRequest(0, 30, Sort.Direction.DESC, "date");

    Page<Answer> byPublished = answerRepository.findByPublished(pageNum);
    return new AsyncResult<>(byPublished);
  }


  public Page<Question> getAll(Pageable pageRequest){

    return questionRepository.findAll(pageRequest);
  }

  public List<Question> getAllQuestion(){
    return questionRepository.findAll();
  }

  public Page<Question> getAllUnansweredQuestions(Pageable pageRequest){
    return questionRepository.findAllUnswered(pageRequest);
  }

/*  public Page<Questions> getPublished(Answer a, Pageable pageRequest){

     return questionRepository.findByAnswerId(pageRequest);
  }*/

  public Page<Answer> getPublished1(Pageable pageRequest){
    return answerRepository.findByPublished(pageRequest);
  }

  public Page<Answer> getLatestPublished(){
    Pageable page = new PageRequest(0, 1, Sort.Direction.DESC, "date");
    return answerRepository.findByPublished(page);
  }

  public Question addNewQuestion(Question question) {
    return questionRepository.save(question);
  }

  @Transactional
//  public List<SearchResult> getAnswerBySearch(String queryString, int page) {
  public List<Answer> getAnswerBySearch(String queryString, int page) {
    FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
    QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Answer.class).get();

    Query luceneQuery = qb.keyword()
        .onFields("answerText", "author", "question.questionText")
        .matching(queryString)
        .createQuery();

    javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Answer.class);
    List<Answer> result = jpaQuery.getResultList();

    for(Answer a : result){
      System.out.println("answer search -> " + a);
    }
//    List<SearchResult> searchResults = new ArrayList<>();
    List<Answer> searchResults = new ArrayList<Answer>();

    SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<code>", "</code>");
    QueryScorer qs = new QueryScorer(luceneQuery);

    Highlighter highlighter = new Highlighter(formatter, qs);

    String highLightStr = "<code>" + queryString + "</code>";

    for (Answer ans : result){
      String answerText = ans.getAnswerText().replaceAll(queryString, highLightStr);
      String questionText = ans.getQuestion().getQuestionText().replaceAll(queryString, highLightStr);

      Answer answer = new Answer(ans);
      answer.setAnswerText(answerText);
      answer.getQuestion().setQuestionText(questionText);
      searchResults.add(answer);
    }

  /*  for(Answer answer : result){
      String findResult = "";
      Analyzer analyzer = new RussianAnalyzer();
      SearchResult sr = new SearchResult(answer.getId());
      try {
        findResult = highlighter.getBestFragment(analyzer, "content", answer.getAnswerText());
        if (findResult != null && !findResult.isEmpty()) {
          sr.setContent(findResult);
          searchResults.add(sr);
        }
      } catch (IOException e) {
        e.printStackTrace();
      } catch (InvalidTokenOffsetsException e) {
        e.printStackTrace();
      }

    }*/

    return searchResults;
  }
}
