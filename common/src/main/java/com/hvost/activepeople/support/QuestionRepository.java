package com.hvost.activepeople.support;

import com.hvost.activepeople.Questions;
import com.hvost.blog.Post;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by kseniaselezneva on 04/03/15.
 */
public interface QuestionRepository extends JpaRepository<Questions, Long> {

}
