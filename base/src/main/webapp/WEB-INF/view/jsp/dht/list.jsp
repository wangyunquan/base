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

    <div class="container"   >
    
    
<div class="row"  >
<form class="form-horizontal" action="./search.htm"  id="searchform">
   <div class="form-group">
        <div class="col-sm-3">
            </div>
    <div class="col-sm-5">
   <input type="text" class="form-control"  id="q"  name="q" value="${q}"  placeholder="输入关键字">
      <input type="hidden"    id="pageNumber"  name="p" value="" >
    </div>
     <div class="col-sm-1">
	<button type="submit"  class="btn btn-primary"><span class="glyphicon glyphicon-search"></span> 查询</button>
    </div>
    <div class="col-sm-3">
      </div>
  </div>
  </form>
      </div>

<div class="row"  >
<div class="col-sm-9 col-md-offset-2">
<p><b>共计找到结果 ${page.totalElements}条.耗时:${totalTime}秒</b></p>
</div>
<div class="col-sm-1">
</div>
</div>
<c:forEach items="${page.content}" var="dhtinfo">
<div class="row"  >
<div class="col-sm-10 col-md-offset-1">
<header ><a href="./get/${dhtinfo.infohash}.htm" target="_blank">${dhtinfo.name}</a></header>
<c:if test="${!dhtinfo.singerfile}">
<ul>
<c:set var="flag" value="true" />
<c:forEach items="${dhtinfo.dhtfiles}"  var="dhtfiles"  varStatus = "status">
 <c:if test="${flag}"><!-- 这部分可以放到库操作，只显示若干条就可以了 -->
<li>       ${dhtfiles.path}   &nbsp;   ${w:fileSize(dhtfiles.singlefilelength) }
</li>
</c:if>
     <c:if test="${status.count==5}">
              <c:set var="flag" value="false"/>
       </c:if>
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
 </c:forEach>
<div class="row"  >
<div class="col-sm-10col-md-offset-2">
	<w:page page="${page}"  jumpFunction="pageto"></w:page>
</div>
    </div>  
    
        </div>
        
	<script src="${ctx}/static/js/jquery/jquery.js"></script>
	<script src="${ctx}/static/bootstrap/js/bootstrap.min.js"></script>
	 <%@ include file="/WEB-INF/layouts/frontFooter.jsp"%>
  </body>
</html>
