package com.hvost.activepeople.support;

import com.hvost.activepeople.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


/**
 * Created by kseniaselezneva on 04/03/15.
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {

 // Page<Questions> findByAnswerId(Answer isPublic, Pageable pageable);

  //@Query("SELECT q FROM Questions q INNER JOIN Answer a INNER JOIN CategoryQuestion cq WHERE q  and a.isPublic = 1")
  //@Query("SELECT q FROM Questions q WHERE q.answerId.isPublic = 1")
 // Page<Questions> findByAnswerId(Pageable pageable);

//  @Query(value = "SELECT q FROM Question q INNER JOIN Answer a WHERE q.id = a.qst_sqt_id", nativeQuery = true)
  @Query(value = "SELECT q FROM Question q WHERE q.isShow = 1 AND  q NOT IN (SELECT a.question FROM Answer a)")
  Page<Question> findAllUnswered(Pageable pageable);

}
