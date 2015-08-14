package com.hvost.controller;

import com.hvost.activepeople.Answer;
import com.hvost.activepeople.Question;
import com.hvost.activepeople.support.ActivePeopleService;
import com.hvost.blog.support.PostService;
import com.hvost.home.TwitterService;
import com.hvost.model.Category;
import com.hvost.service.CategoryService;
import com.hvost.startpage.support.CarouselService;
import com.hvost.support.navigation.Navigation;
import com.hvost.support.navigation.Section;
import com.twitter.Autolink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.*;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

//import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by kseniaselezneva on 29/01/15.
 */
@Controller
@RequestMapping(value = {"", "/", "/index"})
@Navigation(Section.MAIN)
public class HomeController {

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private PostService postService;

  @Autowired
  private CarouselService carouselService;

  @Autowired
  private Twitter twitter;

  @Autowired
  private TwitterTemplate tt;

  @Autowired
  private ActivePeopleService activePeopleService;
  //   private ConnectionRepository connectionRepository;

   /* @Inject
    public HomeController(Twitter twitter, ConnectionRepository connectionRepository){
        this.twitter=twitter;
        this.connectionRepository=connectionRepository;
    }*/

  private static final Logger logger = LoggerFactory.getLogger(PostService.class);


  @Autowired
  private TwitterService twitterService;

  @RequestMapping(method = RequestMethod.GET)
  public String startPage(Model model, HttpSession session) {

    model.addAttribute("carousel", carouselService.getAll());
    model.addAttribute("newest_posts", postService.getNewPosts());

    Page<Answer> lap = activePeopleService.getLatestPublished();
    for(Answer a:lap){
      System.out.println("getLatestPublished -> " + a);
    }
    model.addAttribute("newest_answers", activePeopleService.getLatestPublished());
    model.addAttribute("question", new Question());


    Future<List<String>> asyncResult = twitterService.getTweets();

    session.setAttribute("tweets", asyncResult);

   /* List<String> tweets = null;
    try {
      Thread.sleep(10000);
      tweets = asyncResult.get();
      for(String s: tweets){
        System.out.println("asyncResult->" + s);
      }
 //     model.addAttribute("tweets", tweets);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }*/

/*
    List<Tweet> twitts = tt.timelineOperations().getUserTimeline("K_Tkhostov", 4);

    List<String> tweets = new ArrayList<String>(4);

    for (Tweet tweet : twitts) {
      String regex = "((https?):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
      Pattern p = Pattern.compile(regex);
      Matcher m = p.matcher(tweet.getUnmodifiedText());
      System.out.println("tweet before ->" + tweet.getUnmodifiedText());
      StringBuffer result = new StringBuffer();
      while (m.find()) {
        String url = m.group();
        m.appendReplacement(result, getReplacement(m));
      }
      m.appendTail(result);

      Entities e = tweet.getEntities();
      System.out.println("e.toString ->" + e.getMentions().toString());
      List<UrlEntity> listUrl = e.getUrls();
      for (UrlEntity ue : listUrl)
        System.out.println("ue ->" + ue.getDisplayUrl());

      Autolink autolink = new Autolink();
      autolink.setUrlTarget("_blank");
      System.out.println("autolink -> " + autolink.autoLink(tweet.getUnmodifiedText()));

      tweets.add(autolink.autoLink(tweet.getUnmodifiedText()));

    }
*/

    //   model.addAttribute("tweets", twitts);

    return "/index";
  }

  @RequestMapping(method = RequestMethod.GET,value = "/tweets")
  @ResponseBody
//  public String statusTwitter(Model model, HttpSession session){
  public ModelAndView statusTwitter(Model model, HttpSession session){
    Future<List<String>> asyncResult = (Future<List<String>>) session.getAttribute("tweets");

    if (asyncResult.isDone()){
      System.out.println("tweet is done");
      logger.info("legger::tweet is done");
      List<String> tweets = null;
      try {
        tweets = asyncResult.get();
        for(String s: tweets){
          System.out.println("asyncResult->" + s);
        }
        //     model.addAttribute("tweets", tweets);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (ExecutionException e) {
        e.printStackTrace();
      }
      model.addAttribute("tweets", tweets);
    //  return "COMPLETE";
      return new ModelAndView("/home/tweets");
   //   return "/home/tweets";
    }else {
      System.out.println("tweet is working");
  //    return "WORKING";
    }
    return new ModelAndView();

  }


  private String getReplacement(Matcher matcher) {
    String prefix = "<a target=\"_blank\" href=\"";
    String postfix = "\">" + matcher.group() + "</a>";
    String replace = prefix + matcher.group() + postfix;

    return replace;
  }

  @RequestMapping(value = "/articles", method = RequestMethod.GET)
  public String listArticles(Model model) {
    model.addAttribute("articles", categoryService.getAll());

    return "home";
  }

  @RequestMapping(value = "/addCategory", method = RequestMethod.POST)
  public String addCategory(@ModelAttribute Category category) {
    categoryService.add(category);
    return "redirect:/";
  }

  @RequestMapping(value = "/test", method = RequestMethod.GET)
  public String listAll(Model model) {
    model.addAttribute("categories", categoryService.getAll());
    model.addAttribute("newest_posts", postService.getNewPosts());

      /*  if (connectionRepository.findPrimaryConnection(Twitter.class) != null){
            CursoredList<TwitterProfile> twitts = twitter.friendOperations().getFriends();
*/

    List<Tweet> twitts = tt.timelineOperations().getUserTimeline("Alexey_Pushkov");

    for (Tweet tw : twitts) {
      System.out.println("tw->" + tw.getUnmodifiedText());
      System.out.println("tw->" + tw.getFromUser());
      System.out.println("tw->" + tw.getLanguageCode());
    }

    model.addAttribute("tweets", twitts);


    return "home";
  }
}
