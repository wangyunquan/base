<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<meta name="description" content="">
<meta name="author" content="">
<title>磁力搜索,种子搜索</title>
<link href="${ctx}/static/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<!--[if lt IE 9]>
      <script src="${ctx}/static/bootstrap/js/html5shiv.min.js"></script>
      <script src="${ctx}/static/bootstrap/js/respond.min.js"></script>
    <![endif]-->
 <link href="${ctx}/static/css/dht.css" rel="stylesheet">
</head>
<body>
 <%@ include file="/WEB-INF/layouts/frontHeader.jsp"%>
	<div class="container">
		<div class="row">
			<div align="center">
				<h1>磁 力 搜 索</h1>
			</div>
			<div align="center">
				<h5>
					已收集种子信息${tatalDhtinfo}个
					</h3>
			</div>
			<p></p>
			<br />

			<form class="form-horizontal" action="./search.htm">
				<div class="form-group">
					<div class="col-sm-5  col-md-offset-3">
						<input type="text" class="form-control" id="q" name="q"
							placeholder="输入关键字">
					</div>
					<div class="col-sm-1">
						<button type="submit" class="btn btn-primary">
							<span class="glyphicon glyphicon-search"></span> 查询
						</button>
					</div>
					<div class="col-sm-3"></div>
				</div>
			</form>
		</div>
	</div>
	<script src="${ctx}/static/js/jquery/jquery.js"></script>
	<script src="${ctx}/static/bootstrap/js/bootstrap.min.js"></script>
	
		 <%@ include file="/WEB-INF/layouts/frontFooter.jsp"%>
</body>
</html>
