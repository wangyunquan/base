package com.buswe.moudle.cms;

import java.io.File;

public class CmsConfig
{
  public static String getImageFolder()
  {
    return "upload" + File.separator + "image";
  }
  
  public static String getFileFolder()
  {
    return "upload" + File.separator + "file";
  }
  
  public static String getStaticExtention()
  {
    return ".html";
  }
  
  public static Integer getPageSize()
  {
    return Integer.valueOf(20);
  }
}
