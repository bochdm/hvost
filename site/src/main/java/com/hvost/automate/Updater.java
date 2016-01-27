package com.hvost.automate;

import com.hvost.admin.AdminService;
import com.hvost.blog.CategoryPost;
import com.hvost.blog.Post;
import com.hvost.blog.support.PostService;
import com.hvost.startpage.Carousel;
import com.hvost.startpage.support.CarouselService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by kseniaselezneva on 08/12/15.
 */
@Component
public class Updater {

  private static final Logger logger = LoggerFactory.getLogger(Updater.class);

  @Autowired
  CarouselService carouselService;

  @Autowired
  PostService postService;

  @Autowired
  AdminService adminService;

//  @Scheduled(cron = "0 0 0 * * *")
  @Scheduled(cron = "0 0/5 * * * *")
  public void carouselToBlog(){
    logger.info("Updater.carouselToBlog");

    List<Carousel> onlyActual = carouselService.getOnlyActual();

    for (Carousel carousel : onlyActual) {
      Post post = new Post();
      post.setTitle(carousel.getTitle().replaceAll("\\<.*?>", ""));
      post.setSummary(carousel.getContent().replaceAll("\\<.*?>", ""));
      post.setAuthor("admin");
      String link = carousel.getLink();
      if (link != null && link.contains("http")) {
        String buttonHtml = String.format("<br/><br/><a target='_blank' href='%s'class='btn btn-contrast btn-sm mg-b-md'>Перейти</a>", link);
        post.setContent(carousel.getContent().replaceAll("\\<.*?>", "") + buttonHtml);
      } else {
        post.setContent(carousel.getContent().replaceAll("\\<.*?>", ""));
      }
      adminService.addArticle(post);
      logger.info("move to blog {}", post);
      carousel.setActive(false);
      adminService.updateCarousel(carousel);
    }
  }
}
