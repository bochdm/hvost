package com.hvost.controller;

import com.hvost.activepeople.Answer;
import com.hvost.activepeople.Question;
import com.hvost.activepeople.support.ActivePeopleService;
import com.hvost.support.PaginationInfo;
import com.hvost.support.navigation.Navigation;
import com.hvost.support.navigation.Section;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by kseniaselezneva on 02/03/15.
 */
@Controller
@RequestMapping(value = "/activepeople")
@Navigation(Section.ACTIVEPEOPLE)
public class ActivePeopleController {

  @Autowired
  private ActivePeopleService service;

  @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
  public String listQuestions(Model model, @RequestParam(defaultValue = "1") int page){

    System.out.println("listQuestions");

    PageRequest pageNum = new PageRequest(page-1, 10, Sort.Direction.DESC, "date");


   // Page<Questions> result = service.getAllArticles(pageNum);

    Answer a = new Answer();
    a.setIsPublic(true);
  //  Page<Questions> result = service.getPublished(a, pageNum);

    Page<Answer> result = service.getPublished1(pageNum);


/*    List<Questions> qq =  result.getContent();
    for(Questions q: qq)
      System.out.println(q);*/

    List<Answer> aa =  result.getContent();
    for(Answer ans : aa)
      System.out.println(ans);



    return renderList(result, model);

  }

  @RequestMapping(value = "/addquestion", method = RequestMethod.POST)
  public String addQuestion(@ModelAttribute("question") @Valid Question question, BindingResult bindingResult, Model model){
    System.out.println("addQuestion:newQuestion" + question);
    if (bindingResult.hasErrors())
      return "/activepeople/activepeople";

    service.addNewQuestion(question);

    return "redirect:/activepeople";
  }

  private String renderList(Page<Answer> page, Model model){

    model.addAttribute("answers", page);
    model.addAttribute("question", new Question());
    model.addAttribute("paginationInfo", new PaginationInfo(page));

    return "/activepeople/activepeople";
  }

}
