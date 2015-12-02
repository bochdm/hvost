package com.hvost.contacts.support;

import com.hvost.contacts.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by kseniaselezneva on 19/11/15.
 */
@Service
public class ContactService {
  @PersistenceContext
  EntityManager em;

  @Autowired
  ContactRepository contactRepository;

  public List<Contact> getAllContacts(){
    return contactRepository.findByActive();
  }
}
