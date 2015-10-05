package com.hvost.startpage;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kseniaselezneva on 05/10/15.
 */
@Component
public class NewCarouselListener {

  @PreUpdate
  @PrePersist
  public void prePersist(Carousel carousel){
    String title = carousel.getTitle();
    String content = carousel.getContent();

    // the pattern we want to search for
    Pattern p = Pattern.compile("<p>(\\w+)</p>", Pattern.MULTILINE);
   // Matcher m = p.matcher(stringToSearch);

    String titleNoHTML = title.replaceAll("\\<.*?>", "");
    String contentNoHTML = content.replaceAll("\\<.*?>", "");

    if (titleNoHTML.length() > 60) {
      carousel.setTitle(title.replace(titleNoHTML, titleNoHTML.substring(0, 59)));
    }
    if (contentNoHTML.length() > 200) {
      carousel.setContent(content.replace(contentNoHTML, contentNoHTML.substring(0, 199)));
    }

    System.out.println("nohtml -> " + titleNoHTML);
    System.out.println("nohtml -> " + contentNoHTML);

  }
}
