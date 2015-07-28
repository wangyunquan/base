package com.buswe.moudle.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buswe.moudle.core.dao.RolesAuthorityDao;
import com.buswe.moudle.core.entity.RolesAuthority;
import com.buswe.moudle.core.entity.RulesInfo;
import com.buswe.moudle.core.service.SecurityService;

@Service("securityService")
@Transactional
public class SecurityServiceImpl
  implements SecurityService
{
  @Resource
  RolesAuthorityDao roleAuthorityDao;
  
  @Cacheable(value={"securityCache"}, key="#roleId + 'securityCache'")
  public Map<String, Set<RulesInfo>> getRoleAuthority(String roleId)
  {
    List<RolesAuthority> roleAuhority = this.roleAuthorityDao.findByRolesinfoId(roleId);
    Map<String, Set<RulesInfo>> map = new HashMap();
    for (RolesAuthority ra : roleAuhority) {
      map.put(ra.getAuthority().getName(), ra.getRulesInfos());
    }
    return map;
  }
}
