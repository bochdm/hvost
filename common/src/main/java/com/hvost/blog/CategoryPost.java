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
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "CP_ID")
  private Long id;

  @Column(name = "name")
  private String name;

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

/*  @OneToMany(mappedBy = "categoryPost")
  public List<Post> posts;*/

  @Override
  public String toString() {
    return name;
  }
}
