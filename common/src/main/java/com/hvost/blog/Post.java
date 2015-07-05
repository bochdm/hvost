package com.hvost.blog;

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
@Table(name = "POST")
public class Post implements Serializable {

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

  @Column(length = 350)
  private String summary;


  public CategoryPost getCategoryPost() {
    return categoryPost;
  }

  public void setCategoryPost(CategoryPost categoryPost) {
    this.categoryPost = categoryPost;
  }

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "CP_CP_ID")
  private CategoryPost categoryPost;

  public Post() {
  }

  public Post(String author, String title, String content, Date createdAt, String summary, CategoryPost categoryPost) {
    this.author = author;
    this.title = title;
    this.content = content;
    this.createdAt = createdAt;
    this.summary = summary;
    this.categoryPost = categoryPost;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    try {
      int size = getClass().getDeclaredField("summary").getAnnotation(Column.class).length();
      int inLength = summary.length();
      if (inLength>=size)
        summary = summary.substring(0, size-1);

    } catch (NoSuchFieldException ex) {
    } catch (SecurityException ex) {
    }
    this.summary = summary;
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
    return "Post{" +
        "id=" + id +
        ", author='" + author + '\'' +
        ", title='" + title + '\'' +
        ", summary='" + summary + '\'' +
        ", content='" + content + '\'' +
        ", category='" + categoryPost + '\'' +
        ", createdAt=" + createdAt +
        '}';
  }
}
