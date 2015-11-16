package com.hvost.controller;

import com.hvost.aboutme.support.AboutMeService;
import com.hvost.admin.AdminService;
import com.hvost.support.navigation.Navigation;
import com.hvost.support.navigation.Section;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by kseniaselezneva on 28/05/15.
 */

@Controller
@RequestMapping(value="/aboutme")
@Navigation(Section.ABOUTME)
public class AboutMeController {

  @Autowired
  AdminService adminService;

  @Autowired
  private AboutMeService aboutMeService;

  @RequestMapping(method = RequestMethod.GET)
  public String aboutMe(Model model){
//    model.addAttribute("biography", adminService.getBiography());
    model.addAttribute("biografyBlocks", aboutMeService.getAllBlocksAboutMe());
    int[] counters = new int[]{1,2};
    model.addAttribute("counter", counters);
    return "/about/aboutme";
  }

}
