package com.hvost.activepeople;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import org.springframework.stereotype.Service;

/**
 * Created by kseniaselezneva on 30/01/16.
 */
@Service
public class GoogleGeoCoding {

  public LatLng geo(String address){
    try {
      GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyDBzrhnVIF6mIuRWCRucyciTpm6ZjbtcoQ");
      GeocodingResult[] result = GeocodingApi.geocode(context, address).await();
      for (GeocodingResult geocodingResult : result) {
        System.out.println("geocodingResult -> " + geocodingResult.formattedAddress);
        System.out.println("geocodingResult lat-> " + geocodingResult.geometry.location.lat);
        System.out.println("geocodingResult lng -> " + geocodingResult.geometry.location.lng);
      }
      System.out.println("GoogleGeoCoding -> " + String.format("%s, %s", result[0].geometry.location.lat, result[0].geometry.location.lng));
      return result[0].geometry.location;
    }catch (Exception e){
      System.out.println("GoogleGeoCoding" + e);
    }
    return new LatLng(0,0);
  }

}
