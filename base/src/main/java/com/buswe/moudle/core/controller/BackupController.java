package com.buswe.moudle.core.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.buswe.base.config.AppConfig;
import com.buswe.base.dao.PropertyFilter;
import com.buswe.base.utils.BackUpUtils;
import com.buswe.base.utils.DateTimeUtils;
import com.buswe.base.utils.Servlets;
import com.buswe.base.web.WebHelper;
import com.buswe.moudle.core.entity.BackupDatabase;
import com.buswe.moudle.core.service.BackupDatabaseService;

@Controller
@RequestMapping({"/core/system/backup"})
public class BackupController
{
  @Resource
  BackupDatabaseService service;
  
  @RequestMapping
  public String list(Pageable page, HttpServletRequest request, Model model)
  {
    List<PropertyFilter> filters = WebHelper.filterRequest(request);
    model.addAttribute("page", 
      this.service.findPage(page, filters));
    return "core/system/backupList";
  }
  
  @RequestMapping(value={"/bakupload"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String upload(@RequestParam MultipartFile file, String backName, Model model)
  {
    String dateTime = 
      DateTimeUtils.getCurrentDateString("yyyyMMddHHmmss");
    String zipFileName = AppConfig.getBackDbFilePath() + File.separator + dateTime + ".zip";
    File uploadedFile = new File(zipFileName);
    long size = file.getSize();
    try
    {
      uploadedFile.mkdirs();
      file.transferTo(uploadedFile);
      boolean iszip = BackUpUtils.checkZip(uploadedFile);
      if (!iszip) {
        return "redirect:";
      }
    }
    catch (IllegalStateException|IOException e)
    {
      e.printStackTrace();
      
      BackupDatabase entity = new BackupDatabase();
      entity.setBackName(backName);
      entity.setCreateTime(new Date());
      entity.setFileName(dateTime + ".zip");
      entity.setFilepath(AppConfig.getBackDbFilePath() + File.separator);
      entity.setSuccess(Boolean.valueOf(true));
      entity.setFilesize(Integer.valueOf(new Long(size / 1024L).intValue()));
      this.service.save(entity);
    }
    return "redirect:";
  }
  
  @RequestMapping(value={"/download"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public void handleFileDownload(String id, HttpServletResponse res)
  {
    BackupDatabase entity = (BackupDatabase)this.service.get(id);
    Servlets.setFileDownloadHeader(res, entity.getBackName() + ".zip");
    File file = new File(AppConfig.getBackDbFilePath() + File.separator + entity.getFileName());
    try
    {
      IOUtils.copy(new FileInputStream(file), res.getOutputStream());
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  @RequestMapping(value={"/bakupload"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String uploadInput(Model model)
  {
    return "core/system/backupUpload";
  }
  
  @RequestMapping({"/restore"})
  public String restore(String id)
  {
    this.service.restoreData(id);
    return "redirect:";
  }
  
  @RequestMapping({"/save"})
  public String save(@Valid BackupDatabase entity, BindingResult bindingResult)
  {
    if (!bindingResult.hasErrors()) {
      try
      {
        this.service.backUpdataAll(entity.getBackName());
      }
      catch (Exception localException) {}
    }
    return "redirect:";
  }
  
  @RequestMapping({"/input"})
  public String input(String id, Model model)
  {
    BackupDatabase entity = null;
    if (StringUtils.isNotBlank(id)) {
      entity = (BackupDatabase)this.service.get(id);
    } else {
      entity = new BackupDatabase();
    }
    model.addAttribute("entity", entity);
    return "core/system/backupInput";
  }
  
  @RequestMapping({"/delete"})
  public String delete(String[] id)
  {
    for (String ids : id) {
      this.service.delete(ids);
    }
    return "redirect:";
  }
}
