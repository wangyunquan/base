package com.buswe.base.security;

import java.util.Collection;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.AntPathMatcher;

public class RoleRulesVoter
  extends RoleVoter
{
  private final AntPathMatcher antMatcher = new AntPathMatcher();
  
  public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes)
  {
    int result = 0;
    UserDetail user = (UserDetail)authentication.getPrincipal();
    user.getRulesInfo().clear();
    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    for (ConfigAttribute attribute : attributes) {
      if (supports(attribute))
      {
        result = 1;
        for (GrantedAuthority authority : authorities)
        {
          GrantedAuthorityRules gra = (GrantedAuthorityRules)authority;
          if (this.antMatcher.match(gra.getAuthority(), attribute.getAttribute()))
          {
            user.getRulesInfo().addAll(gra.getRulesInfo());
            result = 1;
          }
        }
      }
    }
    return result;
  }
}
