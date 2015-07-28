<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
</head>
<body>
 
     			<div class="panel panel-info ">
					<div class="panel-heading">规则信息</div>
					<div class="panel-body ">
						<form class="form-horizontal" method="post" action="save">
						<c:if test="${entity.id!=null}">
						<input type="hidden" name="id" value="${entity.id}">
							<input type="hidden" name="timestamp" value="${entity.timestamp}">
						</c:if>
					 
        <div class="form-group">
          <label for="type" class="col-sm-2 control-label">规则类别</label>
          <div class="col-sm-4">
       <w:codeselect type="ruletype" css="form-control" name="type" value="${entity.type}"></w:codeselect>
          </div>
                  <label for="name" class="col-sm-2 control-label">规则名</label>
          <div class="col-sm-4">
            <input type="text"    name="name"  id="name" class="form-control " value="${entity.name}">
          </div>
        </div>
        
    
              <div class="form-group">
          <label for="name" class="col-sm-2 control-label">规则说明</label>
          <div class="col-sm-4">
            <input type="text"    name="info"  id="info" class="form-control " value="${entity.info}">
          </div>
           <label for="name" class="col-sm-2 control-label">取值来源</label>
          <div class="col-sm-4">
                      <input type="text"    name="valueFrom"  id="valueFrom" class="form-control " value="${entity.valueFrom}">
          </div>
        </div>
        
                   <div class="form-group">
          <label for="name" class="col-sm-2 control-label">表达式</label>
          <div class="col-sm-4">
            <input type="text"    name="expression"  id="expression" class="form-control " value="${entity.expression}">
          </div>
           <label for="name" class="col-sm-2 control-label"> </label>
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
