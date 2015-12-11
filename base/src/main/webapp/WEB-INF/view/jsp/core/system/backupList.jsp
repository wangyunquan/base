<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>

</head>
<body>
			<!--  begin content -->
			<form id="pageForm" action="" method="post">
				<input type="hidden" name="page.page" id="pageNo" value="${page.number}">
				<div class="panel panel-info ">
					<div class="panel-heading">备份信息
					</div>
					<div class="panel-body ">
						<div class="form-inline" role="form">
							<div class="form-group">
                               <label class="search-lable">备份名称</label> <input type="email" class="form-control searchInput" />
							</div>
							<button type="submit" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-search"></span> 查询</button>
							
								<a class="btn btn-sm btn-default" type="button" href="bakupload"  style="float:right; margin-right:5px;"><span class="glyphicon glyphicon-upload"></span>上传</a> 
							<a class="btn btn-sm btn-default" type="button" href="input"  style="float:right;margin-right:5px;"><span class="glyphicon glyphicon-plus"></span>新增</a>
						</div>
					</div>
		</div>
					<div class="table-responsive">
						<table
							class="table table-bordered table-hover table-condensed  narrow-bottom">
							<thead>
								<tr>
									<th><input type="checkbox" class="checkControl"
										group="ids"></th>
									<th>编号</th>
									<th>备份名称</th>
										<th>文件名称</th>
									<th>是否成功</th>
										<th>文件大小</th>
										<th>创建时间</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="item" items="${w:it(page)}" varStatus="status">
									<tr>
										<td><input type="checkbox" name="ids" value="${item.id }"></td>
										<td>${page.number*page.size+status.count}</td>
										<td>${item.backName}</td>
										<td>${item.fileName}</td>
										<td> </td>
										<td>${item.filesize}KB</td>
										<td>${item.createTime}</td>
										<td>
										<a class="btn btn-xs btn-info" type="button" href="download?id=${item.id }"> <span	class="glyphicon glyphicon-download-alt"></span>下载</a> 
										<a class="btn btn-xs btn-default" type="button" 	href="javascript:restoreConfirm('${item.id}');"> <span class="glyphicon glyphicon-arrow-left"></span>恢复</a>
						
										
										<a class="btn btn-xs btn-default" type="button" 	href="javascript:deleteConfirm('delete?id=${item.id}');"> <span class="glyphicon glyphicon-remove"></span>删除</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
			
					 
					<w:page page="${page}"></w:page>
				</div>
			</form>
			
 
	<script type="text/javascript">
	function restoreConfirm( id)
	{
		bootbox.setDefaults({locale:"zh_CN"});
		 bootbox.confirm("是否确认恢复?", function(result) {
			 if(result)
				window.location.href="restore?id="+id;
			 });
	}
	</script>
</body>
</html>
