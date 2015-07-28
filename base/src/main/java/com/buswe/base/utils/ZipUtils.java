package com.buswe.base.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipUtils
{
  public static void unZip(File inFile, String desDir)
    throws IOException
  {
    int i = inFile.getName().lastIndexOf('.');
    File newdir = new File(desDir);
    newdir.mkdirs();
    byte[] c = new byte[1024];
    
    ZipFile zf = new ZipFile(inFile);
    Enumeration enu = zf.entries();
    while (enu.hasMoreElements())
    {
      ZipEntry file = (ZipEntry)enu.nextElement();
      
      i = file.getName().replace('/', '\\').lastIndexOf('\\');
      if (i != -1)
      {
        File dirs = new File(desDir + File.separator + 
          file.getName().replace('/', '\\').substring(0, i));
        dirs.mkdirs();
        dirs = null;
      }
      if (!file.isDirectory())
      {
        InputStream fi = zf.getInputStream(file);
        if (fi != null)
        {
          FileOutputStream out = new FileOutputStream(desDir + 
            File.separator + 
            file.getName().replace('/', '\\'));
          int slen;
          while ((slen = fi.read(c, 0, c.length)) != -1)
          {
            out.write(c, 0, slen);
          }
          out.close();
          fi.close();
        }
      }
    }
    zf.close();
  }
  
  public static void zip(File inDir, File desFile)
    throws IOException
  {
    byte[] buf = new byte[1024];
    ZipOutputStream out = new ZipOutputStream(new FileOutputStream(desFile));
    List<String> list = new ArrayList();
    list(list, inDir.getAbsolutePath());
    for (String element : list)
    {
      File file = new File(element);
      if (file.isFile())
      {
        FileInputStream in = new FileInputStream(file);
        String path = file.getAbsolutePath();
        int index = path.lastIndexOf(File.separator);
        path = path.substring(index + 1, path.length());
        out.putNextEntry(new ZipEntry(path));
        int len;
        while ((len = in.read(buf)) > 0)
        {
          out.write(buf, 0, len);
        }
        out.closeEntry();
        in.close();
      }
    }
    out.close();
  }
  
  public static void zip(File inDir, OutputStream os)
    throws IOException
  {
    byte[] buf = new byte[1024];
    ZipOutputStream out = new ZipOutputStream(os);
    List<String> list = new ArrayList();
    list(list, inDir.getAbsolutePath());
    for (String element : list)
    {
      File file = new File(element);
      if (file.isFile())
      {
        FileInputStream in = new FileInputStream(file);
        String path = file.getAbsolutePath();
        int index = path.indexOf(inDir.getName()) + 
          inDir.getName().length() + 1;
        path = path.substring(index, path.length());
        out.putNextEntry(new ZipEntry(path));
        int len;
        while ((len = in.read(buf)) > 0)
        {
          out.write(buf, 0, len);
        }
        out.closeEntry();
        in.close();
      }
    }
    out.close();
  }
  
  private static void list(List<String> lst, String path)
  {
    File f = new File(path);
    if (f.isDirectory())
    {
      lst.add(f.getAbsolutePath() + "\\");
      String[] dirs = f.list();
      for (int i = 0; (dirs != null) && (i < dirs.length); i++) {
        list(lst, f.getAbsolutePath() + "\\" + dirs[i]);
      }
    }
    if (f.isFile()) {
      lst.add(f.getAbsolutePath());
    }
  }
  
  public static void main(String[] arg)
    throws Exception
  {}
}
