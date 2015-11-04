<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>konka - 磁力搜索</title>
<meta name="keywords" content="磁力链接, 磁力链, 磁力搜索, 磁力链接搜索, BT搜索">
<meta name="description"
	content="磁力链接搜索引擎konka.com索引了全球最新最热门的BT种子信息和磁力链接，提供磁力链接搜索、BT搜索、种子搜索等强大功能。">

<link type="text/css" rel="stylesheet" href="./css/default.css">
<link rel="shortcut icon" type="image/x-icon"
	href="http://www.konka.cn/static/img/favicon.ico">
<link rel="apple-touch-icon"
	href="http://www.konka.cn/static/img/konka_apple.jpg">
<script src="./js/jquery-1.3.2.min.js" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

</head>

<body>
	<div id="wrapper">

		<div id="first-content">
			<div id="center-box-wrapper">
				<div id="center-box">
					<div id="center-logo">
						<img src="./assets/logo.png" alt="konka" class="logo">
					</div>
					<form id="search-form" action="./servlet/SearchServlet"
						method="get">
						<div id="search-box">
							<input type="text" id="search" name="searchkeywords" size="25"
								style="height: 30"> <input type="submit" id="btnSearch"
								size="25" style="height: 30" value="搜 索" class="blue">

						</div>
					</form>
					<script type="text/javascript">
						document.getElementById("search").focus();
						document.forms[0].onsubmit = function(e) {
							e.preventDefault();
							var query = document.getElementById("search").value;
							if (!query) {
								document.getElementById("search").focus();
								return false;
							}
							var url = '/DhtSearch/servlet/SearchServlet?searchkeywords='
									+ query;
							window.location = url;
							return false;
						};
					</script>
				</div>
			</div>
		</div>
		<div class="push"></div>
	</div>

	<jsp:include page="footer.jsp" />
</body>
</html>
