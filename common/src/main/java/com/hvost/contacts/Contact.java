package com.hvost.contacts;

import javax.persistence.*;

/**
 * Created by kseniaselezneva on 19/11/15.
 */
@Entity
@Table(name = "CONTACTS")
public class Contact {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String title;
  private String icon;
  private String text;

  public int getId() {
    return id;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  @Column(name = "isactive")
  private boolean active;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return "Contact{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", icon='" + icon + '\'' +
        ", text='" + text + '\'' +
        ", active=" + active +
        '}';
  }
}
