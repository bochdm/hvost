package com.hvost.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by kseniaselezneva on 29/05/15.
 */
@Controller
@RequestMapping(value = "/video")
public class VideoController {

  @RequestMapping(value = "/online",  method = {RequestMethod.GET})
  public String getOnline(Model model){
    return "/video/online";
  }

  @RequestMapping(value = "/archive",  method = {RequestMethod.GET})
  public String getArchive(Model model){
    return "/video/archive";
  }
}
