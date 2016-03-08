package com.hvost.startpage.support;


import com.hvost.startpage.Carousel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.NamedNativeQuery;
import java.util.List;

/**
 * @author kseniaselezneva on 04/08/15
 */

public interface CarouselRepository extends JpaRepository<Carousel, Long> {

  @Query("select sc from Carousel sc where sc.active = true")
  List<Carousel> findByActive();

  @Query(value = "select * from start_carousel c where c.isactive = 1 and c.created_at < DATE_SUB(NOW(), INTERVAL 7 Day)", nativeQuery = true)
  List<Carousel> findOlderLastWeek();
}
