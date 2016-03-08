package com.hvost.blog.support;

import com.hvost.blog.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author kseniaselezneva
 */

public interface PostRepository extends JpaRepository<Post, Long> {

   // Page<Post> findTop10OrderByCreatedAtOrderByCreatedAtDesc(Pageable top);

    @Query("select a from Post a")
    List<Post> getNewestPosts();

  //  Page<Post> findTop2OrderByCreatedAtOrderByCreatedAtDesc();

 // Page<Post> findByCategory(Integer categoryPost, Pageable pageRequest);
  Page<Post> findByCategoryPost_Id(Long categoryPost, Pageable pageRequest);

}
