package com.hvost.startpage.support;


import com.hvost.startpage.Carousel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by kseniaselezneva on 04/08/15.
 */
public interface CarouselRepository extends JpaRepository<Carousel, Long> {

  @Query("select sc from Carousel sc where sc.active = true")
  List<Carousel> findByActive();
}
