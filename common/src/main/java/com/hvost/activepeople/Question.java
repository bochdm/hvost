package com.hvost.activepeople;




import com.hvost.images.Image;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.annotations.Filter;
import org.hibernate.search.annotations.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.persistence.Parameter;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;


/**
 * Created by kseniaselezneva on 03/03/15.
 */
@Entity
@Table(name="QUESTIONS")
@Indexed
public class Question implements Serializable {

  public Question(Question q){
    this.id = q.getId();
    this.author = q.getAuthor();
    this.category = q.getCategory();
    this.date = q.getDate();
    this.questionText = q.getQuestionText();
  }

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

  @Column(name = "EMAIL")
  private String email;

  @Column(name = "ADDRESS")
  private String address;

  public String getLatLng() {
    return latlng;
  }

  public void setLatLng(String latlng) {
    this.latlng = latlng;
  }

  @Column(name = "LATLNG")
  private String latlng;

  @Column(name = "LAT")
  private double lat;

  @Column(name = "LNG")
  private double lng;

  @Column(name = "IS_SHOW")
  private boolean isShow;

  public double getLat() {
    return lat;
  }

  public void setLat(double lat) {
    this.lat = lat;
  }

  public double getLng() {
    return lng;
  }

  public void setLng(double lng) {
    this.lng = lng;
  }

  public void setLat(float lat) {
    this.lat = lat;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Question() {
  }

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "idEntity", cascade = CascadeType.ALL)
  @Filter(name="category", condition = "category=2")
  private Set<Image> images;

  public Set<Image> getImages() {
    return images;
  }

  public void setImages(Set<Image> images) {
    this.images = images;
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

  public boolean isShow() {
    return isShow;
  }

  public void setShow(boolean isShow) {
    this.isShow = isShow;
  }

  @Override
  public String toString() {
    return "Question{" +
        "id=" + id +
        ", questionText='" + questionText + '\'' +
        ", date=" + date +
        ", author='" + author + '\'' +
        ", category=" + category +
        ", email='" + email + '\'' +
        ", address='" + address + '\'' +
        ", latlng='" + latlng + '\'' +
        ", lat=" + lat +
        ", lng=" + lng +
        ", isShow=" + isShow +
        ", images=" + images +
        '}';
  }
}
