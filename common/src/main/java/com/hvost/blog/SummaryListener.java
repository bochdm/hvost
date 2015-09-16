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
    String summary = p.getSummary();
    if (summary.length() > 500) {
      p.setSummary(summary.substring(0, 499));
    }
  }
}
