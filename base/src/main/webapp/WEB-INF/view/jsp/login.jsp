<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglibs.jsp"%>
 <%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <title> </title>
    <!-- Bootstrap core CSS -->
    <link href="${ctx}/static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
    body {
font-family: "微软雅黑", Lucida Sans Unicode, Hiragino Sans GB, WenQuanYi Micro Hei, Verdana, Aril, sans-serif;
}
    </style>
  </head>
  <body>
<div class="container-fluid"  >
    <div class="row" >
        <div class="col-md-4 col-md-offset-4">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <span class="glyphicon glyphicon-lock"></span> 登 录</div>
                <div class="panel-body">
                    <form class="form-horizontal" action="${ctx}/login" method="POST"  role="form">
                    <div class="form-group">
                        <label for="inputEmail3" class="col-sm-3 control-label">
                            用户名 </label>
                        <div class="col-sm-9">
                   <input type="text"  id="username" class="form-control" placeholder="用户名"  name="username" required autofocus>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputPassword3" class="col-sm-3 control-label">
                            密码</label>
                        <div class="col-sm-9">
         <input type="password" id="inputPassword" class="form-control" placeholder="密码"  name="password" required>
                        </div>
                    </div>
                      <c:if test="${jcaptchaEbabled}">
                            <div class="form-group">
                        <label for="inputPassword3" class="col-sm-3 control-label">
                           验证码</label>
                        <div class="col-sm-5">  <input type="text" class="form-control"  name="jcaptchaCode">

                        </div>
                        <div class="col-sm-4">
                                
<img class="jcaptcha-btn jcaptcha-img" 
src="${pageContext.request.contextPath}/captcha/" title="点击更换验证码" id="loginCaptcha"   
 onClick="this.src='${pageContext.request.contextPath}/captcha/'+Math.random()"/> 
                         </div>
                    </div>
                    </c:if> 
                    <div class="form-group">
                        <div class="col-sm-offset-3 col-sm-9">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" id="rememberMe" name="rememberMe"/>
                                   记住我
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group last">
                        <div class="col-sm-offset-3 col-sm-9">
                            <button type="submit" class="btn btn-success btn-sm">
                                登录</button>
                                 <button type="reset" class="btn btn-default btn-sm">
                                重置</button>
                        </div>
                    </div>
                    </form>
                </div>
                <div class="panel-footer">
                   	<%
	String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
	if(error != null){
	%>
                  <div class="alert alert-warning alert-dismissible" role="alert">
			  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			  <%=error %>
		</div>
                    <%
	}
	%>
                    </div>
            </div>
        </div>
    </div>
</div>
<script src="${ctx}/static/js/jquery/jquery.js"></script>
<script src="${ctx}/static/bootstrap/js/bootstrap.js"></script>
</body>

</html>