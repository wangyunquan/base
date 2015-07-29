package com.buswe.base.web.menu;

import java.util.List;
import java.util.Set;

import com.buswe.base.config.ContextHolder;
import com.buswe.moudle.core.entity.Menu;
import com.buswe.moudle.core.service.MenuService;

public class MenuHolder
{
  
  public static Set<Menu> getMenuOfMoudle(String url)
  {
    String[] autiritys = url.split("/");
    for (Menu top : getAllMenu()) {
      if (top.getAuthority().equals(autiritys[0])) {
        for (Menu subTop : top.getChildren()) {
          if (subTop.getAuthority().equals(autiritys[1])) {
            return subTop.getChildren();
          }
        }
      }
    }
    return null;
  }
  
  public static List<Menu> getAllMenu()
  {
    MenuService service = (MenuService)ContextHolder.getBean(MenuService.class);
    
    return service.getAllMenu();
  }
}
