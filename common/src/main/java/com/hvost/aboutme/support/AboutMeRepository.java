package com.hvost.aboutme.support;

import com.hvost.aboutme.AboutMe;
import com.hvost.startpage.Carousel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by kseniaselezneva on 14/11/15.
 */
public interface AboutMeRepository extends JpaRepository<AboutMe, Integer> {
  @Query("select sc from Carousel sc where sc.active = true")
  List<AboutMe> findByActive();
}
