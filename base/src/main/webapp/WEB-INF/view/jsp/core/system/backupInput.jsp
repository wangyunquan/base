<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
 
     			<div class="panel panel-info ">
					<div class="panel-heading">备份信息</div>
					<div class="panel-body ">
						<form class="form-horizontal" method="post" action="save">
						<c:if test="${entity.id!=null}">
						<input type="hidden" name="id" value="${entity.id}">
							<input type="hidden" name="timestamp" value="${entity.timestamp}">
						</c:if>
					 
        <div class="form-group">
          <label for="type" class="col-sm-2 control-label">备份名称</label>
          <div class="col-sm-4">
                   <input type="text"    name="backName"  id="backName" class="form-control " value="${entity.backName}">
       
          </div>
                  <label for="name" class="col-sm-2 control-label"></label>
          <div class="col-sm-4">
          </div>
        </div>
        
 
        
        
     <div class="form-group">
        <div class="col-sm-offset-5 col-sm-7">
            <button type="submit" class="btn btn-info">提交</button>      <button class="btn btn-default" type="button">取消</button>
          </div>
        </div>

  </form>
					</div>
		</div>
 
       
  </body>
</html>
