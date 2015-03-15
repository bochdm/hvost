package com.hvost.admin;

import com.hvost.activepeople.Answer;
import com.hvost.activepeople.Question;
import com.hvost.activepeople.support.AnswerRepository;
import com.hvost.activepeople.support.QuestionRepository;
import com.hvost.blog.Post;
import com.hvost.blog.support.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dmitry.Bochkanov
 * Date: 2/2/15
 * Time: 8:11 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class AdminService {

  private int size = 25;

    @PersistenceContext
    EntityManager em;

  @Autowired
  PostRepository postRepository;

  @Autowired
   AnswerRepository answerRepository;

  @Autowired
  QuestionRepository questionRepository;

    @Transactional
    public void addArticle(Post a){
        em.persist(a);
    }


  @Transactional
  public void updateAnswer(Answer a){
  //  answerRepository.save(a);

    em.merge(a);
  }

  /**
   * Обновляем статью
   */
  @Transactional
  public void updatePost(Post p){
    em.merge(p);
  }

  @Transactional
  public void deletePost(Post p){
    // em.remove(p);
    em.remove(em.contains(p) ? p : em.merge(p));
  }

    @Transactional
    public List<Post> getAllArticles() {
      List<Post> result = em.createQuery("SELECT a FROM Post a", Post.class).getResultList();

      for(Post p : result)
        System.out.println("a -> " + p);

      return result;
    }

    public Page<Answer> getAllAnswers(Pageable pageRequest){
    //  PageRequest pageRequest = new PageRequest(pageNumber-1, 10, Sort.Direction.DESC, "date");
     // Page<Answer> pageAnswer = answerRepository.findAll(pageRequest);
      return answerRepository.findAll(pageRequest);
    }


  public Page<Question> getAllUnansweredQuestions(Pageable pageRequest){

    return questionRepository.findAllUnswered(pageRequest);
  }

  public Answer getAnswer(Long ansId){
    return answerRepository.findOne(ansId);
  }

  public Page<Post> getAllPosts(Pageable pageRequest){
    return postRepository.findAll(pageRequest);
  }

  public Post getPost(Long postID) {
    return postRepository.findOne(postID);
  }


}
