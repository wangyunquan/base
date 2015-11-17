package com.buswe.base.web.taglib;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Locale;

import org.springframework.data.domain.Sort;

public class WebFunctions
{
  public static Iterator iterableChange(Iterable iterable)
  {
    if (iterable == null) {
      return null;
    }
    return iterable.iterator();
  }
  
  public static Sort.Order firstOrder(Sort sort)
  {
    if (sort == null) {
      return null;
    }
    Iterator<Sort.Order> it = sort.iterator();
    if (it.hasNext()) {
      return (Sort.Order)it.next();
    }
    return null;
  }
  
	public static String getFormatFileSize(Long size) {
		DecimalFormat formater = new DecimalFormat("####.0");
		if(size==null) return "0";
		
		if (size < 1024) {
			return size + "byte";
		} else if (size < 1024l * 1024l) {
			float kbsize = size / 1024f;
			return formater.format(kbsize) + "KB";
		} else if (size < 1024l * 1024l * 1024l) {
			float mbsize = size / 1024f / 1024f;
			return formater.format(mbsize) + "MB";
		} else if (size < (1024l * 1024l * 1024l * 1024l)) {
			float gbsize = size / 1024f / 1024f / 1024f;
			return formater.format(gbsize) + "GB";
		} else if (size < 1024 * 1024 * 1024 * 1024 * 1024) {
			float gbsize = size / 1024f / 1024f / 1024f / 1024f;
			return formater.format(gbsize) + "TB";
		} else {
			return "未知";
		}
	}
	
	
	public static String getFormatDate(java.util.Date time) {
		if(time==null) return "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE);
		String ctime = formatter.format(time);
		return ctime;
	}
	
}
