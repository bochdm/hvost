package com.hvost.activepeople.support;

import com.hvost.activepeople.Questions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Created by kseniaselezneva on 04/03/15.
 */
@Service
public class ActivePeopleService {

  private int size = 25;

  @Autowired
  QuestionRepository questionRepository;

  public Page<Questions> getAll(Pageable pageRequest){

    return questionRepository.findAll(pageRequest);
  }
}
