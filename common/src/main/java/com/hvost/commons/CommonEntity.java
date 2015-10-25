package com.hvost.commons;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by kseniaselezneva on 25/10/15.
 */
@Entity
@Table(name = "COMMONS")
public class CommonEntity implements Serializable {

  public CommonEntity() {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column
  private String title;

  @Column
  @Type(type="text")
  private String text;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Integer getId() {
    return id;
  }

  @Override
  public String toString() {
    return "CommonEntity{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", text='" + text + '\'' +
        '}';
  }
}
