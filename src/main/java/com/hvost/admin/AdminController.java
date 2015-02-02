package com.hvost.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created with IntelliJ IDEA.
 * User: Dmitry.Bochkanov
 * Date: 2/2/15
 * Time: 8:43 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value="/admin")
public class AdminController {

  @RequestMapping(value="/addarticle", method = RequestMethod.GET)
  public String addArticle(Model model){
    //model.addAttribute("categories", categoryService.getAll());

    return "admin/articles";
  }

}
