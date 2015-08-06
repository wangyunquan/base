package com.buswe.moudle.core.service.impl;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buswe.base.dao.springdata.BaseRepository;
import com.buswe.base.security.PasswordUtil;
import com.buswe.base.service.BaseServiceImpl;
import com.buswe.moudle.core.dao.GroupInfoDao;
import com.buswe.moudle.core.dao.RolesinfoDao;
import com.buswe.moudle.core.dao.UserBasicDao;
import com.buswe.moudle.core.entity.Rolesinfo;
import com.buswe.moudle.core.entity.UserBasic;
import com.buswe.moudle.core.service.UserBasicService;

@Service("userBasicService")
@Transactional("jpaTransaction")
public class UserBasicServiceImpl
  extends BaseServiceImpl<UserBasic>
  implements UserBasicService
{
  @Resource
  private UserBasicDao userBasicDao;
  @Resource
  private RolesinfoDao rolesinfoDao;
  @Resource
  private GroupInfoDao groupInfoDao;
  
  public BaseRepository<UserBasic, String> getDao()
  {
    return this.userBasicDao;
  }
  
  public UserBasic save(UserBasic entity, String[] rolesIds)
  {
    Set<Rolesinfo> rolesinfo = entity.getRolesinfos();
    PasswordUtil.entryptPassword(entity);
    rolesinfo.clear();
    for (String roleId : rolesIds)
    {
      Rolesinfo role = (Rolesinfo)this.rolesinfoDao.findOne(roleId);
      rolesinfo.add(role);
    }
    entity.setRolesinfos(rolesinfo);
    entity = (UserBasic)save(entity);
    return entity;
  }
  
  public UserBasic findByLoginName(String username)
  {
    return this.userBasicDao.findByLoginName(username);
  }
  
  public UserBasic getUser(String id)
  {
    return this.userBasicDao.getUser(id);
  }
}
