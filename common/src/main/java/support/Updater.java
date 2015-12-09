package support;

import com.hvost.blog.support.PostService;
import com.hvost.startpage.support.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by kseniaselezneva on 08/12/15.
 */
@Component
public class Updater {

  @Autowired
  CarouselService carouselService;

  @Autowired
  PostService postService;


  @Scheduled(cron = "0 0 0 * * *")
  public void carouselToBlog(){

    carouselService.getOnlyActual();

//    carouselService


  }
}
