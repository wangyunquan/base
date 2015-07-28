package com.buswe.moudle.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buswe.base.dao.MatchType;
import com.buswe.base.dao.PropertyFilter;
import com.buswe.base.dao.QueryHelper;
import com.buswe.base.service.CodeHolder;
import com.buswe.moudle.core.dao.AuthorityDao;
import com.buswe.moudle.core.dao.RulesInfoDao;
import com.buswe.moudle.core.entity.Authority;
import com.buswe.moudle.core.entity.CodeValue;
import com.buswe.moudle.core.entity.RulesInfo;
import com.buswe.moudle.core.service.AppCodeService;
import com.buswe.moudle.core.service.AuthorityService;

@Service("authorityService")
@Transactional("jpaTransaction")
public class AuthorityServiceImpl
  implements AuthorityService
{
  @Resource(name="authorityDao")
  private AuthorityDao authorityDao;
  @Resource
  private AppCodeService appCodeService;
  @Resource
  private RulesInfoDao rulesInfoDao;
  
  public Map<String, List<Authority>> authorityTree()
  {
    Set<CodeValue> alltypes = CodeHolder.getAllCodeValue("authoritytype");
    Map<String, List<Authority>> tree = new HashMap();
    for (CodeValue authorityType : alltypes)
    {
      Specification<Authority> spec = QueryHelper.filter("authtype", MatchType.EQ, authorityType.getCode());
      List<Authority> auth = this.authorityDao.findAll(spec);
      tree.put(authorityType.getValue().toString(), auth);
    }
    return tree;
  }
  
  public Page<Authority> findPage(Pageable page, List<PropertyFilter> filters)
  {
    Specification<Authority> specification = QueryHelper.bySearchFilter(filters);
    return this.authorityDao.findAll(specification, page);
  }
  
  public Authority saveAuthority(Authority entity)
  {
    entity.setName(entity.getName().toUpperCase());
    return (Authority)this.authorityDao.save(entity);
  }
  
  public Authority getAuthority(String id)
  {
    return (Authority)this.authorityDao.findOne(id);
  }
  
  public void deleteAuthority(String ids)
  {
    this.authorityDao.delete(ids);
  }
  
  public RulesInfo saveRules(RulesInfo entity)
  {
    return (RulesInfo)this.rulesInfoDao.save(entity);
  }
  
  public List<RulesInfo> getAllRules()
  {
    return this.rulesInfoDao.findAll();
  }
  
  public RulesInfo getRulesInfo(String id)
  {
    return (RulesInfo)this.rulesInfoDao.findOne(id);
  }
  
  public void deleteRulesInfo(String id)
  {
    this.rulesInfoDao.delete(id);
  }
}
