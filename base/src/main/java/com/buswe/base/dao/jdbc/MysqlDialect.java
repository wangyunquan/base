package com.buswe.base.dao.jdbc;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component("mysqlDialect")
public class MysqlDialect implements IDialect {

	@Override
	public String getPageSql(String sql, String orderby, Pageable page) {
		// 设置分页参数
		int pageSize = page.getPageSize();
		int pageNo = page.getPageNumber();
		StringBuffer sb = new StringBuffer(sql);
		if (StringUtils.isNotBlank(orderby)) {
			sb.append(" ").append(orderby);
		}
		sb.append(" limit ").append(pageSize * (pageNo - 1)).append(",")
				.append(pageSize);
		return sb.toString();
	}

	@Override
	public String getDataDaseType() {
		return "mysql";
	}

	@Override
	public boolean isRowNumber() {
		return false;
	}

}
