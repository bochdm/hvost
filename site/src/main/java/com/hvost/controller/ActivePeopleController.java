package com.hvost.controller;

import com.google.maps.model.*;
import com.hvost.activepeople.Answer;
import com.hvost.activepeople.GoogleGeoCoding;
import com.hvost.activepeople.Question;
import com.hvost.activepeople.support.ActivePeopleService;
import com.hvost.activepeople.EmailService;
import com.hvost.images.Image;
import com.hvost.images.support.ImageService;
import com.hvost.support.FolderPath;
import com.hvost.support.ImageUtils;
import com.hvost.support.PaginationInfo;
import com.hvost.support.navigation.Navigation;
import com.hvost.support.navigation.Section;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by kseniaselezneva on 02/03/15.
 */
@SuppressWarnings("SpringMVCViewInspection")
@Controller
@RequestMapping(value = "/activepeople")
@Navigation(Section.ACTIVEPEOPLE)
public class ActivePeopleController {

  @Autowired
  private ActivePeopleService service;

  @Autowired
  private ImageService imageService;

  @Autowired
  private EmailService emailService;

  @Autowired
  private JavaMailSender mailSender;

  @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
  public String listQuestions(Model model, @RequestParam(defaultValue = "1") int page){

    System.out.println("listQuestions");

    PageRequest pageNum = new PageRequest(page-1, 10, Sort.Direction.DESC, "date");


   // Page<Questions> result = service.getAllArticles(pageNum);

    Answer a = new Answer();
    a.setIsPublic(true);
  //  Page<Questions> result = service.getPublished(a, pageNum);

    Page<Answer> result = service.getPublished1(pageNum, 1);


/*    List<Questions> qq =  result.getContent();
    for(Questions q: qq)
      System.out.println(q);*/

    List<Answer> aa = result.getContent();
    for(Answer ans : aa)
      System.out.println(ans);


    return renderList(result, model);
  }

  @RequestMapping(value = "unaswered", method = RequestMethod.GET)
  public String listUnansweredQuestions(Model model, @RequestParam(defaultValue = "1") int page){
    PageRequest pageNum = new PageRequest(page - 1, 10, Sort.Direction.DESC, "date");
    Page<Question> listQuestion = service.getAllUnansweredQuestions(pageNum);

    model.addAttribute("listQuestion", listQuestion);
    model.addAttribute("paginationInfo", new PaginationInfo(listQuestion));

    return "/activepeople/unanswered_questions";
  }

  @RequestMapping(value = "allquestion", method = RequestMethod.GET)
  public String getAllQuestion(Model model){
    List<Question> allQuestion = service.getAllQuestion();

    for (Question question : allQuestion) {
      System.out.println("getAllQuestion:question -> " + question);
    }

    model.addAttribute("allquestion", allQuestion);

    return "allquestion";
  }

  @Autowired
  private GoogleGeoCoding googleGeoCoding;

  @RequestMapping(value = "/addquestion", method = RequestMethod.POST)
  public String addQuestion(@ModelAttribute("question") @Valid Question question,
                            BindingResult bindingResult,
                            MultipartHttpServletRequest request,
                            RedirectAttributes redirectAttributes){
    System.out.println("addQuestion:newQuestion -> " + question);
    if (bindingResult.hasErrors()) {
      return "/activepeople/activepeople";
    }

    com.google.maps.model.LatLng latLng;
    String address = question.getAddress().toUpperCase();
    if (address.contains("САНКТ-ПЕТЕРБУРГ")) {
      latLng = googleGeoCoding.geo(question.getAddress());
    } else {
      latLng = googleGeoCoding.geo("Санкт-Петербург, " + question.getAddress());
    }

    question.setLatLng(latLng.toString());
    question.setLat(latLng.lat);
    question.setLng(latLng.lng);

    Question newQuestion = service.addNewQuestion(question);


    //загрузка файлов
    Map<String, MultipartFile> fileMap = request.getFileMap();
    String rootPath = System.getProperty("catalina.home");
    File imagesDir = new File(rootPath + File.separator + "images" + File.separator + "activepeople" + File.separator);

    for (MultipartFile multipartFile : fileMap.values()){
      Image imageInfo = ImageUtils.getUploadImageInfo(multipartFile, newQuestion);
      imageService.uploadFileInfo(imageInfo);
      ImageUtils.saveFileToLocalDisk(multipartFile, imageInfo, FolderPath.ACTIVEPEOPLE);

    }

    emailService.sendEmail(question);

    redirectAttributes.addFlashAttribute("redirect", "1");

//    return "redirect:/activepeople";
    switch (question.getType()){
      case 1:
        return "redirect:/map";
      case 2:
        return "redirect:/save_outdoors/";
      case 3:
        return "redirect:/comfortable_outdoors/";
      case 4:
        return "redirect:/coach/";
      default:
        return "redirect:/map";
    }
  }

  @RequestMapping(value = "/search", method = RequestMethod.POST)
  public String search(Model model,
                       @RequestParam String q,
                       @RequestParam(defaultValue = "1") int page){

    List<Answer> result = service.getAnswerBySearch(q);
    model.addAttribute("search_results", result);
    model.addAttribute("searchCount", result.size());
    model.addAttribute("queryString", q);

    return "/activepeople/search_results";
  }

  private String renderList(Page<Answer> page, Model model){

    model.addAttribute("answers", page);
    model.addAttribute("question", new Question());
    model.addAttribute("paginationInfo", new PaginationInfo(page));

//    return "/activepeople/activepeople";
    return "/activepeople/activepeople";
  }

}