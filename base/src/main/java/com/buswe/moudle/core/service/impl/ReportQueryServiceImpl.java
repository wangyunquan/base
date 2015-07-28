package com.buswe.moudle.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.buswe.moudle.core.entity.ReportParameter;
import com.buswe.moudle.core.service.JdbcOperationService;
import com.buswe.moudle.core.service.ReportQueryService;
import com.buswe.moudle.core.service.ReportService;

public class ReportQueryServiceImpl
  implements ReportQueryService
{
  ReportService reportService;
  JdbcOperationService jdbcOperationService;
  
  public List<ReportParameter> getReportParameters(String reportId)
  {
    return null;
  }
  
  private Map<String, Object> getOutParam(List<ReportParameter> parameters)
  {
    Map<String, Object> paramMap = new HashMap();
    for (ReportParameter param : parameters) {
      paramMap.put(param.getName(), param.getValue());
    }
    return paramMap;
  }
  
  private Map<String, Object> getQueryParam(List<ReportParameter> parameters)
  {
    Map<String, Object> paramMap = new HashMap();
    for (ReportParameter param : parameters) {
      paramMap.put(param.getName(), param.getValue());
    }
    return paramMap;
  }
}
