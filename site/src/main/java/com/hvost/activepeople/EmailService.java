package com.hvost.activepeople;

import com.hvost.activepeople.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Created by kseniaselezneva on 15/08/15.
 */
@Service
public class EmailService {

  @Autowired
  private JavaMailSender mailSender;

  private SimpleMailMessage email = new SimpleMailMessage( );

  @Async
  public void sendEmail(Question question){

    String projectName = getProjectName(question.getType());

    email.setTo(new String[]{"bochkanov.dm@gmail.com", "seleznevaks@gmail.com"});

    email.setFrom(String.format("%s <admin@tkhostov.com>", projectName));
    email.setSubject(String.format("новый вопрос от проекта %s", projectName));
    email.setText(String.format("Новый вопрос от проекта %s", projectName) + System.lineSeparator() + question.getQuestionText());

    mailSender.send(email);
  }

  private String getProjectName(int type){
    switch (type) {
      case 1:
        return "Активный гражданин";
      case 2:
        return "Безопасный двор";
      case 3:
        return  "Удобный двор";
      case 4:
        return "Дворовый тренер";
      default:
        return "Активный гражданин";
    }
  }

  /**
   * Оповещение автора вопроса о публикации ответа
   * @param answer
   */
  @Async
  public void notifyActiveUser(Answer answer){

    if (answer.getQuestion().getEmail() != null) {
      email.setFrom("Активный гражданин <direktor369@rambler.ru>");
      email.setSubject(String.format("Официальный ответ портала '%s'", getProjectName(answer.getQuestion().getType())));
      email.setTo(answer.getQuestion().getEmail());

      StringBuilder text = new StringBuilder(String.format("Уважаемый %s, благодарим за Ваш вопрос.", answer.getAuthor()));
      text.append(System.lineSeparator());
      text.append("Ответ:");
      text.append(System.lineSeparator());
      text.append(answer.getAnswerText());
      text.append(System.lineSeparator());
      text.append("Благодарим за сотрудничество! С уважением, администрация портала.");

      email.setText(text.toString());
      mailSender.send(email);
    }
  }
}
