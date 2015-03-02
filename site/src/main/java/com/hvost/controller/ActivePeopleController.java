package com.hvost.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by kseniaselezneva on 02/03/15.
 */
@Controller
@RequestMapping(value = "/activepeople")
public class ActivePeopleController {

  @RequestMapping(method = RequestMethod.GET)
  public String start(){

    return "/activepeople/activepeople";
  }
}
