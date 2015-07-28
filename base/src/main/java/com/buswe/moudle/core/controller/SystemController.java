package com.buswe.moudle.core.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/core/system"})
public class SystemController
{
  @RequestMapping
  public String index(HttpServletRequest request, Model model)
  {
    return "core/system/index";
  }
}
