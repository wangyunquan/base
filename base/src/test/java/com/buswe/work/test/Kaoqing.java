package com.buswe.work.test;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Random;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class Kaoqing {

	public static void main(String[] args) throws Exception {

         System.out.println(stime);
		}
	}

	public static void worktime() throws Exception {
		String workTime = "";
		String endWorkTime = "";
		String kaoqingshijian = "";
		Workbook wb = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(kaoqingshijian)));
		Sheet sheet = wb.getSheetAt(0);
		Iterator<Row> rowsIt = sheet.rowIterator();
		rowsIt.next();// First row is the sheet head
		while (rowsIt.hasNext()) {
			Row row = rowsIt.next();
			String shangban = row.getCell(3).getStringCellValue();
			if (shangban.equals("应上班")) {
				Cell beginTime = row.getCell(4);
			}
		}
	}
	
	
	public static String getworkTime()
	{
		int max = 29;
		int min = 15;
		Random random = new Random();
		int stime = random.nextInt(max) % (max - min + 1) + min;
	   String timeString="8:"+new Integer(stime).toString();
		return new Integer(stime).toString();
	}
}
