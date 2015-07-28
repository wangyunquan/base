package com.buswe.moudle.cms.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/cms/back"})
public class CmsBackController
{
  @RequestMapping
  public String index(HttpServletRequest request, Model model)
  {
    return "cms/back/index";
  }
}
