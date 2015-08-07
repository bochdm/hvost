package com.hvost.activepeople;




import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.persistence.Parameter;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


/**
 * Created by kseniaselezneva on 03/03/15.
 */
@Entity
@Table(name="QUESTIONS")
@Indexed
public class Question implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "QST_ID")
  private Long id;

  @NotNull
  @NotEmpty
  @Column(name = "QUESTION_TEXT")
  @Field
  @Analyzer(definition = "ru")
  private String questionText;

/*  @Column(name = "CQ_CQ_ID")
  private int categoryID;*/

  @Column(insertable = false)
  private Date date;

  @Column
  @Field
  @Analyzer(definition = "ru")
  private String author;

  @ManyToOne
  @JoinColumn(name = "CQ_CQ_ID", insertable = false)
  private CategoryQuestion category;

  public Question() {
  }

  /*
  public Answer getAnswerId() {
    return answerId;
  }
*/

/*  @OneToOne(optional = false)
  //@MapsId
//  @JoinColumn(name = "QST_ID", referencedColumnName = "QST_QST_ID", insertable = false, updatable = false)
  private Answer answerId;*/

  public Long getId() {
    return id;
  }

  public CategoryQuestion getCategory() {
    return category;
  }

  public void setCategory(CategoryQuestion categoryID) {
    this.category = categoryID;
  }


  public String getQuestionText() {
    return questionText;
  }

  public void setQuestionText(String questionText) {
    this.questionText = questionText;
  }


  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  @Override
  public String toString() {
    return "Questions{" +
        "id=" + id +
        ", questionText='" + questionText + '\'' +
        ", date=" + date +
        ", author='" + author + '\'' +
        '}';
  }
}
