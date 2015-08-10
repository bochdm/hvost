package com.hvost.blog.support;


import com.hvost.blog.CategoryPost;
import com.hvost.blog.Post;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.hibernate.SessionFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dmitry.Bochkanov
 * Date: 2/5/15
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class PostService {

  private final int PAGE_SIZE = 25;

  @PersistenceContext
  private EntityManager em;

  @Autowired
  private EntityManagerFactory emf;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CategoryPostRepository categoryPostRepository;

  private static final Logger logger = LoggerFactory.getLogger(PostService.class);


    public Page<Post> getAllPost(int pageNumber){
        PageRequest pageRequest = new PageRequest(pageNumber, PAGE_SIZE, Sort.Direction.DESC, "id");
        Page<Post> pagePost = postRepository.findAll(pageRequest);
        for(Post p : pagePost)
            System.out.println("page- > " + p.toString());
        return postRepository.findAll(pageRequest);
    }

  public List<CategoryPost> getAllCategories(){
    return categoryPostRepository.findAll();
  }

  public CategoryPost findCategoryPostById(Long id){
    return categoryPostRepository.findOne(id);
  }

    public Page<Post> getAll(Pageable pageRequest){
        return postRepository.findAll(pageRequest);
    }

    public Post getPost(Long postId){
        Post post = postRepository.findOne(postId);
        if (post != null)
            return post;
        return null;
    }

  public Page<Post> getPublishPostByCategory(Long category, Pageable pageRequest){
    return postRepository.findByCategoryPost_Id(category, pageRequest);
  }

    public Page<Post> getNewPosts(){
        //List<Post> posts = postRepository.getNewestPost();
        Pageable page = new PageRequest(0, 3, Sort.Direction.DESC, "createdAt");
        System.out.println("getNewPosts");
      //  Page<Post> posts = postRepository.findTop10OrderByCreatedAtOrderByCreatedAtDesc(page);
     //   List<Post> p = postRepository.getNewestPosts();

        Page<Post> pp = postRepository.findAll(page);

      for (Post p :pp){
        logger.info("getNewPosts -> " + p);
      }
        return pp;
/*        for (Post p :posts){
            System.out.println("p->" + p);
        }
        if (posts != null)
            return posts;
        return null;*/
    }

/*  public List<Post> getPostBySearch(String queryString, int page){
    return null;
  }*/

@Transactional
  public List<Post> getPostBySearch(String queryString, int page) {

  List<Post> posts = new ArrayList<Post>();


    FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);




  //  em.getTransaction().begin();

    QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Post.class).get();

    Query luceneQuery = qb.keyword()
        .onFields("summary", "author", "content", "title")
        .matching(queryString)
        .createQuery();

    javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Post.class);

 //   jpaQuery.setFirstResult((page - 1) * PAGE_SIZE);
 //   jpaQuery.setMaxResults(PAGE_SIZE);
    List<Post> result = jpaQuery.getResultList();
    System.out.println("search count ->" + jpaQuery.getResultList().size());

    SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<code>", "</code>");
    QueryScorer qs = new QueryScorer(luceneQuery);

    Highlighter highlighter = new Highlighter(formatter, qs);

    for (Post post : result) {
      String findResult = "";
      Analyzer analyzer = new RussianAnalyzer();
      Post searchPost = new Post(post);
      System.out.println("search post ->" + searchPost);
      try {
      //  searchPost.setAuthor(post.getAuthor());
      //  searchPost.setCreatedAt(post.getCreatedAt());
      //  searchPost.setCategoryPost(post.getCategoryPost());
        //post.content
        findResult = highlighter.getBestFragment(analyzer, "content", post.getContent());
        if (findResult != null && !findResult.isEmpty()) {
          searchPost.setContent(findResult);
        }
        //post.summary
        findResult = highlighter.getBestFragment(analyzer, "summary", post.getSummary());
        if (findResult != null && !findResult.isEmpty()) {
          searchPost.setSummary(findResult);
        }
        //post.title
        findResult = highlighter.getBestFragment(analyzer, "title", post.getTitle());
        if (findResult != null && !findResult.isEmpty()) {
          searchPost.setTitle(findResult);
        }

        System.out.println("findResult ->" + findResult);
        posts.add(searchPost);
      }catch (InvalidTokenOffsetsException e){
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
      System.out.println(post);
    }

 //   em.getTransaction().commit();
    return posts;
  }
}
