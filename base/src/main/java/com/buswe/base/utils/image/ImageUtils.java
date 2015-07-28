package com.buswe.base.utils.image;

import java.io.InputStream;
import org.apache.commons.lang3.StringUtils;

public class ImageUtils
{
  public static final String[] IMG_EXTENSIONS = { "jpeg", "jpg", 
    "png", "gif", "bmp", "pcx", "iff", "ras", "pbm", "pgm", "ppm", 
    "psd" };
  
  public static final boolean isImgExtension(String extension)
  {
    if (StringUtils.isBlank(extension)) {
      return false;
    }
    for (String imgExt : IMG_EXTENSIONS) {
      if (StringUtils.equalsIgnoreCase(imgExt, extension)) {
        return true;
      }
    }
    return false;
  }
  
  public static boolean isImage(InputStream in)
  {
    ImageInfo ii = new ImageInfo();
    ii.setInput(in);
    return ii.check();
  }
}
