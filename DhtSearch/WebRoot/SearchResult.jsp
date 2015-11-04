<%@ page language="java" import="java.util.*,java.net.URLDecoder"
	contentType="text/html; charset=utf-8"%>
<%@ page language="java"
	import="com.konka.dhtsearch.db.luncene.LuceneSearchResult"%>
<%@ page language="java"
	import="com.konka.dhtsearch.db.models.DhtInfo_MongoDbPojo"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	String searchkeywords = URLDecoder.decode(request.getParameter("searchkeywords"), "utf-8");
	// 			request.getc
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=searchkeywords%> 磁力链接 - 磁力搜索</title>
<meta name="keywords" content="磁力链接, 磁力链, 磁力搜索, 磁力链接搜索, BT搜索, 纸牌屋">
<meta name="description" content="<%=searchkeywords%>的磁力链接">
<meta name="viewport" content="width=device-width">

<link type="text/css" rel="stylesheet" href="../css/default.css">

<script src="../js/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="../js/jqPaginator.js" type="text/javascript"></script>

</head>
<body>
	<div id="wrapper">
		<jsp:include page="header.jsp" />
		<div id="content">
			<div id="wall">
				<div class="search-statu">
					<span>大约 ${searchResultInfo.total} 条结果。</span>
				</div>
				<c:forEach items="${searchResultInfo.fileInfos}" var="fileInfo"
					varStatus="vs">

					<div class="search-item">
						<div class="item-title">
							<a
								href="DetailsServlet?info_hash=<c:out value="${fileInfo.info_hash}"/>"
								target="_blank"> <c:out value="${fileInfo.name}"
									escapeXml="false" /> </a>
						</div>
						<div class="item-list">

							<c:forEach items="${fileInfo.subfileInfos}" var="subfileinfo"
								varStatus="vs" end="2">
								<p>
									<c:out value="${subfileinfo.name}" />
									<c:out value="${subfileinfo.subFileSize}" />
								</p>
							</c:forEach>

							<c:if test="${fileInfo.subFileCount>3}">
								<p>......</p>
							</c:if>

						</div>
						<div class="item-bar">
							<span class="cpill fileType1">${fileInfo.fileType}</span> <span>创建时间：
								<b> <c:out value="${fileInfo.createTime}" /> </b> </span><span>文件大小：
								<b class="cpill yellow-pill"> <!-- 								formatSize --> <c:out
										value="${fileInfo.fileSize}" /> </b> </span><span>文件数：${fileInfo.subFileCount}
								<b> </b> </span> <a
								href="magnet:?xt=urn:btih:<c:out value="${fileInfo.info_hash}"/>">磁力链接</a>
						</div>
					</div>
				</c:forEach>

				<div class="bottom-pager">
					<c:forEach items="${pageInfo.pageList}" var="pages" varStatus="vs">
						<c:choose>
							<c:when test="${pages.pageNumber eq pageInfo.currentPage}">
								<a><font color="red">${pages.dispalyName}</font> </a>
							</c:when>
							<c:otherwise>
								<a href="${pages.uri}">${pages.dispalyName}</a>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>

	<p id="p1"></p>
	<ul class="pagination" id="pagination1"></ul>
	<p id="p2"></p>
	<ul class="pagination" id="pagination2"></ul>

	<div class="push"></div>
	<jsp:include page="footer.jsp" />
</body>
</html>
