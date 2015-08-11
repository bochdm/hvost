package com.hvost.images;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Dmitry.Bochkanov
 * Date: 8/11/15
 * Time: 8:15 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "IMAGES")
public class Image {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private int category;

  public Long getIdEntity() {
    return idEntity;
  }

  public void setIdEntity(Long idEntity) {
    this.idEntity = idEntity;
  }

  @Column
  private Long idEntity;

  @Column
  private String name;

  @Column
  private String location;

  @Column
  private Long size;

  @Column
  private String type;

  public Long getId() {
    return id;
  }

  public int getCategory() {
    return category;
  }

  public void setCategory(int category) {
    this.category = category;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public Long getSize() {
    return size;
  }

  public void setSize(Long size) {
    this.size = size;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "Image{" +
        "id=" + id +
        ", category=" + category +
        ", name='" + name + '\'' +
        ", location='" + location + '\'' +
        ", size=" + size +
        ", type='" + type + '\'' +
        '}';
  }
}
