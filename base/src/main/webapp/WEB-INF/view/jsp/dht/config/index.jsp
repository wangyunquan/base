<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
 
			<!--  begin content -->
			<form id="pageForm" action="" method="post">
				<input type="hidden" name="page.page" id="pageNo" value="${page.number}">
				
				<div class="panel panel-info ">
					<div class="panel-heading">DHT爬虫设置
					
					</div>
					<div class="panel-body ">
				 <button type="button" class="btn btn-info" id="startService">启动服务</button>
				 
				 <button type="button" class="btn btn-warning" id="stopservice">停止</button>
				 
				 <button type="button" class="btn btn-success"  id="creatIndex"> 创建索引</button>
					</div>
		</div>
		 
			</form>
			
			
<script type="text/javascript">
$(document).ready(function(){
	  $("#startService").click(function(){
	  $.ajax({
		  url:"${ctx}/dht/admin/system/start",
		  async:false,
		  success:function(data){  
		 alert(data);
		  }
		  });
	  });
	  
	  $("#stopservice").click(function(){
		  $.ajax({
			  url:"${ctx}/dht/admin/system/stop",
			  async:false,
			  success:function(data){  
			 alert(data);
			  }
			  });
		  });
	  
	  $("#creatIndex").click(function(){
		  $.ajax({
			  url:"${ctx}/dht/admin/system/creatIndex",
			  async:false,
			  success:function(data){  
			 alert(data);
			  }
			  });
		  });
	  
	  
	});

</script>
 
</body>

</html>
