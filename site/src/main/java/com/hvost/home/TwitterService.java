package com.hvost.home;

import com.twitter.Autolink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.social.twitter.api.Entities;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.UrlEntity;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kseniaselezneva on 08/08/15.
 */
@Service
public class TwitterService {

  @Autowired
  private TwitterTemplate tt;

  @Async
  public Future<List<String>> getTweets(){
    List<Tweet> twitts = tt.timelineOperations().getUserTimeline("K_Tkhostov", 10);

    List<String> tweets = new ArrayList<String>(10);

 /*   try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }*/

    for (Tweet tweet : twitts) {
      String regex = "((https?):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
      Pattern p = Pattern.compile(regex);
      Matcher m = p.matcher(tweet.getUnmodifiedText());
      StringBuffer result = new StringBuffer();
      while (m.find()) {
        String url = m.group();
        m.appendReplacement(result, getReplacement(m));
      }
      m.appendTail(result);

      Entities e = tweet.getEntities();
      System.out.println("e.toString ->" + e.getMentions().toString());
      List<UrlEntity> listUrl = e.getUrls();

      Autolink autolink = new Autolink();
      autolink.setUrlTarget("_blank");

      tweets.add(autolink.autoLink(tweet.getUnmodifiedText()));

    }
    return new AsyncResult<List<String>>(tweets);
  }

  private String getReplacement(Matcher matcher) {
    String prefix = "<a target=\"_blank\" href=\"";
    String postfix = "\">" + matcher.group() + "</a>";
    String replace = prefix + matcher.group() + postfix;

    return replace;
  }

}
