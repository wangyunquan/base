<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/yunquan.tld" prefix="w"%>
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
<title>${dhtinfo.name}</title>
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
    <div class="container"   >
<div class="row"  >
<form class="form-horizontal" action="./search.htm">
   <div class="form-group">
        <div class="col-sm-3">
            </div>
    <div class="col-sm-5">
    </div>
     <div class="col-sm-1">
    </div>
    <div class="col-sm-3">
      </div>
  </div>
  </form>
      </div>
<div class="row"  >
<div class="col-sm-10 col-md-offset-1">
<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">${dhtinfo.name}</h3>
  </div>
  <div class="panel-body">
  
<textarea class="form-control" rows="3">magnet:?xt=urn:btih:${dhtinfo.infohash}</textarea>
  </div>
</div>
</div>
<div class="col-sm-1">
</div>
 </div>

<div class="row"  >
<div class="col-sm-10 col-md-offset-1">
<header ><a href="./get/${dhtinfo.infohash}.htm" target="_blank">${dhtinfo.name}</a></header>
<c:if test="${!dhtinfo.singerfile}">
<ul>
<c:forEach items="${dhtinfo.dhtfiles}" var="dhtfiles">
<li>${dhtfiles.path}   &nbsp;  ${w:fileSize(dhtfiles.singlefilelength)}
</li>
 </c:forEach>
</ul>
</c:if>

<c:if test="${dhtinfo.singerfile}">
<ul>
<li>${dhtinfo.name}   &nbsp; ${dhtinfo.filelength} 
</li>
</ul>
</c:if>
<footer>   视频 创建时间：   ${w:cndate(dhtinfo.creattime) } 文件大小： ${w:fileSize(dhtinfo.filelength) }   下载热度：${ successcount}  访问热度:${dhtinfo.hitcount}  最近下载：${w:cndate(dhtinfo.lastrequesttime) }   </footer>
</div>
<div class="col-sm-1">
</div>
</div>
<br>
    </div>  


	<script src="${ctx}/static/js/jquery/jquery.js"></script>
	<script src="${ctx}/static/bootstrap/js/bootstrap.min.js"></script>
		 <%@ include file="/WEB-INF/layouts/frontFooter.jsp"%>
  </body>
</html>
