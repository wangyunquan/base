<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
</head>
<body>
 
<div class="tabbable">
 <ul class="nav nav-tabs">
 
    <li class="active"><a href="#tab1" data-toggle="tab">码表信息</a></li>
    <li><a href="#tab2" data-toggle="tab">码表值</a></li>
  </ul>
  
 <div class="tab-content">
    <div class="tab-pane active" id="tab1">
<form class="form-horizontal" method="post" action="save">
		<c:if test="${entity.id!=null}">
						<input type="hidden" name="id" value="${entity.id}">
							<input type="hidden" name="timestamp" value="${entity.timestamp}">
						</c:if>
 <p class="form-control-static"></p>
    <div class="form-group">
          <label for="type" class="col-lg-2 control-label">码表类型</label>
          <div class="col-lg-5">
            <input type="text"    name="type"  id="type" class="form-control " value="${entity.type}">
          </div>
        </div>
        
            <div class="form-group">
          <label for="name" class="col-lg-2 control-label">码表名称</label>
          <div class="col-lg-5">
            <input type="text"    name="name"  id="name" class="form-control " value="${entity.name}">
          </div>
        </div>
        
           <div class="form-group">
          <label for="name" class="col-lg-2 control-label">可编辑</label>
          <div class="col-lg-5">
<w:coderadio type="yesno" name="editable" value="${entity.editable}"></w:coderadio>
          </div>
        </div>
         <div class="form-group">
          <label for="valueType" class="col-lg-2 control-label">码表值类型</label>
          <div class="col-lg-3">
          <w:codeselect type="codetype"  value="${entity.valueType}"  name="valueType"></w:codeselect>
          </div>
        </div>
     <div class="form-group">
       <label for="type" class="col-lg-2 control-label"></label>
        <div class="col-lg-5">
            <button type="submit" class="btn btn-info">提交</button>      <button class="btn btn-default">取消</button>
          </div>
        </div>

  </form>
</div>

<div class="tab-pane" id="tab2">
<br/>
<a class="btn btn-sm btn-default" type="button" href="codeinput?codeTypeId=${entity.id}"  style="float:right"><span class="glyphicon glyphicon-plus"></span>新增</a>
     <div class="table-responsive">
                <table class="table table-bordered table-hover table-condensed  narrow-bottom" >
                  <thead>
                    <tr>
                        <th><input type="checkbox"  class="checkControl" group="ids"></th>
				<th>代码</th>
				<th>字符值</th>
				<th>是否可用</th>
                      <th>操作</th>
                    </tr>
                  </thead>
                  <tbody>
                  	<c:forEach var="item" items="${listCode}" varStatus="status">
                    <tr>
                      <td><input type="checkbox"  name="ids" value="${item.id }"></td>
              	<td>${item.code}</td>
		<td>${item.stringValue}</td>
		  <td><w:code type="yesno" key="${item.enable}"/></td>
                      <td> 
                      <a class="btn btn-xs btn-info"  type="button" href="codeinput?id=${item.id }&codeTypeId=${entity.id}"> <span  class="glyphicon glyphicon-new-window"></span>修改
                      </a>
                      <a class="btn btn-xs btn-default"  type="button"  href="javascript:deleteConfirm('codedelete?id=${item.id }');">
                      <span  class="glyphicon glyphicon-remove"></span>删除
                      </a>
                       </td>
                    </tr>
          </c:forEach>
                  </tbody>
                </table>
</div>

    </div>
</div>
 
     </div>
   
        
 
       
  </body>
</html>
