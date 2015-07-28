<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript" src="${ctx}/static/js/ckeditor/ckeditor.js"></script>
     <script type="text/javascript" src="${ctx}/static/js/cms.js"></script>
</head>
<body>
 
     			<div class="panel panel-info ">
					<div class="panel-heading">文章信息</div>
					<div class="panel-body ">
						<form class="form-horizontal" method="post"  enctype="multipart/form-data"  action="save">
						<c:if test="${entity.id!=null}">
						<input type="hidden" name="id" value="${entity.id}">
							<input type="hidden" name="timestamp" value="${entity.timestamp}">
						</c:if>
					 
					   <input type="hidden" name="site.id" value="${siteId}"/>
					   
					   
					         <div class="form-group">
          <label for="type" class="col-sm-2 control-label">标题</label>
          <div class="col-sm-10">
                   <input type="text"    name="title"   class="form-control " value="${entity.title}">
          </div>
          </div>
        			         <div class="form-group">
          <label for="type" class="col-sm-2 control-label">标签</label>
          <div class="col-sm-10">
                   <input type="text"    name="tags"   class="form-control " value="${entity. tags}">
          </div>
        </div>
      		         <div class="form-group">
          <label for="type" class="col-sm-2 control-label">关键字</label>
          <div class="col-sm-10">
                   <input type="text"    name="keywords"   class="form-control " value="${entity. keywords}">
          </div>
      
        </div>
        		         <div class="form-group">
        		                   <label for="name" class="col-sm-2 control-label">栏目</label>
          <div class="col-sm-4">
             <form:select path="entity.category.id"  items="${catList}"  itemValue="id"  itemLabel="displayName"  cssClass="form-control">
             </form:select>
          </div>
          
					    </div>
            <div class="form-group">
             <div class="col-sm-12">
             <textarea rows="10" cols="200"  id="ckEditor"  name="articleData.lobContent">
             ${entity.articleData.lobContent}
             </textarea>
             </div>
             	<c:if test="${entity.articleData.id!=null}">
						<input type="hidden" name="articleData.id" value="${entity.articleData.id}">
						</c:if>
             </div>
    
 <p />
<div class="form-group">
        <div class="col-sm-offset-5 col-sm-7">
            <button type="submit" class="btn btn-info">提交</button>      <button class="btn btn-default" type="button">取消</button>
          </div>
        </div>

  </form>
					</div>
		</div>
      
                         <script type="text/javascript">
        CKEDITOR.replace("ckEditor",{
          toolbar:'Cms',
          filebrowserUploadUrl : '${ctx}/cms/back/upload/file',
          filebrowserImageUploadUrl : '${ctx}/cms/back/upload/file',
          filebrowserFlashUploadUrl :  '${ctx}/cms/back/upload/file'
    });
        var stack_bottomright = {"dir1": "up", "dir2": "left", "firstpos1": 25, "firstpos2": 25};
        $(function() {
        new PNotify({
            title: 'Regular Success',
            text: 'That thing that you were trying to do worked!',
            addclass: "stack-bottomright",
            stack: stack_bottomright,
            type: 'success'
        });
        });
 
    </script>
  </body>


</html>

  
  

  