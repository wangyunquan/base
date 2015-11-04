<%@ page language="java" import="java.util.* ,java.net.URLDecoder"
	contentType="text/html; charset=UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String searchkeywords = request.getParameter("searchkeywords");
%>

<div class="header-div">
	<form class="search-form" method="get">
		<a href=<%=basePath%> title="Btbook home"> <img
			src="../assets/logo_40.png" alt="Btbook" class="nav-logo"> </a> <input
			type="text" id="search" title="Search"
			value="<%=URLDecoder.decode(searchkeywords == null
					? ""
					: searchkeywords, "utf-8")%>"
			size="25" style="height: 31" autocomplete="off" name="q"> <input
			type="submit" id="btnSearch" value="搜 索" class="blue">
	</form>
</div>
<script type="text/javascript">
	document.getElementById("search").focus();
	document.forms[0].onsubmit = function(e) {
		e.preventDefault();
		var query = document.getElementById("search").value;
		if (!query) {
			document.getElementById("search").focus();
			return false;
		}
		var url = '/DhtSearch/servlet/SearchServlet?searchkeywords=' + query;
		window.location = url;
		return false;
	};
</script>

