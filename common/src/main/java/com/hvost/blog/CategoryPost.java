package com.hvost.blog;

import javax.persistence.*;
import java.util.List;

/**
 * Created by kseniaselezneva on 19/03/15.
 */
@Entity
@Table(name = "CATEGORY")
public class CategoryPost {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "CP_ID")
  private Integer id;

  @Column(name = "name")
  private String name;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @OneToMany(mappedBy = "categoryPost")
  public List<Post> posts;

  @Override
  public String toString() {
    return name;
  }
}
