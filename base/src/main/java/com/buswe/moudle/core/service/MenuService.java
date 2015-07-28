package com.buswe.moudle.core.service;

import java.util.List;

import com.buswe.base.service.BaseService;
import com.buswe.moudle.core.entity.Menu;

public abstract interface MenuService
  extends BaseService<Menu>
{
  public abstract Menu getMenu(String paramString);
  
  public abstract List<Menu> getAllMenu();
}
