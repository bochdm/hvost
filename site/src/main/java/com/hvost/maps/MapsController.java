package com.hvost.maps;

import com.hvost.activepeople.Answer;
import com.hvost.activepeople.Question;
import com.hvost.activepeople.support.ActivePeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by kseniaselezneva on 02/02/16.
 */
@Controller
@RequestMapping(value = "/map")
public class MapsController {

  @Autowired
  private ActivePeopleService activePeopleService;

  @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
  public String toMap(Model model, HttpSession session){

    Future<Page<Question>> allUnansweredQuestions = activePeopleService.getAllUnaswered();
    session.setAttribute("unaswered", allUnansweredQuestions);
    model.addAttribute("unaswered", allUnansweredQuestions);

    Future<Page<Answer>> answers = activePeopleService.getAnswers();
    session.setAttribute("answers", answers);
    model.addAttribute("answers", answers);

    model.addAttribute("question", new Question());

    return "/activepeople/map_question";
  }

  @RequestMapping(value = "/unansweredcheck", method = RequestMethod.GET)
  @ResponseBody
  public List<Map<String, String>> checkUnsweredRequestDone(Model model, HttpSession session){
    Future<Page<Question>> asyncResult = (Future<Page<Question>>) session.getAttribute("unaswered");

    List<Map<String, String>> unAnsweredMarkers = new ArrayList<>();

    if (asyncResult.isDone()){
      Page<Question> questions = null;
      try{
        questions = asyncResult.get();
        for (Question question : questions) {
          double lat = question.getLat();
          double lng = question.getLng();
          if (lat != 0 && lng != 0){
            Map<String, String> questionInfo = new HashMap<>();
            questionInfo.put("lat", String.valueOf(lat));
            questionInfo.put("lng", String.valueOf(lng));

            StringBuilder sb = new StringBuilder("<b>");
            sb.append(question.getAddress());
            sb.append("</b>");
            sb.append("<br/>");
            sb.append(question.getQuestionText());

            questionInfo.put("text", sb.toString());
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

  @RequestMapping(value = "/answeredcheck", method = RequestMethod.GET)
  @ResponseBody
  public List<Map<String, String>> checkAnsweredRequestDone(Model model, HttpSession session){
    Future<Page<Answer>> asyncResult = (Future<Page<Answer>>) session.getAttribute("answers");

    List<Map<String, String>> answeredMarkers = new ArrayList<>();

    if (asyncResult.isDone()){
      Page<Answer> answers = null;
      try{
        answers = asyncResult.get();
        for (Answer answer : answers) {
          double lat = answer.getQuestion().getLat();
          double lng = answer.getQuestion().getLng();
          if (lat != 0 && lng != 0){
            Map<String, String> questionInfo = new HashMap<>();
            questionInfo.put("lat", String.valueOf(lat));
            questionInfo.put("lng", String.valueOf(lng));

            StringBuilder sb = new StringBuilder("<b>");
            sb.append(answer.getQuestion().getAddress());
            sb.append("</b>");
            sb.append("<br/>");
            sb.append(answer.getQuestion().getQuestionText());

            questionInfo.put("text", sb.toString());
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
