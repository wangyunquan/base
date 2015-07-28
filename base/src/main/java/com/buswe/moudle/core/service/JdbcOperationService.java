package com.buswe.moudle.core.service;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract interface JdbcOperationService
{
  public abstract List<Map<String, ?>> queryList(String paramString, Map<String, Object> paramMap);
  
  public abstract JdbcTemplate getJdbctemplate();
}
