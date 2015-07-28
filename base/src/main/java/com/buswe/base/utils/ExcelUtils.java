package com.buswe.base.utils;

import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.buswe.base.service.ServiceException;

public class ExcelUtils
{
  private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);
  private static final String DATE_FORMAT = "yyyy-MM-dd";
  private CellStyle dateStyle;
  
  public static Object getCellValue(Cell cell)
  {
    if (cell == null) {
      return null;
    }
    try
    {
      if (cell.getCellType() == 5)
      {
        logger.error("Cell content is error. Sheet: {}, row: {}, column: {}", 
          new Object[] { cell.getSheet().getSheetName(), Integer.valueOf(cell.getRowIndex()), Integer.valueOf(cell.getColumnIndex()) });
        return null;
      }
      if (cell.getCellType() == 3) {
        return null;
      }
      if (cell.getCellType() == 4) {
        return Boolean.valueOf(cell.getBooleanCellValue());
      }
      if (cell.getCellType() == 0) {
        return Double.valueOf(cell.getNumericCellValue());
      }
      if (cell.getCellType() == 1) {
        return cell.getStringCellValue();
      }
    }
    catch (IllegalStateException e)
    {
      logger.error(e.getLocalizedMessage());
      logger.error("Read cell error. Sheet: {}, row: {}, column: {}", 
        new Object[] { cell.getSheet().getSheetName(), Integer.valueOf(cell.getRowIndex()), Integer.valueOf(cell.getColumnIndex()) });
      throw new ServiceException(e);
    }
    return null;
  }
  
  public static void setCellValue(Cell cell, Object data)
  {
    if (data == null)
    {
      cell.setCellValue("");
      return;
    }
    if ((data instanceof Date))
    {
      cell.setCellValue((Date)data);
      cell.setCellStyle(getDateStyle("yyyy-MM-dd", cell.getRow().getSheet().getWorkbook()));
      return;
    }
    if ((data instanceof Boolean))
    {
      cell.setCellValue(((Boolean)data).booleanValue());
      return;
    }
    if ((data instanceof Number))
    {
      cell.setCellValue(((Number)data).doubleValue());
      return;
    }
    cell.setCellValue(data.toString());
  }
  
  private static CellStyle getDateStyle(String dateFormat, Workbook workbook)
  {
    DataFormat format = workbook.createDataFormat();
    CellStyle result = workbook.createCellStyle();
    result.setDataFormat(format.getFormat(dateFormat));
    return result;
  }
}
