package com.buswe.moudle.core.service;

import com.buswe.base.service.BaseService;
import com.buswe.moudle.core.entity.BackupDatabase;

public abstract interface BackupDatabaseService
  extends BaseService<BackupDatabase>
{
  public abstract Boolean backUpdataAll(String paramString);
  
  public abstract Boolean restoreData(String paramString);
  
  public abstract Boolean restoreData(BackupDatabase paramBackupDatabase);
}
