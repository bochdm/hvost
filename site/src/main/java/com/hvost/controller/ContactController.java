package com.hvost.controller;

import com.hvost.contacts.support.ContactService;
import com.hvost.support.navigation.Navigation;
import com.hvost.support.navigation.Section;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by kseniaselezneva on 19/11/15.
 */
@Controller
@RequestMapping(value = "contacts1")
@Navigation(Section.CONTACTS)
public class ContactController {

  @Autowired
  ContactService contactService;

  @RequestMapping(method = RequestMethod.GET)

  public String contacts(Model model){
    model.addAttribute("contactBlocks", contactService.getAllContacts());

    return "/contacts";
  }
}
