package com.hvost.home;

/**
 * Created by kseniaselezneva on 27/09/15.
 */
public class GoogleSearchResult {

  private String url;
  private String title;

  public GoogleSearchResult(String title, String url) {
    this.url = url;
    this.title = title;
  }

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

  @Override
  public String toString() {
    return "GoogleSearchResult{" +
        "url='" + url + '\'' +
        ", title='" + title + '\'' +
        '}';
  }
}
