package com.hvost.startpage.support;


import com.hvost.startpage.Carousel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by kseniaselezneva on 04/08/15.
 */
@Service
public class CarouselService {

  @Autowired
  CarouselRepository carouselRepository;

  public List<Carousel> getAll(){
    return carouselRepository.findAll();
  }
}
