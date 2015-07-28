<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
 
     			<div class="panel panel-info ">
					<div class="panel-heading">角色信息</div>
					<div class="panel-body ">
						<form class="form-horizontal" method="post" action="save">
						<c:if test="${entity.id!=null}">
						<input type="hidden" name="id" value="${entity.id}">
							<input type="hidden" name="timestamp" value="${entity.timestamp}">
						</c:if>
					 
        <div class="form-group">
          <label for="type" class="col-sm-2 control-label">角色名</label>
          <div class="col-sm-4">
      <input type="text"    name="rolename"  id="rolename" class="form-control " value="${entity.rolename}">
          </div>
                  <label for="name" class="col-sm-2 control-label">角色描述</label>
          <div class="col-sm-4">
            <input type="text"    name="rolesinfo"  id="rolesinfo" class="form-control " value="${entity.rolesinfo}">
          </div>
        </div>
        
        <c:forEach items="${authMap}" var="entry"> 
        <div class="panel ">
         <div class="panel-heading">${entry.key} <input type="checkbox"  name="selectAll"  value="${entry.key}"  onclick="selecAll(this)"/>全选</div>
         <div class="panel-body ">
         <c:forEach items="${entry.value}"  var="item">
         <div class="col-sm-3 form-inline"> 
  <div class="checkbox">
    <label>
    ${item.authinfo} <input type="checkbox"  name="authId"  authType="${entry.key}"  auth="${item.name}"  value="${item.id}">&nbsp;
    <select name="${item.id}"> <option value="">全部</option>
    <c:forEach items="${allRules}" var="rules">
    <option value="${rules.id}">${rules.info}</option>
    </c:forEach>
    </select>
    </label>
  </div>
 </div>
 </c:forEach>
 
      </div>
      </div>
      </c:forEach>
      
     <div class="form-group">
        <div class="col-sm-offset-5 col-sm-7">
            <button type="submit" class="btn btn-info">提交</button>      <button class="btn btn-default" type="button">取消</button>
          </div>
        </div>

  </form>
					</div>
		</div>
      <script type="text/javascript">
      function selecAll(checkbox)
      {
    	  var check=$(checkbox);
    	  $("[authType='"+check.val()+"']").each(function(){
    		  $(this).attr("checked",check.is(":checked"));
    		  
    	  });
      }
      $(function() {
    	   <c:forEach items="${roleAuth}" var="roleAuthMap"> 
    	   var authId='${roleAuthMap.key}';
    	   $(":checkbox[auth='"+authId+"']").attr("checked",true);
    	   
    	   </c:forEach >
      });
      </script>
       
  </body>
</html>
