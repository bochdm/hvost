package com.hvost.startpage;

import javax.persistence.*;
import java.util.Arrays;

/**
 * Created by kseniaselezneva on 04/08/15.
 */
@Entity
@Table(name = "start_carousel")
public class Carousel {

  public Long getId() {
    return id;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String content;

  @Column(name = "isactive")
  private boolean active;

  @Column(name = "title")
  private String title;

  @Column(name = "link")
  private String link;

  @Column(name = "title_class")
  private String titleClass;

  @Column(name = "content_class")
  private String contentClass;

  @Column(name = "link_class")
  private String linkClass;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getTitleClass() {
    return titleClass;
  }

  public void setTitleClass(String titleClass) {
    this.titleClass = titleClass;
  }

  public String getContentClass() {
    return contentClass;
  }

  public void setContentClass(String contentClass) {
    this.contentClass = contentClass;
  }

  public String getLinkClass() {
    return linkClass;
  }

  public void setLinkClass(String linkClass) {
    this.linkClass = linkClass;
  }

  public boolean getActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


}
