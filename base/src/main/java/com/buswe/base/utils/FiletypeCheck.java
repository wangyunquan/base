package com.buswe.base.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public class FiletypeCheck
{
  public static boolean checkFile(File file, String type)
  {
    type = type.toLowerCase();
    
    String fileName = file.getName();
    if (!fileName.endsWith(type)) {
      return false;
    }
    String hex = FiletypeEnum.valueOf(type).getValue();
    String typeHex = getFileHex(file).toUpperCase();
    if (!typeHex.startsWith(hex)) {
      return false;
    }
    return true;
  }
  
  private static String getFileHex(File file)
  {
    byte[] b = new byte[50];
    try
    {
      InputStream is = new FileInputStream(file);
      is.read(b, 0, b.length);
      is.close();
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return TypeConvertUtils.byteToHex(b);
  }
  
  public static void main(String[] arg)
  {
    System.out.println(checkFile(new File("D:\\tu\\6hua.zip"), "zip"));
  }
}
