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
					<div class="panel-heading">文章信息
					</div>
					<div class="panel-body ">
						<div class="form-inline" role="form">
							<div class="form-group">
                               <label class="search-lable">标题名称</label> <input type="email" class="form-control searchInput" />
							</div>
							<button type="submit" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-search"></span> 查询</button>
							
							<a class="btn btn-sm btn-default" type="button" href="input"  style="float:right"><span class="glyphicon glyphicon-plus"></span>新增</a>
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
                      <th>标题 </th>
                      <th>状态</th>
                      <th>更新时间</th>
                       <th>点击数</th>
                      <th> </th>
                    </tr>
                  </thead>
                  <tbody>
                  	<c:forEach var="item"  items="${w:it(page)}"   varStatus="status">
                    <tr>
         	<td><input type="checkbox" name="ids" value="${item.id }"></td>
										<td>${page.number*page.size+status.count}</td>
                      <td>${item.title}</td>
                      <td>${item.status}</td>
                      <td>${item.updateDate}</td>
                      <td>${item.hits}</td>
                 	<td>
										<a class="btn btn-xs btn-info" type="button" href="input?id=${item.id }"> <span	class="glyphicon glyphicon-new-window"></span>修改</a> 
										<a class="btn btn-xs btn-default" type="button" 	href="javascript:deleteConfirm('delete?id=${item.id}');"> <span class="glyphicon glyphicon-remove"></span>删除</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
			
					 
					<w:page page="${page}"></w:page>
				</div>
			</form>
			

</body>
</html>
