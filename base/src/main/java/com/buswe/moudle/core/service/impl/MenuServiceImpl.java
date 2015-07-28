package com.buswe.moudle.core.service.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buswe.base.constants.SytemConstants;
import com.buswe.base.dao.springdata.BaseRepository;
import com.buswe.base.service.BaseServiceImpl;
import com.buswe.moudle.core.dao.MenuDao;
import com.buswe.moudle.core.entity.Menu;
import com.buswe.moudle.core.service.MenuService;

@Service("menuServiceImpl")
@Transactional("jpaTransaction")
public class MenuServiceImpl
  extends BaseServiceImpl<Menu>
  implements MenuService
{
  static List<Menu> tmenu = null;
  @Resource
  MenuDao menuDao;
  
  public BaseRepository<Menu, String> getDao()
  {
    return this.menuDao;
  }
  
  public Menu getMenu(String id)
  {
    return this.menuDao.getInitParent(id);
  }
  @Cacheable(value={SytemConstants.CACHE_ALL_MENU})
  public List<Menu> getAllMenu()
  {
    List<Menu> topMenu = this.menuDao.findByLevel(1);
    Iterator localIterator2;
    for (Iterator localIterator1 = topMenu.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
    {
      Menu top = (Menu)localIterator1.next();
      localIterator2 = top.getChildren().iterator();  
      Menu sec = (Menu)localIterator2.next();
      for (Menu left : sec.getChildren()) {
        left.getAuthority();
      }
      sec.getAuthority();
    }
    tmenu = topMenu;
    
    return tmenu;
  }
}
