<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>没有找到 - 磁力搜索</title>
<meta name="keywords" content="磁力链接, 磁力链, 磁力搜索, 磁力链接搜索, BT搜索">
<meta name="description"
	content="磁力链接搜索引擎btbook.net索引了全球最新最热门的BT种子信息和磁力链接，提供磁力链接搜索、BT搜索、种子搜索等强大功能。">
<meta name="viewport" content="width=device-width">

<link type="text/css" rel="stylesheet" href="../css/default.css">
<script src="../js/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="../js/default.js" type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="../css/embed.bluebox.css">
<body>
	<div id="wrapper">
		<jsp:include page="header.jsp" />

		<div id="content">
			<div id="wall">

				<div style="position: relative">
					<img src="../assets/404.jpg">
					<div style="position: absolute;left:268px;top:90px;">
						<p class="error">
							Error 404<span>抱歉，您要访问的页面不存在</span>
						</p>
					</div>
				</div>
				<style type="text/css">
p.error {
	color: #ff7300 !important;
	font-size: 30px;
	font-family: "\5FAE\8F6F\96C5\9ED1";
}

p.error span {
	font-size: 18px;
	color: #444;
}
</style>

				<div class="push"></div>
			</div>
		</div>
	</div>

	<jsp:include page="footer.jsp" />

</body>
</html>