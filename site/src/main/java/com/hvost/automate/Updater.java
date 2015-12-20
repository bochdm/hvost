package com.hvost.automate;

import com.hvost.admin.AdminService;
import com.hvost.blog.Post;
import com.hvost.blog.support.PostService;
import com.hvost.startpage.Carousel;
import com.hvost.startpage.support.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by kseniaselezneva on 08/12/15.
 */
@Component
public class Updater {

  @Autowired
  CarouselService carouselService;

  @Autowired
  PostService postService;

  @Autowired
  AdminService adminService;

//  @Scheduled(cron = "0 0 0 * * *")
  @Scheduled(cron = "0 * * * * *")
  public void carouselToBlog(){
    System.out.println("Updater.carouselToBlog");

    List<Carousel> onlyActual = carouselService.getOnlyActual();

    for (Carousel carousel : onlyActual) {
      Post post = new Post();
      post.setTitle(carousel.getTitle().replaceAll("\\<.*?>", ""));
      post.setSummary(carousel.getContent().replaceAll("\\<.*?>", ""));
      post.setAuthor("admin");
      String link = carousel.getLink();
      if (link.contains("http")) {
        post.setContent(carousel.getContent().replaceAll("\\<.*?>", "") + "<br/><a href='" + link + "'/>");
      } else {
        post.setContent(carousel.getContent().replaceAll("\\<.*?>", ""));
      }
      adminService.addArticle(post);
      System.out.println("move to blog ->" + post);
      carousel.setActive(false);
      adminService.updateCarousel(carousel);
    }
  }
}
