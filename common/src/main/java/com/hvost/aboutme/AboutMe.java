package com.hvost.aboutme;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by kseniaselezneva on 14/11/15.
 */
@Entity
@Table(name = "ABOUTME")
public class AboutMe implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "AM_ID")
  private int id;

  @Column(name = "IMAGE_NAME")
  private String imageName;

  @Column(name = "ORDER_ID")
  private int orderID;

  @Column
  @Type(type = "text")
  private String text;

  @Column
  private String title;

  public String getImageName() {
    return imageName;
  }

  public void setImageName(String imageName) {
    this.imageName = imageName;
  }

  public int getOrderID() {
    return orderID;
  }

  public void setOrderID(int order) {
    this.orderID = order;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public String toString() {
    return "AboutMe{" +
        "id=" + id +
        ", imageName='" + imageName + '\'' +
        ", order=" + orderID +
        ", text='" + text + '\'' +
        ", title='" + title + '\'' +
        '}';
  }
}
