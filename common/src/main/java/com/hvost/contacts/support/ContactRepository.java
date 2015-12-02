package com.hvost.contacts.support;

import com.hvost.contacts.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by kseniaselezneva on 19/11/15.
 */
public interface ContactRepository extends JpaRepository<Contact, Integer> {
  @Query("select c from Contact c where c.active = true")
  List<Contact> findByActive();
}
