package com.hvost.activepeople.support;

import com.hvost.activepeople.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author kseniaselezneva
 */
public interface AnswerRepository extends JpaRepository<Answer, Long> {

  @Query("SELECT a FROM Answer a WHERE a.isPublic = true")
  Page<Answer> findByPublished(Pageable pageable);


  @Query("SELECT a FROM Answer a WHERE a.isPublic = true and a.question.type = :type")
  Page<Answer> findByPublishedAndType(Pageable pageable, @Param("type") int type);

}
