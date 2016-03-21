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
 * @author kseniaselezneva
 */
@Service
public class ActivePeopleService {

  @Autowired
  QuestionRepository questionRepository;

  @Autowired
  AnswerRepository answerRepository;

  @PersistenceContext
  EntityManager em;

  /**
   *
   * @param type Категория вопроса:
   *             <ol>
   *               <li>Активный гражданин (1)</li>
   *               <li>Безопасный двор    (2)</li>
   *               <li>Комфортный двор    (3)</li>
   *               <li>Дворовый тренер    (4)</li>
   *             </ol>
   * @return Список всех неотвеченных вопросов
   */
  @Async
  public Future<Page<Question>> getAllVisibleUnaswered(int type){
    PageRequest pageNum = new PageRequest(0, 30, Sort.Direction.DESC, "date");

    Page<Question> allUnswered = questionRepository.findVisibleUnswered(pageNum, type);

    return new AsyncResult<Page<Question>>(allUnswered);
  }

  /**
   *
   * @param type Категория вопроса:
   *             <ol>
   *               <li>Активный гражданин (1)</li>
   *               <li>Безопасный двор    (2)</li>
   *               <li>Комфортный двор    (3)</li>
   *               <li>Дворовый тренер    (4)</li>
   *             </ol>
   * @return Список опубликованных ответов
   */
  @Async
  public Future<Page<Answer>> getAnswers(int type){
    PageRequest pageNum = new PageRequest(0, 30, Sort.Direction.DESC, "date");

    Page<Answer> byPublished = answerRepository.findByPublishedAndType(pageNum, type);

    return new AsyncResult<Page<Answer>>(byPublished);
  }


  /**
   *
   * @return Список всех вопросов
   */
  public List<Question> getAllQuestion(){
    return questionRepository.findAll();
  }

  public Page<Question> getAllUnansweredQuestions(Pageable pageRequest){
    return questionRepository.findAllUnswered(pageRequest);
  }

  public Page<Answer> getPublished1(Pageable pageRequest, int type){
    return answerRepository.findByPublished(pageRequest);
  }

  public Page<Answer> getPublishedAnswerByType(Pageable pageRequest, int type){
    return answerRepository.findByPublishedAndType(pageRequest, type);
  }

  public Page<Answer> getLatestPublished(){
    Pageable page = new PageRequest(0, 1, Sort.Direction.DESC, "date");
    return answerRepository.findByPublished(page);
  }

  public Question addNewQuestion(Question question) {
    return questionRepository.save(question);
  }

  @Transactional
  public List<Answer> getAnswerBySearch(String queryString) {
    FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
    QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Answer.class).get();

    Query luceneQuery = qb.keyword()
        .onFields("answerText", "author", "question.questionText")
        .matching(queryString)
        .createQuery();

    javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Answer.class);
    List<Answer> result = jpaQuery.getResultList();

    List<Answer> searchResults = new ArrayList<Answer>();

    String highLightStr = "<code>" + queryString + "</code>";

    for (Answer ans : result){
      String answerText = ans.getAnswerText().replaceAll(queryString, highLightStr);
      String questionText = ans.getQuestion().getQuestionText().replaceAll(queryString, highLightStr);

      Answer answer = new Answer(ans);
      answer.setAnswerText(answerText);
      answer.getQuestion().setQuestionText(questionText);
      searchResults.add(answer);
    }

    return searchResults;
  }
}
