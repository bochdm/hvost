package com.hvost.startpage;

import javax.persistence.*;
import java.sql.Blob;

/**
 * Created by kseniaselezneva on 04/08/15.
 */
@Entity
@Table(name = "START_CAROUSEL")
public class Carousel {

  public int getId() {
    return id;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column
  private String content;


  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }

  @Lob
  @Column(columnDefinition = "blob")
  private byte[] image;

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }



  @Override
  public String toString() {
    return "Сarousel{" +
        "id=" + id +
        ", content='" + content + '\'' +
        '}';
  }
}
