package com.hvost.blog;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Parameter;

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
@Indexed
@AnalyzerDef(name = "ru",
    tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
    filters = {
        @TokenFilterDef(factory = LowerCaseFilterFactory.class),
        @TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {
            @Parameter(name = "language", value = "Russian")
        })
    })
public class Post implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
 // @Field(index = Index., analyze = Analyze.YES, store = Store.NO)
  @Field
  @Analyzer(definition = "ru")
  private  String author;

  @Column
  @Field
  @Analyzer(definition = "ru")
  private String title;

  @Column
  @Type(type="text")
  @Field
  @Analyzer(definition = "ru")
  private String content;

  @Column
  private Date createdAt;

  @Column(length = 350)
  @Field
  @Analyzer(definition = "ru")
  private String summary;


  public CategoryPost getCategoryPost() {
    return categoryPost;
  }

  public void setCategoryPost(CategoryPost categoryPost) {
    this.categoryPost = categoryPost;
  }

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "CP_CP_ID")
  private CategoryPost categoryPost;

  public Post() {
  }

  public Post(Post post){
    this.id     = post.getId();
    this.author = post.getAuthor();
    this.title = post.getTitle();
    this.content = post.getContent();
    this.createdAt = post.getCreatedAt();
    this.summary = post.getSummary();
    this.categoryPost = post.getCategoryPost();
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
   /* if (summary.isEmpty()) {
      try {
        int size = getClass().getDeclaredField("summary").getAnnotation(Column.class).length();
        int inLength = summary.length();
        if (inLength >= size)
          summary = summary.substring(0, size - 1);

      } catch (NoSuchFieldException ex) {
      } catch (SecurityException ex) {
      }
    }*/
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
