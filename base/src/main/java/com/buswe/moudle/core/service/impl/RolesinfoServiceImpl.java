package com.buswe.moudle.core.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buswe.base.dao.MatchType;
import com.buswe.base.dao.QueryHelper;
import com.buswe.base.dao.springdata.BaseRepository;
import com.buswe.base.service.BaseServiceImpl;
import com.buswe.moudle.core.dao.AuthorityDao;
import com.buswe.moudle.core.dao.RolesAuthorityDao;
import com.buswe.moudle.core.dao.RolesinfoDao;
import com.buswe.moudle.core.dao.RulesInfoDao;
import com.buswe.moudle.core.entity.Authority;
import com.buswe.moudle.core.entity.RolesAuthority;
import com.buswe.moudle.core.entity.Rolesinfo;
import com.buswe.moudle.core.entity.RulesInfo;
import com.buswe.moudle.core.service.RolesinfoService;

@Service("rolesinfoService")
@Transactional("jpaTransaction")
public class RolesinfoServiceImpl
  extends BaseServiceImpl<Rolesinfo>
  implements RolesinfoService
{
  @Resource
  private RolesinfoDao rolesinfoDao;
  @Resource
  private RolesAuthorityDao rolesAuthorityDao;
  @Resource
  private AuthorityDao authorityDao;
  @Resource
  private RulesInfoDao rulesInfoDao;
  
  public BaseRepository<Rolesinfo, String> getDao()
  {
    return this.rolesinfoDao;
  }
  
  @CacheEvict(value={"securityCache"}, key="#roleId + 'securityCache'")
  public void saveAuthAndRules(Rolesinfo role, Map<String, String[]> authAndRules)
  {
    role = (Rolesinfo)this.rolesinfoDao.save(role);
    
    Specification<RolesAuthority> spec = QueryHelper.filter("rolesinfo.id", MatchType.EQ, role.getId());
    List<RolesAuthority> oldAuth = this.rolesAuthorityDao.findAll(spec);
    this.rolesAuthorityDao.delete(oldAuth);
    for (Map.Entry<String, String[]> entry : authAndRules.entrySet())
    {
      RolesAuthority rolesAuthority = new RolesAuthority();
      Authority auth = (Authority)this.authorityDao.findOne((String)entry.getKey());
      Set<RulesInfo> rulesInfos = new HashSet();
      for (String rule : (String[])entry.getValue()) {
        if (StringUtils.isNotBlank(rule))
        {
          RulesInfo rulesInfo = (RulesInfo)this.rulesInfoDao.findOne(rule);
          rulesInfos.add(rulesInfo);
        }
      }
      rolesAuthority.setAuthority(auth);
      rolesAuthority.setRolesinfo(role);
      if (rulesInfos.size() != 0) {
        rolesAuthority.setRulesInfos(rulesInfos);
      }
      this.rolesAuthorityDao.save(rolesAuthority);
    }
  }
}
