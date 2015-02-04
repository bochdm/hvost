package com.hvost.article;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Dmitry.Bochkanov
 * Date: 2/2/15
 * Time: 8:13 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "article")
public class Article implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private  String author;

  @Column
  private String title;

  @Column
  @Type(type="text")
  private String content;

  @Column
  private Date createdAt;

  public Article(String author, String title, String content) {
    this.author = author;
    this.title = title;
    this.content = content;
  }

  public Article() {
  }

  public Long getId() {
    return id;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public String toString() {
    return "Article{" +
        "id=" + id +
        ", author='" + author + '\'' +
        ", title='" + title + '\'' +
        ", content='" + content + '\'' +
        ", createdAt=" + createdAt +
        '}';
  }
}
