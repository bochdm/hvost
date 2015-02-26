package com.hvost.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by kseniaselezneva on 26/02/15.
 */
@Controller
public class MainController {

  @RequestMapping("/login.html")
  public String login(){
    return "login";
  }

  @RequestMapping("/login-error.html")
  public String loginError(Model model){
    model.addAttribute("loginError", true);
    return "login";
  }
}
