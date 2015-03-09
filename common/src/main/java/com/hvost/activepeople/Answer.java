package com.hvost.activepeople;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by kseniaselezneva on 03/03/15.
 */
@Entity
@Table(name = "ANSWER")
public class Answer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ASW_ID")
  private long id;

  @Column(name = "ANSWER_TEXT")
  @Type(type="text")
  private String answerText;

  @Column(name = "DATE")
  private Date date;

  @Column(name = "PUBLIC")
  private int isPublic;

  //@OneToOne(mappedBy = "answerId")
/*  @Column(name="QST_QST_ID")
  private long qstID;*/

  public Questions getQuestion() {
    return question;
  }

  @OneToOne
  @JoinColumn(name = "QST_QST_ID", referencedColumnName = "QST_ID")
  private Questions question;


  public long getId() {
    return id;
  }

  public String getAnswerText() {
    return answerText;
  }

  public void setAnswerText(String answerText) {
    this.answerText = answerText;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public int getIsPublic() {
    return isPublic;
  }

  public void setIsPublic(int isPublic) {
    this.isPublic = isPublic;
  }


  @Override
  public String toString() {
    return "Answer{" +
        "id=" + id +
        ", answerText='" + answerText + '\'' +
        ", date=" + date +
        ", isPublic=" + isPublic +
        ", question=" + question +
        '}';
  }
}
