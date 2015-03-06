package com.hvost.controller;

import com.hvost.activepeople.Answer;
import com.hvost.activepeople.Questions;
import com.hvost.activepeople.support.ActivePeopleService;
import com.hvost.blog.Post;
import com.hvost.support.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by kseniaselezneva on 02/03/15.
 */
@Controller
@RequestMapping(value = "/activepeople")
public class ActivePeopleController {

  @Autowired
  private ActivePeopleService service;

  @RequestMapping(method = RequestMethod.GET)
  public String listQuestions(Model model, @RequestParam(defaultValue = "1") int page){

    PageRequest pageNum = new PageRequest(page-1, 10, Sort.Direction.DESC, "date");


    //Page<Questions> result = service.getAll(pageNum);

    Answer a = new Answer();
    a.setIsPublic(1);
    Page<Questions> result = service.getPublished(a, pageNum);



    List<Questions> qq =  result.getContent();
    for(Questions q: qq)
      System.out.println("q->" + q.getQuestionText());

    model.addAttribute("newQuestion", new Questions());

    return renderList(result, model);

  }

  @RequestMapping(value = "/newquestion", method = RequestMethod.POST)
  public String addQuestion(@ModelAttribute Questions questions){
    service.addNewQuestion(questions);

    return "/activepeople/activepeople";
  }

  private String renderList(Page<Questions> page, Model model){

    model.addAttribute("questions", page);
    model.addAttribute("paginationInfo", new PaginationInfo(page));


    return "/activepeople/activepeople";
  }

}
