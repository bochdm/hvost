package com.hvost.search;

import java.util.Date;

/**
 * Created by kseniaselezneva on 09/08/15.
 */
public class SearchResult {

  private long   id;
  private String title;
  private String content;
  private String author;

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  private Date createdAt;

  public SearchResult(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  @Override
  public String toString() {
    return "SearchResult{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", content='" + content + '\'' +
        ", author='" + author + '\'' +
        ", createdAt=" + createdAt +
        '}';
  }
}
