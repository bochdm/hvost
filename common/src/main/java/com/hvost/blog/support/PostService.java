package com.hvost.blog.support;


import com.hvost.blog.CategoryPost;
import com.hvost.blog.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    public Page<Post> getAll(Pageable pageRequest){
        return postRepository.findAll(pageRequest);
    }

    public Post getPost(Long postId){
        Post post = postRepository.findOne(postId);
        if (post != null)
            return post;
        return null;
    }

    public Page<Post> getNewPosts(){
        //List<Post> posts = postRepository.getNewestPost();
        Pageable page = new PageRequest(0,2, Sort.Direction.DESC, "createdAt");
        System.out.println("getNewPosts");
      //  Page<Post> posts = postRepository.findTop10OrderByCreatedAtOrderByCreatedAtDesc(page);
     //   List<Post> p = postRepository.getNewestPosts();

        Page<Post> pp = postRepository.findAll(page);
        System.out.println("getNewPosts");
        return pp;
/*        for (Post p :posts){
            System.out.println("p->" + p);
        }
        if (posts != null)
            return posts;
        return null;*/
    }

}
