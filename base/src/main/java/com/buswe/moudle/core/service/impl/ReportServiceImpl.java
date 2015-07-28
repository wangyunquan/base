package com.buswe.moudle.core.service.impl;

import javax.annotation.Resource;

import com.buswe.base.dao.springdata.BaseRepository;
import com.buswe.base.service.BaseServiceImpl;
import com.buswe.moudle.core.dao.ReportDao;
import com.buswe.moudle.core.entity.Report;
import com.buswe.moudle.core.service.ReportService;

public class ReportServiceImpl
  extends BaseServiceImpl<Report>
  implements ReportService
{
  @Resource
  private ReportDao reportDao;
  
  public BaseRepository<Report, String> getDao()
  {
    return this.reportDao;
  }
}
