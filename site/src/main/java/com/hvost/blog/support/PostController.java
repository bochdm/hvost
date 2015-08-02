package com.hvost.blog.support;

import com.hvost.blog.CategoryPost;
import com.hvost.blog.Post;
import com.hvost.model.Category;
import com.hvost.support.PaginationInfo;
import com.hvost.support.navigation.Navigation;
import com.hvost.support.navigation.Section;
import com.twitter.Autolink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.social.support.URIBuilder;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TweetData;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.print.Pageable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

  private static final Logger logger = LoggerFactory.getLogger(PostController.class);


  @Autowired
  private Twitter twitter;

  //@RequestMapping(method = {GET, POST})
  @RequestMapping(value = "", method = {GET, POST})
  public String listBlogs(HttpServletRequest request, Model model, @RequestParam(defaultValue = "1") int page) {
    //Pageable pageRequst = PageableFactory
    System.out.println("PostController");
    PageRequest pageNum = new PageRequest(page - 1, 10, Sort.Direction.DESC, "createdAt");

    //   Page<Post> result = postService.getAllPost(page);
    //  model.addAttribute("articles", result);

    Page<Post> result = postService.getAll(pageNum);

    URL url = null;
    try {
      url = new URL(request.getScheme(), request.getServerName(), request.getServerPort(), request.getContextPath());
    }catch (MalformedURLException mue){
      mue.printStackTrace();
    }

    model.addAttribute("baseUrl", url.toString());

    System.out.println("baseUrl->" + url);

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
    return "/blog";
    //    return renderListPosts(result, model);
  }

  @RequestMapping(value = "/{id:\\d+}", method = {GET})
  public String showPost(Model model, @PathVariable Long id) {
    System.out.println("id_post = " + id);
    Post post = postService.getPost(id);
    model.addAttribute("post", post);
    return "/blog/blog_single_post";
  }

  @RequestMapping(value = "/tweet/{id:\\d+}")
  public String sendTweet(Model model, @PathVariable Long id) throws UnsupportedEncodingException {
    Post post = postService.getPost(id);
    //String tw = new String(post.getContent().substring(0,115));
    String tw = new String("Тестовое сообщение в твиттер".getBytes("UTF-8"), "ISO-8859-1");
    System.out.println("tw -> " + tw);
    TweetData tweetData = new TweetData(tw);
    try {
      UrlResource resource = new UrlResource("http://www.w3schools.com/html/pic_mountain.jpg");
      tweetData.withMedia(resource);
    } catch (MalformedURLException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
    model.addAttribute("post", post);
    twitter.timelineOperations().updateStatus(tweetData);
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

  @RequestMapping(value = "/search", method = {POST})
  public String search(Model model,
                       @RequestParam String q,
                       @RequestParam(defaultValue = "1") int page){
    System.out.println("PostController:search -> " + q);

    List<Post> result = postService.getPostBySearch(q, page);

    model.addAttribute("search_results", result);

    return "/blog/results";
  }


  private String renderListPosts(Page<Post> page, Model model) {

    model.addAttribute("articles", page);
    model.addAttribute("paginationInfo", new PaginationInfo(page));

    List<CategoryPost> categories = postService.getAllCategories();
    model.addAttribute("categories", categories);

    List<Tweet> tweets = twitter.timelineOperations().getUserTimeline("K_Tkhostov", 4);

   // List<Map<Long, String>> tweetList = new ArrayList<String>(3);
   // List<Map<Long, String>> tweetList = new ArrayList<Map<Long, String>>(3);
    Map<Long, String> tweetList = new HashMap<Long, String>(4);


    for (Tweet tweet : tweets){

      Autolink autolink = new Autolink();
      autolink.setUrlTarget("_blank");
      logger.info("autolink -> " + autolink.autoLink(tweet.getUnmodifiedText()));
     // System.out.println("tweet.id -> " + tweet;
      tweetList.put(tweet.getId(), autolink.autoLink(tweet.getUnmodifiedText()));

    }

    /*for(Map.Entry<Long, String> t: tweetList){
      t.getKey();
    }*/

    model.addAttribute("tweets", tweetList);

    return "/blog/blog_small";
  }
}
