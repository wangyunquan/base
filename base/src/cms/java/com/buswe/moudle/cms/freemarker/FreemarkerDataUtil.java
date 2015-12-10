package com.buswe.moudle.cms.freemarker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.buswe.base.config.ContextHolder;
import com.buswe.moudle.cms.entity.Site;
import com.buswe.moudle.cms.service.CategoryService;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;
import freemarker.template.TemplateScalarModel;

public class FreemarkerDataUtil
{
  public static void appdendData(Map<String, Object> map, Site site)
  {
    HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    String basePath = request.getContextPath();
    


    map.put("base", basePath);
    
    CategoryService catService = (CategoryService)ContextHolder.getBean("categoryService");
    


    map.put("site", site);
    map.put("allCat", catService.findBySiteId(site.getId()));
  }
  /**
   * 获取标签传入的变量
   * @param name
   * @param params
   * @return
   * @throws TemplateException
   */
	public static String getString(String name,
			Map<String, TemplateModel> params) throws TemplateException {
		TemplateModel model = params.get(name);
		if (model == null) {
			return null;
		}
		if (model instanceof TemplateScalarModel) {
			return ((TemplateScalarModel) model).getAsString();
		} else if ((model instanceof TemplateNumberModel)) {
			return ((TemplateNumberModel) model).getAsNumber().toString();
		} else {
		 throw new TemplateModelException(" name  is null ");
		 
		}
	}
	
  public static void makeStaticFile(String path, Map<String, Object> map, Template template)
    throws Exception
  {
    File file = new File(path);
    if (!file.exists()) {
      file.createNewFile();
    }
    FileOutputStream fos = null;
    Writer out = null;
    try
    {
      fos = new FileOutputStream(file);
      out = new OutputStreamWriter(fos, "UTF-8");
      template.process(map, out);
    }
    catch (Exception e)
    {
      throw e;
    }
    finally
    {
      if (out != null)
      {
        out.flush();
        out.close();
      }
      if (fos != null) {
        fos.close();
      }
    }
  }
}
