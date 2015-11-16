package com.hvost.aboutme.support;

import com.hvost.aboutme.AboutMe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by kseniaselezneva on 14/11/15.
 */
@Service
public class AboutMeService {

  @PersistenceContext
  EntityManager em;

  @Autowired
  AboutMeRepository aboutMeRepository;

  public List<AboutMe> getAllBlocksAboutMe(){
    return aboutMeRepository.findAll();
  }
}
