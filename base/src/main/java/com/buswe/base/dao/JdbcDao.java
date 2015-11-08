package com.buswe.base.dao;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;

import com.buswe.base.dao.jdbc.IDialect;

public interface JdbcDao {
	public abstract IDialect getDialect();
	public boolean showsql();
   public List<T> queryForList(String sql );
	
	
}
