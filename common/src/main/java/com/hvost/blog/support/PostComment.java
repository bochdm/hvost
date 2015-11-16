package com.hvost.blog.support;



import javax.persistence.*;

/**
 * Created by kseniaselezneva on 20/05/15.
 */
@Entity
@Table(name="COMMENTS")
public class PostComment {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name = "CMT_ID")
  private Long id;

  @Column(name = "TEXT")
  private String commentText;

  @Column
  private String author;

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }


  public Long getId() {
    return id;
  }

  public String getCommentText() {
    return commentText;
  }

  public void setCommentText(String commentText) {
    this.commentText = commentText;
  }


}
