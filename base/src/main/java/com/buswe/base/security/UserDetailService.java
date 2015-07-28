package com.buswe.base.security;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buswe.moudle.core.entity.Rolesinfo;
import com.buswe.moudle.core.entity.RulesInfo;
import com.buswe.moudle.core.entity.UserBasic;
import com.buswe.moudle.core.service.SecurityService;
import com.buswe.moudle.core.service.UserBasicService;

@Service("userDetailsService")
@Transactional("jpaTransaction")
public class UserDetailService
  implements UserDetailsService
{
  @Resource
  private UserBasicService userBasicService;
  @Resource
  private SecurityService securityService;
  
  public UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException, DataAccessException
  {
    UserBasic user = this.userBasicService.findByLoginName(username);
    if (user == null) {
      throw new UsernameNotFoundException("用户不存在");
    }
    boolean enabled = true;
    boolean accountNonExpired = true;
    boolean credentialsNonExpired = true;
    boolean accountNonLocked = true;
    Set<GrantedAuthority> grantedAuths = obtainGrantedAuthorities(user);
    UserDetail userdetail = new UserDetail(
      user.getLoginName(), user.getPassword(), enabled, 
      accountNonExpired, credentialsNonExpired, accountNonLocked, 
      grantedAuths);
    userdetail.setUserId(user.getId());
    
    return userdetail;
  }
  
  private Set<GrantedAuthority> obtainGrantedAuthorities(UserBasic user)
  {
    Set<GrantedAuthority> gaSet = new HashSet();
    Iterator localIterator2;
    for (Iterator localIterator1 = user.getRolesinfos().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
    {
      Rolesinfo ri = (Rolesinfo)localIterator1.next();
      Map<String, Set<RulesInfo>> ramap = this.securityService.getRoleAuthority(ri
        .getId());
      localIterator2 = ramap.entrySet().iterator(); 
      Map.Entry<String, Set<RulesInfo>> entry = (Map.Entry)localIterator2.next();
      gaSet.add(new GrantedAuthorityRules((String)entry.getKey(), (Set)entry.getValue()));
    }
    return gaSet;
  }
}
