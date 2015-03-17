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

  @Column(name = "DATE", insertable = false)
  private Date date;

  @Column(name = "PUBLIC")
  private boolean isPublic;

  @OneToOne
  @JoinColumn(name = "QST_QST_ID", referencedColumnName = "QST_ID")
  private Question question;

  @Column(name = "AUTHOR")
  private String author;

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  //@OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
  //@OneToOne(mappedBy = "answerId")
/*  @Column(name="QST_QST_ID")
  private long qstID;*/

  public Question getQuestion() {
    return question;
  }

  public void setQuestion(Question question) {
    this.question = question;
  }

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

  public boolean getIsPublic() {
    return isPublic;
  }

  public void setIsPublic(boolean isPublic) {
    this.isPublic = isPublic;
  }


  @Override
  public String toString() {
    return "Answer{" +
        "  id=" + id +
        ", answerText='" + answerText + '\'' +
        ", date=" + date +
        ", isPublic=" + isPublic +
        ", question=" + question +
        '}';
  }
}
