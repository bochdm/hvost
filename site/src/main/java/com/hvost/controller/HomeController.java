package com.hvost.controller;

import com.hvost.blog.support.PostService;
import com.hvost.model.Category;
import com.hvost.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.social.twitter.api.Twitter;

import javax.inject.Inject;


/**
 * Created by kseniaselezneva on 29/01/15.
 */
@Controller
@RequestMapping("")
public class HomeController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PostService postService;

    private Twitter twitter;
    private ConnectionRepository connectionRepository;

/*    @Inject
    public HomeController(Twitter twitter, ConnectionRepository connectionRepository){
        this.twitter=twitter;
        this.connectionRepository=connectionRepository;
    }*/

    @RequestMapping(value="/test", method = RequestMethod.GET)
    public String listAll(Model model){
      model.addAttribute("categories", categoryService.getAll());
      model.addAttribute("newest_posts", postService.getNewPosts());

/*        if (connectionRepository.findPrimaryConnection(Twitter.class) != null){
            CursoredList<TwitterProfile> twitts = twitter.friendOperations().getFriends();


        for(TwitterProfile tw : twitts){
            System.out.println("tw->" + tw.getName());
        }
    }*/

      return "home";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String startPage(Model model){
        model.addAttribute("newest_posts", postService.getNewPosts());
        return "/index";
    }

  @RequestMapping(value="/articles", method = RequestMethod.GET)
  public String listArticles(Model model){
    model.addAttribute("articles", categoryService.getAll());

    return "home";
  }

    @RequestMapping(value = "/addCategory", method = RequestMethod.POST)
    public String addCategory(@ModelAttribute Category category){
        categoryService.add(category);
        return "redirect:/";
    }
}
