package com.hvost.activepeople.support;

import com.hvost.activepeople.Answer;
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

  @Autowired
  AnswerRepository answerRepository;

  public Page<Questions> getAll(Pageable pageRequest){

    return questionRepository.findAll(pageRequest);
  }

/*  public Page<Questions> getPublished(Answer a, Pageable pageRequest){

     return questionRepository.findByAnswerId(pageRequest);
  }*/

  public Page<Answer> getPublished1(Pageable pageRequest){
    return answerRepository.findByPublished(pageRequest);
  }


  public void addNewQuestion(Questions questions) {
    questionRepository.save(questions);
  }
}
