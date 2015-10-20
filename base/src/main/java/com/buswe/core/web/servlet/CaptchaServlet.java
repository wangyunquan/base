package com.buswe.core.web.servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.buswe.base.web.captcha.JCaptcha;

public class CaptchaServlet  extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6809764118418373679L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException
	        {
		response.setDateHeader("Expires", 0L);  
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
        response.setHeader("Pragma", "no-cache");  
        response.setContentType("image/jpeg");  
 
        String id = request.getRequestedSessionId();  
        BufferedImage bi = JCaptcha.captchaService.getImageChallengeForID(id);  
        ServletOutputStream out = response.getOutputStream();  
        ImageIO.write(bi, "jpg", out);  
        try {  
            out.flush();  
        } finally {  
            out.close();  
        }  
	        }
}
