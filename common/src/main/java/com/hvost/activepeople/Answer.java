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

  @Column
  private Date date;

  @Column(name = "PUBLIC")
  private int isPublic;

  @OneToOne(mappedBy = "answerId")
//  @Column(name = "QST_QST_ID")
  private Questions qstID;


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

  public Questions getQstID() {
    return qstID;
  }

  public void setQstID(Questions qstID) {
    this.qstID = qstID;
  }
}
