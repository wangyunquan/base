package com.buswe.moudle.core.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specifications;

import com.buswe.base.service.BaseService;
import com.buswe.moudle.core.entity.GroupInfo;

public abstract interface GroupInfoService
  extends BaseService<GroupInfo>
{
  public abstract List<GroupInfo> findAll(Specifications paramSpecifications);
}
