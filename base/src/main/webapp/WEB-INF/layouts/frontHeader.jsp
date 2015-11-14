<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
	<nav class="navbar navbar-default navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#"><font color="blue">B</font>us<font color="red">W</font>e.com</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="${ctx}/index.htm">磁力搜索</a></li>
					<li><a href="${ctx}/blog/">博客</a></li>
					<li><a href="#contact">技术wiki</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="#">关于我<span class="sr-only">(current)</span></a></li>
				</ul>
			</div>
		</div>
	</nav>