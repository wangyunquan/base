<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
 
     			<div class="panel panel-info ">
					<div class="panel-heading">用户信息</div>
					<div class="panel-body ">
						<form class="form-horizontal" method="post" action="save">
						<c:if test="${entity.id!=null}">
						<input type="hidden" name="id" value="${entity.id}">
							<input type="hidden" name="timestamp" value="${entity.timestamp}">
						</c:if>
					 
        <div class="form-group">
    
                  <label for="loginName" class="col-sm-2 control-label">用户名</label>
          <div class="col-sm-4">
            <input type="text"    name="loginName"  id="loginName" class="form-control " value="${entity.loginName}">
          </div>
          
                    <label for="password" class="col-sm-2 control-label">密码</label>
          <div class="col-sm-4">
            <input type="text"    name="password"  id="password" class="form-control " value="${entity.password}">
          </div>
        </div>
        
                <div class="form-group">
    
                  <label for="name" class="col-sm-2 control-label">姓名</label>
          <div class="col-sm-4">
            <input type="text"    name="name"  id="name" class="form-control " value="${entity.name}">
          </div>
          
                    <label for="email" class="col-sm-2 control-label">密码</label>
          <div class="col-sm-4">
            <input type="text"    name="email"  id="email" class="form-control " value="${entity.email}">
          </div>
        </div>
        
                <div class="form-group">
    
                  <label for="enabled" class="col-sm-2 control-label">是否可用</label>
          <div class="col-sm-4">
         <w:coderadio type="yesno" name="enabled" value="${entity.enabled}"></w:coderadio>
          </div>
          
                    <label for="locked" class="col-sm-2 control-label">是否锁定</label>
          <div class="col-sm-4">
    <w:coderadio type="yesno" name="accountNonLocked" value="${entity.accountNonLocked}"></w:coderadio>
          </div>
        </div>
 
         <div class="form-group">
         <label for="rol" class="col-sm-2 control-label">角色</label>
        <div class="col-sm-offset-5 col-sm-5">
        <c:forEach items="${allRoles}" var="roles">${roles.rolesinfo}  <input type="checkbox" name="rolesIds" value="${roles.id}" 
               <c:forEach items="${entity.rolesinfos}" var="entityrole"><c:if test="${roles.id==entityrole.id}"> checked</c:if></c:forEach>></c:forEach>
 
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
