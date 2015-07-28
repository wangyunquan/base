package com.buswe.base.config;

import org.springframework.core.env.Environment;

public class AppConfig
{
  public static final String DB_RESTORE_SQL = "dbrestore";
  private static String WEBAPP_ROOT = null;
  private static Integer DEFAULT_PAGENUMBER;
  private static String MYSQL_HOME = "";
  private static String DATABASE;
  private static String DATABASE_URL;
  private static String DATABASE_USERNAME;
  private static String DATABASE_PASSWORD;
  private static String DATABASE_PORT;
  private static String[] BACKUP_IGNORE_TABLE;
  private static String BACKUP_DATA_DIR = "data";
  private static String REPORT_TEMPLATE_PATH;
  public static int DISPLAY_PAGE_NO = 20;
  public static int MESSAGE_SHOW_TIME = 300;
  
  static
  {
    Environment env = ContextHolder.getApplicationContext().getEnvironment();
    MYSQL_HOME = env.getProperty("database.home");
    DATABASE = env.getProperty("database.name");
    DATABASE_URL = env.getProperty("database.host");
    DATABASE_PORT = env.getProperty("database.port");
    DATABASE_USERNAME = env.getProperty("database.username");
    DATABASE_PASSWORD = env.getProperty("database.password");
    BACKUP_IGNORE_TABLE = env.getProperty("database.backupignor").split(",");
    DEFAULT_PAGENUMBER = Integer.valueOf(env.getProperty("page.default"));
  }
  
  public static Integer getPagesize()
  {
    return DEFAULT_PAGENUMBER;
  }
  
  public static String getMysqlhome()
  {
    return MYSQL_HOME;
  }
  
  public static String getDatabase()
  {
    return DATABASE;
  }
  
  public static String getDbUser()
  {
    return DATABASE_USERNAME;
  }
  
  public static String getDbPass()
  {
    return DATABASE_PASSWORD;
  }
  
  public static String getDbhost()
  {
    return DATABASE_URL;
  }
  
  public static Integer getDbport()
  {
    return Integer.valueOf(DATABASE_PORT);
  }
  
  public static String[] getDbBackIgnore()
  {
    return BACKUP_IGNORE_TABLE;
  }
  
  public static String reportFilePath()
  {
    return REPORT_TEMPLATE_PATH;
  }
  
  public static String getBackDbFilePath()
  {
    return getWebContainerPath() + BACKUP_DATA_DIR;
  }
  
  public static String getWebContainerPath()
  {
    return System.getProperty("webapp.root");
  }
  
  public static boolean isWindows()
  {
    return false;
  }
}
