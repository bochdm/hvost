package com.hvost.maps;

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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author kseniaselezneva
 */
@SuppressWarnings("SpringMVCViewInspection")
@Controller
@RequestMapping(value = "/map")
@Navigation(Section.ACTIVEPEOPLE)
public class MapsController {

  @Autowired
  private ActivePeopleService activePeopleService;


  @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
  public String toMap(Model model, @RequestParam(defaultValue = "1") int page, HttpSession session){

    Future<Page<Question>> allUnansweredQuestions = activePeopleService.getAllVisibleUnaswered(1);
    session.setAttribute("unaswered", allUnansweredQuestions);
    model.addAttribute("unaswered", allUnansweredQuestions);

    Future<Page<Answer>> answers = activePeopleService.getAnswers(1);
    session.setAttribute("answers", answers);
    model.addAttribute("answers", answers);

    model.addAttribute("question", new Question());

    PageRequest pageNum = new PageRequest(page-1, 10, Sort.Direction.DESC, "date");
//    Page<Answer> result = activePeopleService.getPublished1(pageNum);
    Page<Answer> result = activePeopleService.getPublishedAnswerByType(pageNum, 1);


    renderList(result, model);

    return "/activepeople/map_question";
  }

  private void renderList(Page<Answer> page, Model model){

    model.addAttribute("answers", page);
    model.addAttribute("question", new Question(1));
    model.addAttribute("paginationInfo", new PaginationInfo(page));
  }

  @RequestMapping(value = "unansweredcheck", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<Map<String, String>> checkUnsweredRequestDone(HttpSession session){
    Future<Page<Question>> asyncResult = (Future<Page<Question>>) session.getAttribute("unaswered");


    List<Map<String, String>> unAnsweredMarkers = new ArrayList<>();

    if (asyncResult.isDone()){
      Page<Question> questions;
      try{
        questions = asyncResult.get();
        for (Question question : questions) {
          double lat = question.getLat();
          double lng = question.getLng();
          if (lat != 0 && lng != 0){
            Map<String, String> questionInfo = new HashMap<>();
            questionInfo.put("lat", String.valueOf(lat));
            questionInfo.put("lng", String.valueOf(lng));

            questionInfo.put("address", question.getAddress());
            questionInfo.put("text", "<b>" + question.getAddress() + "</b>" + "<br/>" + question.getQuestionText() + "<br/><br/>");
            unAnsweredMarkers.add(questionInfo);
          }
        }

        return unAnsweredMarkers;
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
        return null;
      }
    }
    return null;
  }

  @RequestMapping(value = "answeredcheck", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<Map<String, String>> checkAnsweredRequestDone(HttpSession session, HttpServletRequest request){
    Future<Page<Answer>> asyncResult = (Future<Page<Answer>>) session.getAttribute("answers");

    List<Map<String, String>> answeredMarkers = new ArrayList<>();

    StringBuilder req = new StringBuilder();
//    req.append("http://localhost:8880/visitka/").append("map").append("/#");
    req.append(request.getRequestURL().toString().replace("answeredcheck", "#"));

    if (asyncResult.isDone()){
      Page<Answer> answers;
      try{
        answers = asyncResult.get();
        for (Answer answer : answers) {
          double lat = answer.getQuestion().getLat();
          double lng = answer.getQuestion().getLng();
          if (lat != 0 && lng != 0){
            Map<String, String> questionInfo = new HashMap<>();
            questionInfo.put("lat", String.valueOf(lat));
            questionInfo.put("lng", String.valueOf(lng));

            questionInfo.put("address", answer.getQuestion().getAddress());
            questionInfo.put("text", "<b>" + answer.getQuestion().getAddress() + "</b>" + "<br/>" + answer.getQuestion().getQuestionText() + "<br/><br/>" + "<b>" + String.format("<a href='%s%s'>Проверить ответ</a>", req.toString(), answer.getId()) + "</b>");
            answeredMarkers.add(questionInfo);
          }
        }

        return answeredMarkers;
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
        return null;
      }
    }
    return null;
  }
}
