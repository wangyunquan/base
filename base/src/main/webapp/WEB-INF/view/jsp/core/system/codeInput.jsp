<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
</head>
<body>
 
     			<div class="panel panel-info ">
					<div class="panel-heading">码表信息
					</div>
					<div class="panel-body ">
						<form class="form-horizontal" method="post" action="codesave">
						<c:if test="${entity.id!=null}">
						<input type="hidden" name="id" value="${entity.id}">
							<input type="hidden" name="timestamp" value="${entity.timestamp}">
						</c:if>
						        <div class="form-group">
						        <label  class="col-sm-2 control-label">码表类型</label>
						              <div class="col-sm-10">  
						           <label   class="  control-label"> ${entity.codeType.name }</label>  
						           <input type="hidden" name="codeType.id" value="${entity.codeType.id}"/>
						              </div>
					     </div>
        <div class="form-group">
          <label for="type" class="col-sm-2 control-label">代码</label>
          <div class="col-sm-4">
            <input type="text"    name="code"  id="code" class="form-control " value="${entity.code}">
          </div>
                  <label for="name" class="col-sm-2 control-label">字符值</label>
          <div class="col-sm-4">
            <input type="text"    name="stringValue"  id="stringValue" class="form-control " value="${entity.stringValue}">
          </div>
        </div>
        
              <div class="form-group">
          <label for="name" class="col-sm-2 control-label">中文值</label>
          <div class="col-sm-4">
            <input type="text"    name="cnValue"  id="cnValue" class="form-control " value="${entity.cnValue}">
          </div>
           <label for="name" class="col-sm-2 control-label">是否可用</label>
          <div class="col-sm-4">
<w:coderadio type="yesno" name="enable" value="${entity.enable}"></w:coderadio>
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
