package com.hvost.activepeople;

import com.hvost.activepeople.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by kseniaselezneva on 15/08/15.
 */
@Service
public class EmailService {

  @Autowired
  private JavaMailSender mailSender;

  @Async
  public void sendEmail(Question question){
    SimpleMailMessage email = new SimpleMailMessage( );
    email.setFrom("bochdm@tkhostov.com");
    email.setTo("bochkanov.dm@gmail.com");
    email.setSubject("новый вопрос");
    email.setText("Новый вопрос от проекта Активный гражданин " + System.lineSeparator() + question.getQuestionText());

    mailSender.send(email);
  }
}
