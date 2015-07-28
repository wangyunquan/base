<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>王云权的折腾：<sitemesh:title /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="description" content="">
<meta name="author" content="">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Le styles -->
<link href="${ctx}/static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="${ctx}/static/bootstrap/css/bootstrap-theme.css" rel="stylesheet">
<link href="${ctx}/static/jquery-ui/jquery-ui.css" rel="stylesheet">
<link href="${ctx}/static/css/theme.css" rel="stylesheet">
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="${ctx}/static/bootstrap/js/html5shiv.js"></script>
    <![endif]-->
  <script src="${ctx}/static/js/jquery/jquery.js"></script>
  <script src="${ctx}/static/bootstrap/js/bootstrap.js"></script>
<script src="${ctx}/static/js/yunquan.js"></script>
<script src="${ctx}/static/js/cms.js"></script>
<script src="${ctx}/static/jquery-ui/jquery-ui.js"></script>
<script src="${ctx}/static/js/respond.min.js"></script>
<script src="${ctx}/static/js/bootbox/bootbox.js"></script>
<sitemesh:head />
</head>
<body>
    <div class="navbar navbar-default navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Project name</a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="#">Home</a></li>
            <li><a href="#about">About</a></li>
            <li><a href="#contact">Contact</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="#">Action</a></li>
                <li><a href="#">Another action</a></li>
                <li><a href="#">Something else here</a></li>
                <li class="divider"></li>
                <li class="dropdown-header">Nav header</li>
                <li><a href="#">Separated link</a></li>
                <li><a href="#">One more separated link</a></li>
              </ul>
            </li>
            
            <w:topMenu></w:topMenu>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li><a href="../navbar/">Default</a></li>
            <li><a href="../navbar-static-top/">Static top</a></li>
            <li class="active"><a href="./">Fixed top</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>
    
      <div class="container containerTop">
      	<div class="row row-offcanvas row-offcanvas-right">
		<div class="col-xs-12 col-md-2 sidebar-offcanvas" id="sidebar" role="navigation">
	 <w:sideBar></w:sideBar>
		</div>
		<div class="col-xs-12 col-md-10">
 
	
