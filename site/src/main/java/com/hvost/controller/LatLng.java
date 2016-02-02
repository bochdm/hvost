package com.hvost.controller;

import java.math.BigDecimal;

/**
 * Created by kseniaselezneva on 28/01/16.
 */
public class LatLng {
  private BigDecimal lat;
  private BigDecimal lon;

  public BigDecimal getLat() {
    return lat;
  }

  public void setLat(BigDecimal lat) {
    this.lat = lat;
  }

  public BigDecimal getLon() {
    return lon;
  }

  public void setLon(BigDecimal lon) {
    this.lon = lon;
  }

  public LatLng(BigDecimal lat, BigDecimal lon) {
    this.lat = lat;
    this.lon = lon;
  }

  public LatLng(String lat, String lon) {
    this.lat = new BigDecimal(lat);
    this.lon = new BigDecimal(lon);
  }
}
