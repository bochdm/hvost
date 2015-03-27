package com.hvost.support.navigation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry.bochkanov
 * Date: 3/27/15
 * Time: 3:33 PM
 * To change this template use File | Settings | File Templates.
 */

public class ViewRenderingHelper {

    public String navClass(String active, String current){
      if (active.equals(current))
        return "dropdown active";
      else
        return "dropdown";
    }
}
