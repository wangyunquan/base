package com.buswe.base.utils;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class BackUpUtils
{
  public static boolean checkZip(File uploadedFile)
  {
    Boolean iszip = Boolean.valueOf(FiletypeCheck.checkFile(uploadedFile, "zip"));
    if (!iszip.booleanValue()) {
      return false;
    }
    try
    {
      ZipFile zf = new ZipFile(uploadedFile);
      Enumeration enu = zf.entries();
      while (enu.hasMoreElements())
      {
        ZipEntry entry = (ZipEntry)enu.nextElement();
        String name = entry.getName();
        if (name.equalsIgnoreCase("dbrestore.sql")) {
          return true;
        }
      }
      zf.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return false;
  }
}
