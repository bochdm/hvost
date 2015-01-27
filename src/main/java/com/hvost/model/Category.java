package com.hvost.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by kseniaselezneva on 27/01/15.
 */
@Entity
@Table(name="CATEGORY")
public class Category implements Serializable {


    @Id
    @Column(name = "ID")
    @GeneratedValue
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "URL")
    private String url;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
