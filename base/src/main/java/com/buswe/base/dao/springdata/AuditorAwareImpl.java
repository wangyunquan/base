package com.buswe.base.dao.springdata;

import org.springframework.data.domain.AuditorAware;

import com.buswe.moudle.core.entity.UserBasic;

public class AuditorAwareImpl
  implements AuditorAware<UserBasic>
{
  public UserBasic getCurrentAuditor()
  {
    return null;
  }
}
