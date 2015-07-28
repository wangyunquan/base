package com.buswe.moudle.cms.service;

import java.util.Date;

import com.buswe.moudle.cms.entity.Category;

public abstract interface GenerateService
{
  public abstract boolean generateIndex(String paramString);
  
  public abstract Integer generateCat(Category paramCategory, Date paramDate1, Date paramDate2);
  
  public abstract Integer generateAllcat(String paramString);
}
