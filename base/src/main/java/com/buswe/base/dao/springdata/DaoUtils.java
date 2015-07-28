package com.buswe.base.dao.springdata;

import javax.persistence.criteria.Path;
import org.apache.commons.lang3.StringUtils;

public class DaoUtils
{
  public static <T> Path<T> toExpressionRecursively(Path<T> root, String propertyName)
  {
    String[] names = StringUtils.split(propertyName, ".");
    Path expression = root.get(names[0]);
    for (int i = 1; i < names.length; i++) {
      expression = expression.get(names[i]);
    }
    return expression;
  }
}
