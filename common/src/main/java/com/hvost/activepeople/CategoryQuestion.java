package com.hvost.activepeople;

import javax.persistence.*;

/**
 * Created by kseniaselezneva on 04/03/15.
 */
@Entity
@Table(name = "CATEGORY_QUESTION")
public class CategoryQuestion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "CQ_ID")
  private int id;

  @Column(name = "NAME")
  private String name;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
