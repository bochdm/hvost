package com.hvost.contacts.support;

import com.hvost.contacts.Contact;
import com.hvost.startpage.Carousel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by kseniaselezneva on 19/11/15.
 */
public interface ContactRepository extends JpaRepository<Contact, Integer> {
}
