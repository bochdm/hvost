package com.hvost.support;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * Created by kseniaselezneva on 20/07/15.
 */
public class MyServletContext implements ServletContextListener {
  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {

  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {
    Enumeration<Driver> drivers = DriverManager.getDrivers();
    while (drivers.hasMoreElements()) {
      Driver driver = drivers.nextElement();
      try {
        DriverManager.deregisterDriver(driver);
        System.out.println(String.format("deregistering jdbc driver: %s", driver));
      } catch (SQLException e) {
        System.out.println(String.format("Error deregistering driver %s", driver));
      }

    }
    System.out.println("Context destroyed");
  }
}
