package com.hvost.blog.support;


import com.hvost.blog.CategoryPost;
import com.hvost.blog.Post;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
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

    private int size = 25;

  @PersistenceContext
  private EntityManager em;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CategoryPostRepository categoryPostRepository;


    public Page<Post> getAllPost(int pageNumber){
        PageRequest pageRequest = new PageRequest(pageNumber, size, Sort.Direction.DESC, "id");
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
        System.out.println("getNewPosts -> " + p);
      }
        return pp;
/*        for (Post p :posts){
            System.out.println("p->" + p);
        }
        if (posts != null)
            return posts;
        return null;*/
    }


  @Transactional
  public List<Post> getPostBySearch(String queryString) {

    System.out.println("getPostBySearch::" + queryString);

    FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);

    try {
      fullTextEntityManager.createIndexer().startAndWait();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
 //   em.getTransaction().begin();

    QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Post.class).get();

    Query luceneQuery = qb.keyword()
        .onFields("summary", "author", "content")
        .matching(queryString)
        .createQuery();

    javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Post.class);

    List<Post> result = jpaQuery.getResultList();

    for (Post post : result) {
      System.out.println(post);
    }
    return result;
  }
}
