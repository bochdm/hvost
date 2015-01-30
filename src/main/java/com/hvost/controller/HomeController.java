package com.hvost.controller;

import com.hvost.model.Category;
import com.hvost.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by kseniaselezneva on 29/01/15.
 */
@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value="/test", method = RequestMethod.GET)
    public String listAll(Model model){
     model.addAttribute("categories", categoryService.getAll());

        return "home";
    }

    @RequestMapping(value = "/addCategory", method = RequestMethod.POST)
    public String addCategory(@ModelAttribute Category category){
        categoryService.add(category);
        return "redirect:/";
    }
}
