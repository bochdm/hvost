package com.hvost.controller;

import com.hvost.activepeople.Question;
import com.hvost.contacts.support.ContactService;
import com.hvost.support.navigation.Navigation;
import com.hvost.support.navigation.Section;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by kseniaselezneva on 28/05/15.
 */

@Controller
@RequestMapping(value = "/contacts")
@Navigation(Section.CONTACTS)
public class ContactsInfoController {

  @Autowired
  private JavaMailSender mailSender;

  @Autowired
  ContactService contactService;

  @RequestMapping(method = RequestMethod.GET)
  public String contacts(Model model){
    model.addAttribute("contactBlocks", contactService.getAllContacts());

    model.addAttribute("question", new Question());
    return "/contacts/contacts";
  }

  @RequestMapping(value = "/askme", method = RequestMethod.POST)
  public String askMe(@ModelAttribute("question") @Valid Question question, BindingResult bindingResult, Model model){
    if (bindingResult.hasErrors()) {
      return "/contacts/contacts";
    }

    SimpleMailMessage email = new SimpleMailMessage();
    email.setTo("bochkanov.dm@gmail.com");
    email.setSubject("Тхостову К.Э.");
    email.setText("Письмо отправленно с официального сайта Тхостова К.Э. " + System.lineSeparator() + question.getQuestionText());

    mailSender.send(email);

    return "redirect:/contacts";
  }
}
