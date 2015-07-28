package com.buswe.moudle.core.service;

import java.util.List;

import com.buswe.base.service.BaseService;
import com.buswe.moudle.core.entity.DynamicColumn;
import com.buswe.moudle.core.entity.DynamicReport;

public abstract interface DynamicReportService
  extends BaseService<DynamicReport>
{
  public abstract List<DynamicColumn> getQueryColumn(String paramString);
  
  public abstract DynamicReport generateColumn(DynamicReport paramDynamicReport, List<DynamicColumn> paramList)
    throws Exception;
}
