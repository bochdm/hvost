package com.hvost.home;

import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by kseniaselezneva on 27/09/15.
 */
@Service
public class GoogleApiSearchService {

  @Async
  public Future<List<String>> getResult() {
    URL url = null;
    try {
      url = new URL(
          "https://ajax.googleapis.com/ajax/services/search/web?v=1.0&"
              + "q=Paris%20Hilton");

      System.out.println("search url = " + url);

      URLConnection connection = url.openConnection();

      String line;
      StringBuilder builder = new StringBuilder();
      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      while ((line = reader.readLine()) != null) {
        builder.append(line);
      }

      JSONObject json = new JSONObject(builder.toString());
      System.out.println("json google = " + json);



    } catch (MalformedURLException mue) {
      mue.printStackTrace();
    }catch (IOException ioe){
      ioe.printStackTrace();
    }

    List<String> teest = new ArrayList<String>();
    teest.add("lalal");
    teest.add("eirjtgh ejh");
    teest.add("dkjhoeirhgoi");
    return new AsyncResult<List<String>>(teest);
  }

  @Async
  public Future<List<GoogleSearchResult>> testData() {
    String address = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
    String query = System.getProperty("google_query", "Тхостов Константин").trim();
    String charset = "UTF-8";
    GoogleResults results = null;
    List<GoogleSearchResult> googleSearchList = new ArrayList<>(4);
    try {
      URL url = new URL(address + URLEncoder.encode(query, charset));
      Reader reader = new InputStreamReader(url.openStream(), charset);
      results = new Gson().fromJson(reader, GoogleResults.class);

      int total = results.getResponseData().getResults().size();
      System.out.println("total: " + total);

      // Show title and URL of each results

      for (int i = 0; i <= total - 1; i++) {
        googleSearchList.add(new GoogleSearchResult(results.getResponseData().getResults().get(i).getTitle(),
                                                    results.getResponseData().getResults().get(i).getUrl()));

      }
    }catch(UnsupportedEncodingException ueo){
      ueo.printStackTrace();
    }catch (MalformedURLException mue){
      mue.printStackTrace();
    }catch (IOException ioe){
      ioe.printStackTrace();
    }

    return new AsyncResult(googleSearchList);
  }
}
