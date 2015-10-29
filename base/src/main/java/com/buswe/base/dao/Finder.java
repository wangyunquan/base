package com.buswe.base.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 封装查询接口
 * 
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version 2013-03-19 11:08:15
 * @see org.springrain.frame.util.Finder
 */
public class Finder {
	private Map<String,Object> params=null;
    private StringBuffer sql=new StringBuffer();
   // private String countSql=null;
    private String orderSql=null;
    //存储过程
    private String procName=null;
    //函数
    private String funName=null;
    
    private String pageSql=null;
    //设置总条数查询的finder
    private Finder countFinder=null;
    
    
    public Finder(){}
    
    public Finder (String s){
    	this.sql.append(s);
    }
    
    /** 添加子句 */
	public Finder append(String s) {
		sql.append(s);
		return this;
	}

	/**
	 * 设置参数。
	 * 
	 * @param param
	 * @param value
	 * @return
	 */
	public Finder setParam(String param, Object value) {
		if(params==null)
			params=new HashMap<String,Object>();
		params.put(param, value);
		return this;
	}
	/**
	 * 根据 Entity(分表)或者Class返回查询语句的Finder
	 * 等同 getSelectFinder(User.class)==return new Finder("SELECT * FROM t_User ");
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public static Finder getSelectFinder(Object object) throws Exception{
		return getSelectFinder(object, "*");
	}
	/**
	 * 根据 Entity(分表)或者Class返回更新语句的Finder
	 * 等同 getUpdateFinder(User.class,"id,name")==return new Finder("SELECT id,name FROM t_User ");
	 * @param object,updatesql
	 * @return
	 * @throws Exception
	 */
	
	public static Finder getSelectFinder(Object object,String selectsql) throws Exception{
		if(object==null){
			return null;
		}
		if(StringUtils.isBlank(selectsql)){
			selectsql="*";
		}
		String tableName=getTableName(object);
		Finder finder=new Finder("SELECT ");
		finder.append(selectsql).append(" FROM ").append(tableName).append(" ");
		return finder;
		
	}
	
	/**
	 * 根据 Entity(分表)或者Class返回更新语句的Finder
	 * 等同 getUpdateFinder(User.class)==return new Finder("UPDATE t_User SET ");
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public static Finder getUpdateFinder(Object object) throws Exception{
		return getUpdateFinder(object, null);
	}
	
	/**
	 * 根据 Entity(分表)或者Class返回更新语句的Finder
	 * 等同 getUpdateFinder(User.class,"name=:name")==return new Finder("UPDATE t_User SET name=:name ");
	 * @param object,updatesql
	 * @return
	 * @throws Exception
	 */
	
	public static Finder getUpdateFinder(Object object,String updatesql) throws Exception{
		if(object==null){
			return null;
		}
		String tableName=getTableName(object);
		Finder finder=new Finder("UPDATE ");
		finder.append(tableName).append(" SET ");
		if(StringUtils.isNotBlank(updatesql)){
			finder.append(updatesql).append(" ");
		}
		return finder;
		
	}
	
	/**
	 * 根据 Entity(分表)或者Class返回删除语句的Finder
	 * 等同 getDeleteFinder(User.class)==return new Finder("DELETE FROM  t_user ");
	 * @param object
	 * @return
	 * @throws Exception
	 */
	
	public static Finder getDeleteFinder(Object object) throws Exception{
		if(object==null){
			return null;
		}
		String tableName=getTableName(object);
		Finder finder=new Finder("DELETE FROM  ");
		finder.append(tableName).append(" ");
		return finder;
	}
	
	
	public static String getTableName(Object object) throws Exception {
		return ClassUtils.getTableName(object);
	}
	
	
	
	
	
	

	public Map<String, Object> getParams() {
		return params;
	}

	/*
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	*/

	public String getSql() {
		if(sql==null)
			return null;
		return sql.toString();
	}

	public void setSql(String sql) {
		this.sql = new StringBuffer(sql);
	}

	

	public String getOrderSql() {
		return orderSql;
	}

	public void setOrderSql(String orderSql) {
		this.orderSql = orderSql;
	}

	public String getPageSql() {
		return pageSql;
	}

	public void setPageSql(String pageSql) {
		this.pageSql = pageSql;
	}

	public String getProcName() {
		return procName;
	}

	public void setProcName(String procName) {
		this.procName = procName;
	}

	public String getFunName() {
		return funName;
	}

	public void setFunName(String funName) {
		this.funName = funName;
	}
/**
 * 查询总条数的 Finder对象
 * @return Finder
 */
	public Finder getCountFinder() {
		return countFinder;
	}

	public void setCountFinder(Finder countFinder) {
		this.countFinder = countFinder;
	}

}
