package com.hvost.activepeople.support;

import com.hvost.activepeople.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by kseniaselezneva on 08/03/15.
 */
public interface AnswerRepository extends JpaRepository<Answer, Long> {

  @Query("SELECT a FROM Answer a WHERE a.isPublic = 1")
  Page<Answer> findByPublished(Pageable pageable);
}
