<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
</head>
<body>
 
     			<div class="panel panel-info ">
					<div class="panel-heading">菜单信息</div>
					<div class="panel-body ">
						<form class="form-horizontal" method="post" action="save">
						<c:if test="${entity.id!=null}">
						<input type="hidden" name="id" value="${entity.id}">
							<input type="hidden" name="timestamp" value="${entity.timestamp}">
						</c:if>
					 
        <div class="form-group">
          <label for="type" class="col-sm-2 control-label">菜单名称</label>
          <div class="col-sm-4">
       <input type="text"    name="name"  id="name" class="form-control " value="${entity.name}">
          </div>
                  <label for="name" class="col-sm-2 control-label">权限名</label>
          <div class="col-sm-4">
            <input type="text"    name="authority"  id="authority" class="form-control " value="${entity.authority}">
          </div>
        </div>
        
              <div class="form-group">
          <label for="name" class="col-sm-2 control-label">url</label>
          <div class="col-sm-4">
            <input type="text"    name="url"  id="url" class="form-control " value="${entity.url}">
          </div>
           <label for="name" class="col-sm-2 control-label"> 顺序</label>
          <div class="col-sm-4">
              <input type="text"    name="orderNo"  id="orderNo" class="form-control " value="${entity.orderNo}">
          </div>
        </div>
        
                <div class="form-group">
          <label for="name" class="col-sm-2 control-label">上级菜单</label>
          <div class="col-sm-4">
          <select name="parent.id"  class="form-control ">
          <option ></option>
     	<c:forEach var="item" items="${allMenu}">
          <option value="${item.id }" <c:if test="${item.id eq parentId}">selected</c:if>>${item.name}</option>
          </c:forEach>
          </select>
          </div>
           <label for="name" class="col-sm-2 control-label"> 层级 </label>
          <div class="col-sm-4">
            <select name="level"  class="form-control ">
            <option value="1" <c:if test="${entity.level==1 }"> selected</c:if>>1</option>
            <option value="2" <c:if test="${entity.level==2 }"> selected</c:if>>2 </option>
             <option value="3" <c:if test="${entity.level==3 }"> selected</c:if>>3 </option>
             <option value="4" <c:if test="${entity.level==4 }"> selected</c:if>>4 </option>
            </select>          
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
