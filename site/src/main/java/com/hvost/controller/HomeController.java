package com.hvost.controller;

import com.hvost.blog.support.PostService;
import com.hvost.model.Category;
import com.hvost.service.CategoryService;
import com.twitter.Autolink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.*;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by kseniaselezneva on 29/01/15.
 */
@Controller
@RequestMapping(value = {"", "/", "/index"})
public class HomeController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PostService postService;

    @Autowired
    private Twitter twitter;

    @Autowired
    private TwitterTemplate tt;
 //   private ConnectionRepository connectionRepository;

   /* @Inject
    public HomeController(Twitter twitter, ConnectionRepository connectionRepository){
        this.twitter=twitter;
        this.connectionRepository=connectionRepository;
    }*/

    @RequestMapping(value="/test", method = RequestMethod.GET)
    public String listAll(Model model){
      model.addAttribute("categories", categoryService.getAll());
      model.addAttribute("newest_posts", postService.getNewPosts());

      /*  if (connectionRepository.findPrimaryConnection(Twitter.class) != null){
            CursoredList<TwitterProfile> twitts = twitter.friendOperations().getFriends();
*/
        
        List<Tweet> twitts = tt.timelineOperations().getUserTimeline("Alexey_Pushkov");

        for(Tweet tw : twitts){
            System.out.println("tw->" + tw.getUnmodifiedText());
            System.out.println("tw->" + tw.getFromUser());
            System.out.println("tw->" + tw.getLanguageCode());
        }

        model.addAttribute("tweets", twitts);


      return "home";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String startPage(Model model){
        model.addAttribute("newest_posts", postService.getNewPosts());
        List<Tweet> twitts = tt.timelineOperations().getUserTimeline("K_Tkhostov", 4);

      List<String> tweets = new ArrayList<String>(4);

        for (Tweet tweet : twitts){
          String regex = "((https?):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
          Pattern p = Pattern.compile(regex);
          Matcher m = p.matcher(tweet.getUnmodifiedText());
          System.out.println("tweet before ->" + tweet.getUnmodifiedText());
          StringBuffer result = new StringBuffer();
          while (m.find()){
            String url = m.group();
            m.appendReplacement(result, getReplacement(m));
          }
          m.appendTail(result);

       /*    System.out.println("tweet after ->" + result.toString());

         Map<String,Object> map = tweet.getExtraData();
          for (Map.Entry<String,Object> mm :  map.entrySet())
            System.out.println("getExtraData - > " + mm.toString())*/;

          Entities e =  tweet.getEntities();
          System.out.println("e.toString ->" +  e.getMentions().toString());
          List<UrlEntity> listUrl = e.getUrls();
          for (UrlEntity ue: listUrl)
            System.out.println("ue ->" + ue.getDisplayUrl());

          Autolink autolink = new Autolink();
          autolink.setUrlTarget("_blank");
          System.out.println("autolink -> " + autolink.autoLink(tweet.getUnmodifiedText()));

          tweets.add(autolink.autoLink(tweet.getUnmodifiedText()));

        }



     //   model.addAttribute("tweets", twitts);
        model.addAttribute("tweets", tweets);
        return "/index";
    }

  @RequestMapping(value = "/aboutme", method = RequestMethod.GET)
  public String aboutMe(){
    return "/about/aboutme";
  }

  @RequestMapping(value = "/contacts", method = RequestMethod.GET)
  public String contacts(){
    return "/contacts/contacts";
  }

  private  String getReplacement(Matcher matcher){
    String prefix  = "<a target=\"_blank\" href=\"";
    String postfix = "\">" + matcher.group() + "</a>";
    String replace = prefix + matcher.group() + postfix;

    return replace;
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
