package com.hvost.admin;

import com.hvost.activepeople.Answer;
import com.hvost.blog.Post;
import com.hvost.support.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

/*  @RequestMapping(value = {"/", "/login", "/index"}, method = RequestMethod.GET)
  public String getIndex(){
      System.out.println("AdminController.getIndex");
     return "login";
  }*/

/*  @RequestMapping(method = RequestMethod.GET)
  public String index(Model model){
    model.addAttribute("article", new Post());
    return "admin/articles";
  }*/

  @RequestMapping(method = RequestMethod.GET)
  //@RequestMapping(value = {"/"}, method = RequestMethod.GET)
  public String index(){

    return "/admin/index";
  }

  @RequestMapping(value= {"/newarticle"}, method = RequestMethod.GET)
  public String showArticle(Model model){
    model.addAttribute("article", new Post());
    return "admin/articles";
  }

  @RequestMapping(value = "/listarticle", method = RequestMethod.GET)
  public String allArticles(Model model){
    model.addAttribute("articles", adminService.getAllArticles());

    return "home";
  }

  @RequestMapping(value="/addarticle", method = RequestMethod.POST)
  public String addArticle(@ModelAttribute Post post){
    //model.addAttribute("categories", categoryService.getAllArticles());
    adminService.addArticle(post);
    return "redirect:/home/test";
  }

  @RequestMapping(value = "/allanswers", method = RequestMethod.GET)
  public String getAllAnswer(Model model, @RequestParam(defaultValue = "1") int page){
    PageRequest pageNum = new PageRequest(page-1, 10, Sort.Direction.DESC, "date");
    Page<Answer> result = adminService.getAllAnswers(pageNum);

    model.addAttribute("answers", result);
    model.addAttribute("paginationInfo", new PaginationInfo(result));
    return "admin/allquestions";
  }



}
