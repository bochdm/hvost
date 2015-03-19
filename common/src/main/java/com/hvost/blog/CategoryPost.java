package com.hvost.blog;

import javax.persistence.*;

/**
 * Created by kseniaselezneva on 19/03/15.
 */
@Entity
@Table(name = "category")
public class CategoryPost {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "name")
  private String name;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
