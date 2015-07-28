package com.buswe.moudle.core.service;

import java.util.List;

import com.buswe.moudle.core.entity.ReportParameter;

public abstract interface ReportQueryService
{
  public abstract List<ReportParameter> getReportParameters(String paramString);
}
