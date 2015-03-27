package com.hvost.blog.support;

import com.hvost.blog.CategoryPost;
import com.hvost.blog.Post;
import com.hvost.model.Category;
import com.hvost.support.PaginationInfo;
import com.hvost.support.navigation.Navigation;
import com.hvost.support.navigation.Section;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.print.Pageable;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by kseniaselezneva on 07/02/15.
 */
@Controller
@RequestMapping("/blog")
@Navigation(Section.BLOG)
public class PostController {

  @Autowired
  private PostService postService;


  @Autowired
  private Twitter twitter;

  //@RequestMapping(method = {GET, POST})
  @RequestMapping(value = "", method = {GET, POST})
  public String listBlogs(Model model, @RequestParam(defaultValue = "1") int page) {
    //Pageable pageRequst = PageableFactory
    System.out.println("PostController");
    PageRequest pageNum = new PageRequest(page - 1, 10, Sort.Direction.DESC, "createdAt");

    //   Page<Post> result = postService.getAllPost(page);
    //  model.addAttribute("articles", result);

    Page<Post> result = postService.getAll(pageNum);

    return renderListPosts(result, model);
    //return "/blog/blog_small";
  }

  @RequestMapping(value = "/all")
  public String getAll(Model model) {
    PageRequest pageRequest = new PageRequest(0, 10);
    Page<Post> result = postService.getAll(pageRequest);

    model.addAttribute("articles", result);
    model.addAttribute("paginationInfo", new PaginationInfo(result));

    //   model.addAttribute("articles", result);
    return "/blog/blog_small";
    //    return renderListPosts(result, model);
  }

  @RequestMapping(value = "/{id:\\d+}", method = {GET})
  public String showPost(Model model, @PathVariable Long id) {
    System.out.println("id_post = " + id);
    Post post = postService.getPost(id);
    model.addAttribute("post", post);
    return "/blog/blog_single_post";
  }

  @RequestMapping(value = "/category/{id:\\d+}", method = {GET})
  public String publishPostsForCategory(@PathVariable Long id, Model model,
                                        @RequestParam(defaultValue = "1") int page) {

    System.out.println("publishPostsForCategory");

    PageRequest pageNum = new PageRequest(page - 1, 10, Sort.Direction.DESC, "createdAt");

    Page<Post> result = postService.getPublishPostByCategory(id, pageNum);

    return renderListPosts(result, model);
  }


  private String renderListPosts(Page<Post> page, Model model) {

    model.addAttribute("articles", page);
    model.addAttribute("paginationInfo", new PaginationInfo(page));

    List<CategoryPost> categories = postService.getAllCategories();
    model.addAttribute("categories", categories);

    List<Tweet> tweets = twitter.timelineOperations().getUserTimeline("K_Tkhostov", 3);

    model.addAttribute("tweets", tweets);

    return "/blog/blog_small";
  }
}
