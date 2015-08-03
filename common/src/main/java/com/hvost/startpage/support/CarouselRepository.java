package com.hvost.startpage.support;


import com.hvost.startpage.Carousel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by kseniaselezneva on 04/08/15.
 */
public interface CarouselRepository extends JpaRepository<Carousel, Long> {
}
