package com.hvost.admin;

import com.hvost.activepeople.Answer;
import com.hvost.activepeople.Question;
import com.hvost.activepeople.support.AnswerRepository;
import com.hvost.activepeople.support.QuestionRepository;
import com.hvost.archive.Archive;
import com.hvost.archive.support.ArchiveRepository;
import com.hvost.blog.CategoryPost;
import com.hvost.blog.Post;
import com.hvost.blog.support.CategoryPostRepository;
import com.hvost.blog.support.PostRepository;
import com.hvost.startpage.Carousel;
import com.hvost.startpage.support.CarouselRepository;
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

  @Autowired
  CategoryPostRepository categoryPostRepository;

  @Autowired
  ArchiveRepository archiveRepository;

  @Autowired
  CarouselRepository carouselRepository;

    @Transactional
    public void addArticle(Post a){
        //em.persist(a);
      em.merge(a);
    }


  @Transactional
  public void updateAnswer(Answer a){
  //  answerRepository.save(a);

    em.merge(a);
  }

  @Transactional
  public void addReply(Answer a){
    em.persist(a);
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
  public void addArchiveVideo(Archive a){
    em.merge(a);
  }

  @Transactional
  public void updateArchiveVideo(Archive a){
    em.merge(a);
  }

  @Transactional
  public void deleteArchiveVideo(Archive a){
    em.remove(em.contains(a) ? a :em.merge(a));
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


  public Page<Archive> getAllArchive(Pageable pageRequest){
   return archiveRepository.findAll(pageRequest);
  }

  public Archive getArchiveVideo(Long videoID){
    return archiveRepository.findOne(videoID);
  }

  public Question getQuestion(Long id){
    return questionRepository.findOne(id);
  }

  public List<Carousel> getAllCarousel(){
    return carouselRepository.findAll();
  }

  public Carousel getCarousel(Long carouselID){
    return carouselRepository.findOne(carouselID);
  }

  @Transactional
  public void addCarousel(Carousel a){
    em.merge(a);
  }

  @Transactional
  public void updateCarousel(Carousel a){
    em.merge(a);
  }

  @Transactional
  public void deleteCarousel(Carousel a){
    em.remove(em.contains(a) ? a :em.merge(a));
  }

  public List<CategoryPost> getAllCategoriesPost(){
    return categoryPostRepository.findAll();
  }
}
