package com.hvost.activepeople.support;

import com.hvost.activepeople.Answer;
import com.hvost.activepeople.Questions;
import com.hvost.blog.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by kseniaselezneva on 04/03/15.
 */
public interface QuestionRepository extends JpaRepository<Questions, Long> {

 // Page<Questions> findByAnswerId(Answer isPublic, Pageable pageable);

  //@Query("SELECT q FROM Questions q INNER JOIN Answer a INNER JOIN CategoryQuestion cq WHERE q  and a.isPublic = 1")
  @Query("SELECT q FROM Questions q WHERE q.answerId.isPublic = 1")
  Page<Questions> findByAnswerId(Pageable pageable);


}
