package com.hvost.controller;

import com.hvost.support.navigation.Navigation;
import com.hvost.support.navigation.Section;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by kseniaselezneva on 28/05/15.
 */

@Controller
@RequestMapping(value="/aboutme")
@Navigation(Section.ABOUTME)
public class AboutMeController {

  @RequestMapping(method = RequestMethod.GET)
  public String aboutMe(){
    return "/about/aboutme";
  }

}
