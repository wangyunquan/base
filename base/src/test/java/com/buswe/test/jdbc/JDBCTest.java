package com.buswe.test.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class JDBCTest {

	public static void main(String[] args) throws Exception {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		   Connection conn = DriverManager
		     .getConnection("jdbc:mysql://localhost/yiding?user=root&password=741369");
 
DatabaseMetaData dbMetData = conn.getMetaData();  

ResultSet rs = dbMetData.getTables(conn.getCatalog(), "root", null, new String[]{"TABLE"});
while(rs.next())
{
	 String tableName=rs.getString("TABLE_NAME");
	 System.out.println(tableName);
	 ResultSet colRet = dbMetData.getColumns(null,"%", tableName,"%");
	 
	 while(colRet.next())
	 {
		 System.out.print("，"+colRet.getString("COLUMN_NAME"));
	 }
	 System.out.println();
	}
 

	}
 
}
