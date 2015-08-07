package com.hvost.archive;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by kseniaselezneva on 05/07/15.
 */
@Entity
@Table(name = "ARCHIVE")
@Indexed
public class Archive implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  @Type(type = "text")
  @Field
  @Analyzer(definition = "ru")
  private String content;

  @Column
  private Date createdAt;

  @Column(length = 1000)
  @Field
  @Analyzer(definition = "ru")
  private String summary;

  @Column(length = 100)
  private String title;

  @Column
  private String url;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Archive() {
  }

  public Long getId() {
    return id;
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

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  @Override
  public String toString() {
    return "Archive{" +
        "id=" + id +
        ", content='" + content + '\'' +
        ", createdAt=" + createdAt +
        ", summary='" + summary + '\'' +
        ", title='" + title + '\'' +
        ", url='" + url + '\'' +
        '}';
  }
}
