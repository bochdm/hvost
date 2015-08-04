package com.hvost.activepeople.support;

import com.hvost.activepeople.Answer;
import com.hvost.activepeople.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

  public Page<Question> getAll(Pageable pageRequest){

    return questionRepository.findAll(pageRequest);
  }

/*  public Page<Questions> getPublished(Answer a, Pageable pageRequest){

     return questionRepository.findByAnswerId(pageRequest);
  }*/

  public Page<Answer> getPublished1(Pageable pageRequest){
    return answerRepository.findByPublished(pageRequest);
  }

  public Page<Answer> getLatestPublished(){
    Pageable page = new PageRequest(0, 2, Sort.Direction.DESC, "date");
    return answerRepository.findByPublished(page);
  }

  public void addNewQuestion(Question question) {
    questionRepository.save(question);
  }
}
