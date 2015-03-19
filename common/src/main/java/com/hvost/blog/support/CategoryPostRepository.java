package com.hvost.blog.support;

import com.hvost.blog.CategoryPost;
import com.hvost.blog.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by kseniaselezneva on 19/03/15.
 */
public interface CategoryPostRepository extends JpaRepository<CategoryPost, Long>{
}