//package com.buswe.base.security;
//
//import java.util.Set;
//
//import org.springframework.security.core.GrantedAuthority;
//
//import com.buswe.moudle.core.entity.RulesInfo;
//
//public class GrantedAuthorityRules
//  implements GrantedAuthority
//{
//  private static final long serialVersionUID = 3485085668728984575L;
//  private String authority;
//  private Set<RulesInfo> rulesInfo;
//  
//  public String getAuthority()
//  {
//    return this.authority;
//  }
//  
//  public GrantedAuthorityRules(String authority, Set<RulesInfo> rulesInfo)
//  {
//    this.authority = authority;
//    this.rulesInfo = rulesInfo;
//  }
//  
//  public Set<RulesInfo> getRulesInfo()
//  {
//    return this.rulesInfo;
//  }
//  
//  public void setRulesInfo(Set<RulesInfo> rulesInfo)
//  {
//    this.rulesInfo = rulesInfo;
//  }
//  
//  public void setAuthority(String authority)
//  {
//    this.authority = authority;
//  }
//}
