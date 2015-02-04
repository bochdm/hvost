package com.hvost.admin;

import com.hvost.article.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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

  @Autowired
  private AdminService adminService;

  @RequestMapping(value="/newarticle", method = RequestMethod.GET)
  public String showArticle(Model model){
    model.addAttribute("article", new Article());
    return "admin/articles";
  }

  @RequestMapping(value = "/listarticle", method = RequestMethod.GET)
  public String allArticles(Model model){
    model.addAttribute("articles", adminService.getAll());

    return "home";
  }

  @RequestMapping(value="/addarticle", method = RequestMethod.POST)
  public String addArticle(@ModelAttribute Article article){
    //model.addAttribute("categories", categoryService.getAll());
    adminService.addArticle(article);
    return "redirect:/home/test";
  }



}
