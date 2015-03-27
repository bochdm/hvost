package com.hvost.support.navigation;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry.bochkanov
 * Date: 3/26/15
 * Time: 8:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class Navigable extends HandlerInterceptorAdapter {
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    super.postHandle(request, response, handler, modelAndView);

    if (handler instanceof HandlerMethod){
      HandlerMethod handlerMethod = (HandlerMethod) handler;
      Navigation navSection = handlerMethod.getBean().getClass().getAnnotation(Navigation.class);
      if (navSection != null && modelAndView != null){
        System.out.println("Navigable -> navSection.bean = " + navSection.value().toString().toLowerCase());
        modelAndView.addObject("navSection", navSection.value().toString().toLowerCase());
      }
    }

  }
}
