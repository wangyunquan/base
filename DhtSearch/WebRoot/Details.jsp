<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%-- <% String info_hash=${} %> --%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${dhtInfo_MongoDbPojo.torrentInfo.name} - 磁力搜索</title>
<meta name="keywords"
	content="磁力链接, 磁力链, 磁力搜索, 磁力链接搜索, BT搜索">
<meta name="description"
	content="磁力链接, 磁力链, 磁力搜索, 磁力链接搜索, BT搜索">
<meta name="viewport" content="width=device-width">

<link type="text/css" rel="stylesheet" href="../css/default.css">
<script src="../js/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="../js/default.js" type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="../css/embed.bluebox.css">
</head>
<body style="">
	<div id="wrapper">

		<jsp:include page="header.jsp" />
		<div id="content">
			<div id="wall">

				<h2>${dhtInfo_MongoDbPojo.torrentInfo.name}</h2>
				<div class="fileDetail">
					<table class="detail-table detail-width">
						<tbody>
							<tr class="detail-header-bg">
								<th>文件类型</th>
								<th>创建时间</th>
								<th>文件大小</th>
								<th>文件数量</th>
							</tr>
							<tr>
								<td>视频</td>
								<td>${dhtInfo_MongoDbPojo.torrentInfo.formatCreatTime}</td>
								<td>${dhtInfo_MongoDbPojo.torrentInfo.formatSize}</td>
								<td><c:choose>
										<c:when
											test="${dhtInfo_MongoDbPojo.torrentInfo.singerFile==true}">
											<c:out value="1" />
										</c:when>
										<c:otherwise>
									${fn:length(dhtInfo_MongoDbPojo.torrentInfo.multiFiles)}
								</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</tbody>
					</table>

					<div class="detail-panel detail-width">
						<div class="panel-header">磁力链接</div>
						<div class="panel-body">
							<a href="magnet:?xt=urn:btih:${dhtInfo_MongoDbPojo.info_hash}">magnet:?xt=urn:btih:${dhtInfo_MongoDbPojo.info_hash}</a>
						</div>
					</div>

					<div class="detail-panel detail-width">
						<div class="panel-header">文件列表</div>
						<div class="panel-body">
							<ol>
								<c:choose>
									<c:when
										test="${dhtInfo_MongoDbPojo.torrentInfo.singerFile!=true}">
										<c:forEach
											items="${dhtInfo_MongoDbPojo.torrentInfo.multiFiles}"
											var="multiFiles" varStatus="vs">
											<li><c:out value="${multiFiles.path}"></c:out><span
												class="cpill blue-pill"><c:out
														value="${multiFiles.formatSize}" /> </span>
											</li>


										</c:forEach>
									</c:when>
									<c:otherwise>

										<li><c:out
												value="${dhtInfo_MongoDbPojo.torrentInfo.name}"></c:out><span
											class="cpill blue-pill"><c:out
													value="${dhtInfo_MongoDbPojo.torrentInfo.formatSize}" /> </span>
										</li>

									</c:otherwise>
								</c:choose>

							</ol>
						</div>
					</div>
					<div class="detail-panel detail-width">
						<div class="panel-header">友情提示</div>
						<div class="panel-body">
							<p>投诉邮箱：ts@konka.net</p>
							<p>
								不想下载？试试<a href="http://pan.baidu.com" target="_blank">百度云播放</a>！
							</p>
							<p>亲，你造吗？将网页分享给您的基友，下载的人越多速度越快哦！</p>
						</div>
					</div>
					<div class="detail-panel detail-width"></div>
				</div>

				<div class="push"></div>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp" />

</body>
</html>
