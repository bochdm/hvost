package com.hvost.blog.support;


import com.hvost.blog.CategoryPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

/**
 * Created by kseniaselezneva on 23/03/15.
 */
@Component
public class CategoryPostFormatter implements Formatter<CategoryPost> {

  @Autowired
  PostService postService;


  @Override
  public CategoryPost parse(String s, Locale locale) throws ParseException {
   // CategoryPost categoryPost = new CategoryPost();
   // categoryPost.setId(Integer.parseInt(s));
    CategoryPost categoryPost = postService.findCategoryPostById(Long.parseLong(s));
    System.out.println("CategoryPostFormatter:parse ->" + categoryPost);
    return categoryPost;
  }

  @Override
  public String print(CategoryPost categoryPost, Locale locale) {
    System.out.println("CategoryPostFormatter:print -> " + categoryPost.toString());
    return categoryPost.getId().toString();
  }
}
