<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>

     			<div class="panel panel-info ">
					<div class="panel-heading">静态化</div>
					<div class="panel-body ">
	<form class="form-horizontal" method="post" action="generate">
  <input type="hidden" name="siteId" value="${siteId }"/>
  
        


               <div class="form-group">
                       <label for="name" class="col-sm-2 control-label"> 静态化</label>
          <div class="col-sm-4">
                  <select name="type"> <option value="cat">所有栏目</option></select>
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