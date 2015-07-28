<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglibs.jsp"%>
   <script src="${ctx}/static/js/jquery/jquery.js"></script>
  <script src="${ctx}/static/js/ztree/jquery.ztree.js"></script>
 <link href="${ctx}/static/js/ztree/ztree.css" rel="stylesheet">
  	<script src="${ctx}/static/js/ztree/jquery.ztree.js"></script>
<style type="text/css">
.ztree li span.button.switch.level0 {visibility:hidden; width:1px;}
.ztree li ul.level0 {padding:0; background:none;}
</style>
<script type="text/javascript">
$(function() {
	var setting = {
		view: {
			expandSpeed: "",
			dblClickExpand: function(treeId, treeNode) {
				return false;				
			}
		},
		callback: {
			onClick: function(event, treeId, treeNode) {
				$("#treeSelectedId").val(treeNode.id);
				$("#treeSelectedName").val(treeNode.name);
			},
			onDblClick: function(event, treeId, treeNode) {
				$("#treeSelectedId").val(treeNode.id);
				$("#treeSelectedName").val(treeNode.name);
				$("#catDialogOk").click();
			}
		},
		data: {
			simpleData: {
				enable: true
			}
		}
	};
	var zNodes =[
  	<c:forEach var="node" items="${catList}" varStatus="status">
	{"id":"${node.id}","pId":"<c:out value="${node.parent.id}" default="0"/>","name":"${node.displayName}","open":true,"position":${status.index}}<c:if test="${!status.last}">,</c:if>
  	</c:forEach>
  ];
	 
	var ztree = $.fn.zTree.init($("#displayCatTree"), setting, zNodes);
	 
//	var selectedNode = ztree.getNodesByParam("id",${id});
	//ztree.selectNode(selectedNode[0]);
});
</script>
<input type="hidden" id="treeSelectedId" value=""/>
<input type="hidden" id="treeSelectedName" value=""/>
<ul id="displayCatTree" class="ztree" style="padding-top:5px"></ul>