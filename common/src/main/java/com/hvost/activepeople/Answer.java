package com.hvost.activepeople;

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
  private long id;

  @Column(name = "ANSWER_TEXT")
  private String answerText;

  @Column
  private Date date;

  @Column(name = "PUBLIC")
  private int isPublic;

  @Column(name = "QST_QST_ID")
  private long qstID;


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

  public long getQstID() {
    return qstID;
  }

  public void setQstID(long qstID) {
    this.qstID = qstID;
  }
}
