package com.buswe.base.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils
{
  public static final String SSFORMARTSTRING = "yyyyMMddHHmmss";
  public static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  
  public static String getCurrentDateString(String formatStr)
  {
    return formartString(getCurrentDate(), formatStr);
  }
  
  public static Date getCurrentDate()
  {
    return new Date();
  }
  
  public static Date formatDate(String dateString)
  {
    try
    {
      return df.parse(dateString);
    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static Date formatDate(String dateStr, String formatStr)
  {
    DateFormat df = new SimpleDateFormat(formatStr);
    try
    {
      return df.parse(dateStr);
    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static String formartString(Date date, String formatStr)
  {
    DateFormat df = new SimpleDateFormat(formatStr);
    return df.format(date);
  }
}
