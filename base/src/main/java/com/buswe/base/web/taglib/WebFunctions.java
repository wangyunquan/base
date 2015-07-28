package com.buswe.base.web.taglib;

import java.util.Iterator;

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
}
