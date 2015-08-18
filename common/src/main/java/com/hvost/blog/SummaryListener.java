package com.hvost.blog;

import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Created by kseniaselezneva on 18/08/15.
 */

@Component
public class SummaryListener {

  @PreUpdate
  @PrePersist
  void prePersist(Post p){
    System.out.println("before getSummary -> "+p.getSummary());
    p.setSummary(p.getSummary().substring(0, 499));
    System.out.println("after getSummary -> " + p.getSummary());

  }
}
