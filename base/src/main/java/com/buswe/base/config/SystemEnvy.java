package com.buswe.base.config;

import java.io.PrintStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class SystemEnvy
{
  public static final String OS_NAME = System.getProperty("os.name");
  public static final String USERHOME = System.getProperty("user.home");
  public static final String USERDIR = System.getProperty("user.dir");
  public static final String WEBROOT = System.getProperty("webapp.root");
  
  public static void main(String[] args)
  {
    Map<String, String> envy = System.getenv();
    for (Map.Entry<String, String> env : envy.entrySet()) {
      System.out.println((String)env.getKey() + "----" + (String)env.getValue());
    }
    System.out.println("!!!!!!!!!!!!!!!!!!");
    System.out.println("!!!!!!!!!!!!!!!!!!");
    System.out.println("!!!!!!!!!!!!!!!!!!");
    System.out.println("!!!!!!!!!!!!!!!!!!");
    Properties prop = System.getProperties();
    for (Object env : prop.entrySet()) {
      System.out.println(((Map.Entry)env).getKey() + "----" + ((Map.Entry)env).getValue());
    }
  }
}
