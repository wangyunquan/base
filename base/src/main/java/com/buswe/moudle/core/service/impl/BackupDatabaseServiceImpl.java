package com.buswe.moudle.core.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buswe.base.config.AppConfig;
import com.buswe.base.dao.springdata.BaseRepository;
import com.buswe.base.service.BaseServiceImpl;
import com.buswe.base.utils.DateTimeUtils;
import com.buswe.base.utils.ZipUtils;
import com.buswe.moudle.core.dao.BackupDatabaseDao;
import com.buswe.moudle.core.entity.BackupDatabase;
import com.buswe.moudle.core.service.BackupDatabaseService;

@Service("backupDatabaseService")
@Transactional("jpaTransaction")
public class BackupDatabaseServiceImpl
  extends BaseServiceImpl<BackupDatabase>
  implements BackupDatabaseService
{
  @Resource
  private BackupDatabaseDao backupDatabaseDao;
  
  public BaseRepository<BackupDatabase, String> getDao()
  {
    return this.backupDatabaseDao;
  }
  
  public void delete(String id)
  {
    BackupDatabase bak = (BackupDatabase)get(id);
    String zipFile = bak.getFilepath() + File.separator + bak.getFileName();
    super.delete(id);
    new File(zipFile).deleteOnExit();
  }
  
  public Boolean backUpdataAll(String backName)
  {
    String host = AppConfig.getDbhost();
    String username = AppConfig.getDbUser();
    String password = AppConfig.getDbPass();
    String dbName = AppConfig.getDatabase();
    Integer port = AppConfig.getDbport();
    String[] ignortables = AppConfig.getDbBackIgnore();
    String mysqlbinPath = AppConfig.getMysqlhome() + File.separator + "bin" + File.separator;
    
    String backUpDir = AppConfig.getBackDbFilePath();
    File backDir = new File(backUpDir);
    if (!backDir.exists()) {
      backDir.mkdirs();
    }
    File errorFilepath = null;
    File backupFilePath = null;
    try
    {
      errorFilepath = new File(backUpDir + File.separator + "MYSQLERROR" + ".txt");
      errorFilepath.createNewFile();
      backupFilePath = new File(backUpDir + File.separator + "dbrestore" + ".sql");
      backupFilePath.createNewFile();
    }
    catch (IOException e1)
    {
      e1.printStackTrace();
    }
    String dateTime = 
      DateTimeUtils.getCurrentDateString("yyyyMMddHHmmss");
    
    String ignortable = " ";
    if (ignortables.length > 0) {
      for (String ign : ignortables) {
        ignortable = ignortable + " --ignore-table=" + dbName + "." + ign;
      }
    }
    String command = mysqlbinPath + "mysqldump -h" + 
      host + 
      " -p" + 
      port + 
      " -u" + 
      username + 
      " -p" + 
      password + 
      " --database " + 
      dbName + 
      " --opt --skip-comments --complete-insert --hex-blob --default-character-set=utf8" + 
      " --result-file=" + backupFilePath.getAbsolutePath() + ignortable + 
      " --log-error=" + errorFilepath.getAbsolutePath();
    
    BackupDatabase bak = new BackupDatabase();
    
    Runtime runt = Runtime.getRuntime();
    try
    {
      Process proc = runt.exec(command);
      int tag = proc.waitFor();
      if (tag == 0)
      {
        if (errorFilepath.length() != 0L) {
          bak.setSuccess(Boolean.valueOf(false));
        }
      }
      else {
        bak.setSuccess(Boolean.valueOf(false));
      }
    }
    catch (IOException|InterruptedException e)
    {
      bak.setSuccess(Boolean.valueOf(false));
      e.printStackTrace();
    }
    String zipFileName = AppConfig.getBackDbFilePath() + File.separator + dateTime + ".zip";
    File zip = new File(zipFileName);
    try
    {
      ZipUtils.zip(backupFilePath, zip);
      Long size = Long.valueOf(zip.length() / 1024L);
      bak.setBackName(backName);
      bak.setFilesize(Integer.valueOf(size.intValue()));
      bak.setSuccess(Boolean.valueOf(true));
      bak.setFileName(dateTime + ".zip");
    }
    catch (IOException e)
    {
      bak.setSuccess(Boolean.valueOf(false));
      e.printStackTrace();
    }
    finally
    {
      FileUtils.deleteQuietly(errorFilepath);
      

      FileUtils.deleteQuietly(backupFilePath);
    }
    save(bak);
    return bak.getSuccess();
  }
  
  public Boolean restoreData(String id)
  {
    BackupDatabase bak = (BackupDatabase)get(id);
    return restoreData(bak);
  }
  
  public BackupDatabase save(BackupDatabase entity)
  {
    entity.setCreateTime(new Date());
    entity.setFilepath(AppConfig.getBackDbFilePath());
    entity.setUserId("system");
    return (BackupDatabase)super.save(entity);
  }
  
  public Boolean restoreData(BackupDatabase bak)
  {
    String fileName = bak.getFileName();
    File restore = null;
    try
    {
      restore = File.createTempFile("dbrestore", ".sql");
    }
    catch (IOException e1)
    {
      e1.printStackTrace();
    }
    String zipFile = bak.getFilepath() + File.separator + fileName;
    File zip = new File(zipFile);
    try
    {
      ZipFile zf = new ZipFile(zip);
      Enumeration enu = zf.entries();
      ZipEntry file = (ZipEntry)enu.nextElement();
      InputStream fi = zf.getInputStream(file);
      FileOutputStream out = new FileOutputStream(restore);
      byte[] c = new byte[1024];
      int slen;
      while ((slen = fi.read(c, 0, c.length)) != -1)
      {
        out.write(c, 0, slen);
      }
      zf.close();
      out.close();
      fi.close();
      String mysqlbinPath = AppConfig.getMysqlhome() + File.separator + "bin" + File.separator;
      String command = mysqlbinPath + "mysql -h" + AppConfig.getDbhost() + " -p" + 
        AppConfig.getDbport() + " -u" + 
        AppConfig.getDbUser() + " -p" + 
        AppConfig.getDbPass() + "  < " + restore.getAbsolutePath();
      Runtime runt = Runtime.getRuntime();
      Process proc = runt.exec(command);
      int tag = proc.waitFor();
      Boolean localBoolean;
      if (tag == 0) {
        return Boolean.valueOf(true);
      }
      return Boolean.valueOf(false);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      restore.deleteOnExit();
    }
    return Boolean.valueOf(false);
  }
}
