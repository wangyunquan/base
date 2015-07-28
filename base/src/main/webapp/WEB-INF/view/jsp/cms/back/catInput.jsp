<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
 
     			<div class="panel panel-info ">
					<div class="panel-heading">栏目信息</div>
					<div class="panel-body ">
	<form class="form-horizontal" method="post" action="save">
  <c:if test="${entity.id!=null }">
  <input type="hidden" name="id" value="${entity.id }"/>
    <input type="hidden" name="timestamp" value="${entity.timestamp}"/>
  </c:if>
  <input type="hidden" name="site.id" value="${siteId}"/>
  
        <div class="form-group">
          <!-- Text input-->
         <label for="type" class="col-sm-2 control-label">栏目名称</label>
          <div class="col-sm-4">
            <input type="text" name="name"  css="form-control" value="${entity.name}">
          </div>
                        <label for="name" class="col-sm-2 control-label"> 父栏目</label>
                    <div class="col-sm-4">
                 
            <input type="hidden" name="parent.id"  id="parentId"  value="${parent.id}">
            <input type="text"  name="parent.name"  id="parentName"  css="form-control"  value="${parent.name}">
            <input id="parentButton" type="button" value="父栏目"/>
          </div>
        </div>
 
               <div class="form-group">
                       <label for="name" class="col-sm-2 control-label"> 显示名称</label>
          <div class="col-sm-4">
                        <input type="text"  name="displayName"  css="form-control"  value="${entity.displayName}">
          
          </div>
          
          <label for="name" class="col-sm-2 control-label">栏目模板</label>
          <div class="col-sm-4">
                 <input type="text"  name="templates"  css="form-control"  value="${entity.templates}">
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
