<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
 
			<!--  begin content -->
			<form id="pageForm" action="listRules" method="post">
				<div class="panel panel-info ">
					<div class="panel-heading">规则信息
					</div>
					<div class="panel-body ">
						<div class="form-inline" role="form">
							<div class="form-group">
                               <label class="search-lable">规则名称</label> <input type="email" class="form-control searchInput" />
							</div>
							<button type="submit" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-search"></span> 查询</button>
							
							<a class="btn btn-sm btn-default" type="button" href="inputRules"  style="float:right"><span class="glyphicon glyphicon-plus"></span>新增</a>
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
									<th>名称</th>
									<th>规则说明</th>
									<th>规则类型</th>
									<th>值来源</th>
									<th>表达式</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="item" items="${w:it(page)}" varStatus="status">
									<tr>
										<td><input type="checkbox" name="ids" value="${item.id }"></td>
										<td>${page.number*page.size+status.count}</td>
										<td>${item.name}</td>
										<td>${item.info}</td>
										<td><w:code key="${item.type}" type="authoritytype"></w:code></td>
										<td>${item.valueFrom}</td>
										<td>${item.expression}</td>
										<td>
										<a class="btn btn-xs btn-info" type="button" href="iinputRules?id=${item.id }"> <span	class="glyphicon glyphicon-new-window"></span>修改</a> 
										<a class="btn btn-xs btn-default" type="button" 	href="javascript:deleteConfirm('deleteRules?id=${item.id}');"> <span class="glyphicon glyphicon-remove"></span>删除</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
				</div>
			</form>
 
</body>
</html>
