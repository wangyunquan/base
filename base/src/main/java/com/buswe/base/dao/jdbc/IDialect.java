/**
 * 数据库方言接口
 */

package com.buswe.base.dao.jdbc;

import org.springframework.data.domain.Pageable;


public  interface IDialect {
	/**
	 * 得到分页语句
	 * @param sql 正常的select 语句,没有order by 
	 * @param orderby order by 语句
	 * @param page 分页对象
	 * @return
	 */
	String getPageSql(String sql,String orderby,Pageable page);
	/**
	 * 获取数据库类型
	 * @return
	 */
	String getDataDaseType();
	/**
	 * 是否包含 rownum 列
	 * @return
	 */
	boolean isRowNumber();
	
}
