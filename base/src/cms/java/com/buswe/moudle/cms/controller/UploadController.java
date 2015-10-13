package com.buswe.moudle.cms.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.buswe.base.config.SystemEnvy;
import com.buswe.base.utils.image.ImageUtils;
import com.buswe.base.web.WebHelper;
import com.buswe.moudle.cms.CmsConfig;

@Controller
@RequestMapping({"/cms/back/upload"})
public class UploadController
{
  @RequestMapping({"/file"})
  @ResponseBody
  public String uploadFile(HttpServletRequest request, HttpServletResponse response)
    throws IOException
  {
    StringBuffer outString = new StringBuffer("");
    
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
    Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
    if (CollectionUtils.isEmpty(fileMap)) {
      throw new IllegalStateException("No upload file found!");
    }
    for (MultipartFile file : fileMap.values())
    {
      String extension = FilenameUtils.getExtension(
        file.getOriginalFilename()).toLowerCase();
      
      String fileUrl = getFilepath(extension);
      File destFile = new File(SystemEnvy.WEBROOT + File.separator + fileUrl);
      FileUtils.touch(destFile);
      if ((ImageUtils.isImgExtension(extension)) 
          )
      {
        String width = request.getParameter("width");
        String height = request.getParameter("height");
        BufferedImage buff = ImageIO.read(file.getInputStream());
        if ((!StringUtils.isNotBlank(width)) || 
          (!StringUtils.isNotBlank(height))) {
          file.transferTo(destFile);
        }
      }
      else
      {
        file.transferTo(destFile);
      }
      String fileName = file.getOriginalFilename();
      long fileLength = file.getSize();
      
      fileUrl = WebHelper.getRootUrl() + "/" + fileUrl;
      fileUrl = fileUrl.replace("\\", "/");
      


      String ckeditor = request.getParameter("CKEditor");
      if (ckeditor != null)
      {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        

        String error = "";
        String callback = request.getParameter("CKEditorFuncNum");
        outString.append("<script type=\"text/javascript\">");
        outString.append("(function(){var d=document.domain;while (true){try{var A=window.parent.document.domain;break;}catch(e) {};d=d.replace(/.*?(?:\\.|$)/,'');if (d.length==0) break;try{document.domain=d;}catch (e){break;}}})();\n");
        if (StringUtils.isBlank(error)) {
          outString.append("window.parent.CKEDITOR.tools.callFunction(" + 
            callback + ",'" + fileUrl + "',''" + ");");
        } else {
          outString.append("alert('" + error + "');");
        }
        outString.append("</script>");
      }
    }
    return outString.toString();
  }
  
  private String getFilepath(String extension)
  {
    String uuid = UUID.randomUUID().toString();
    String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
    String path = "";
    if (ImageUtils.isImgExtension(extension)) {
      path = CmsConfig.getImageFolder() + File.separator + date + File.separator;
    }
    return path + uuid + "." + extension;
  }
}
