<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
  <head>
  </head>
  <body>
  
     <div class="container-fluid">
      <div class="row-fluid">
        <div class="span2">
          <div class="well sidebar-nav">
            <ul class="nav nav-list">
              <li class="nav-header">Sidebar</li>
              <li class="active"><a href="#">Link</a></li>
              <li><a href="../appcode/list">码表管理</a></li>
              <li><a href="#">Link</a></li>
              <li><a href="#">Link</a></li>
              <li class="nav-header">Sidebar</li>
              <li><a href="#">Link</a></li>
              <li><a href="#">Link</a></li>
              <li><a href="#">Link</a></li>
              <li><a href="#">Link</a></li>
              <li><a href="#">Link</a></li>
              <li><a href="#">Link</a></li>
              <li class="nav-header">Sidebar</li>
              <li><a href="#">Link</a></li>
              <li><a href="#">Link</a></li>
              <li><a href="#">Link</a></li>
            </ul>
          </div><!--/.well -->
        </div><!--/span-->
        <div class="span10">
  <form class="form-horizontal" method="post"  action="save">
  <c:if test="${entity.id!=null }">
  <input type="hidden" name="id" value="${entity.id }"/>
  </c:if>
    <fieldset>
  
    <div class="control-group">
          <!-- Text input-->
          <label class="control-label" for="input01">站点名称</label>
          <div class="controls">
            <input type="text" placeholder="placeholder" name="name" class="input-xlarge" value="${entity.name}">
          </div>
        </div><div class="control-group">

          <label class="control-label" for="input01">站点标题</label>
          <div class="controls">
            <input type="text" placeholder="placeholder" name="title" class="input-xlarge" value="${entity.title}">
            <p class="help-block">站点标题</p>
          </div>
        </div>

    

    <div class="control-group">

          <!-- Text input-->
          <label class="control-label" for="input01">描述</label>
          <div class="controls">
            <input type="text" placeholder="placeholder" name="desciption" class="input-xlarge" value="${entity.desciption}">
            <p class="help-block">描述，有助于搜索引擎优化</p>
          </div>
        </div>

    

    

    <div class="control-group">

          <!-- Text input-->
          <label class="control-label" for="input01">关键字</label>
          <div class="controls">
            <input type="text" placeholder="placeholder" name="keywords" class="input-xlarge" value="${entity.keywords}">
          </div>
        </div>

    <div class="control-group">

          <!-- Text input-->
          <label class="control-label" for="input01">主题</label>
          <div class="controls">
            <input type="text" placeholder="placeholder"  name="theme" class="input-xlarge" value="${entity.theme}">
          </div>
        </div>
 <div class="control-group">
   <label class="control-label" for="input01"></label>
   <div class="controls">
    <button class="btn" type="submit">提交</button>
    </div>
</div>
    </fieldset>
  </form>
  
  
  </div>
  </div>
  </div>
  
</body>
</html>