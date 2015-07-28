package com.buswe.moudle.core.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buswe.base.dao.springdata.BaseRepository;
import com.buswe.base.service.BaseServiceImpl;
import com.buswe.moudle.core.dao.DynamicColumnDao;
import com.buswe.moudle.core.dao.DynamicReportDao;
import com.buswe.moudle.core.entity.DynamicColumn;
import com.buswe.moudle.core.entity.DynamicReport;
import com.buswe.moudle.core.service.DynamicReportService;

@Service("dynamicReportServiceImpl")
@Transactional("jpaTransaction")
public class DynamicReportServiceImpl
  extends BaseServiceImpl<DynamicReport>
  implements DynamicReportService
{
  @Resource
  private DynamicReportDao dynamicReportDao;
  @Resource
  private DynamicColumnDao dynamicColumnDao;
  @Resource
  DataSource dataSource;
  
  public BaseRepository<DynamicReport, String> getDao()
  {
    return this.dynamicReportDao;
  }
  
  public List<DynamicColumn> getQueryColumn(String reportId)
  {
    List<DynamicColumn> columns = new ArrayList();
    DynamicReport report = (DynamicReport)this.dynamicReportDao.findOne(reportId);
    String sqlType = report.getSqlType();
    if (sqlType.equalsIgnoreCase("JDBC"))
    {
      String sql = report.getSqlString();
      sql = subWhereSql(sql);
      Connection conn = null;
      PreparedStatement ps = null;
      ResultSet rs = null;
      try
      {
        conn = this.dataSource.getConnection();
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        for (int i = 1; i <= columnCount; i++)
        {
          String columnName = JdbcUtils.lookupColumnName(rsmd, i);
          Integer type = Integer.valueOf(rsmd.getColumnType(i));
          DynamicColumn dm = new DynamicColumn();
          dm.setName(columnName);
          dm.setType(type.toString());
          columns.add(dm);
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      finally
      {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(ps);
        JdbcUtils.closeConnection(conn);
      }
    }
    return columns;
  }
  
  private String subWhereSql(String sql)
  {
    String sqltoChange = sql.toUpperCase();
    if (sqltoChange.contains("WHERE"))
    {
      int index = sqltoChange.indexOf("WHERE");
      sql = sql.substring(0, index);
    }
    return sql;
  }
  
  public DynamicReport generateColumn(DynamicReport entity, List<DynamicColumn> columnList)
    throws Exception
  {
    this.dynamicReportDao.save(entity);
    
    entity.setFileUrl("D:\\entity.xml");
    for (DynamicColumn column : columnList)
    {
      column.setDynamicReport(entity);
      this.dynamicColumnDao.save(column);
      entity.getColumns().add(column);
    }
    return entity;
  }
}
