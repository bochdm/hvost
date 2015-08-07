package com.hvost.indexer;

import com.hvost.indexer.support.PublishedPostIndexer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by kseniaselezneva on 06/08/15.
 */
@Component
public class IndexScheduler {
  private static final long ONE_HOUR = 1000 * 60 * 60;
  private static final long ONE_MINUTE = 1000 * 60 * 5;
  private static final long ONE_DAY = ONE_HOUR * 24;

  @Autowired
  PublishedPostIndexer publishedPostIndexer;

  @Scheduled(fixedDelay = ONE_MINUTE)
  public void indexPublishedPost(){
    System.out.println("IndexScheduler->indexPublishedPost");
    publishedPostIndexer.indexUpdater();
  }

 /* @Scheduled(fixedDelay = ONE_HOUR)
  public void indexQuestions(){

  }

  @Scheduled(fixedDelay = ONE_DAY)
  public void indexVideoArchive(){

  }*/


}
