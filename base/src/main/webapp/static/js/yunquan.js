function pageJump(pageNo) {
	$("#pageNo").val(pageNo);
	$("#pageForm").submit();
}

function deleteConfirm(url)
{
	bootbox.setDefaults({locale:"zh_CN"});
	 bootbox.confirm("是否确认删除?", function(result) {
		 if(result)
			window.location.href=url;
		 });
	}
$(function() {
	/**
	 * 单选全选,设置checkbox的class为checkControl，属性group为需要控制的checkbox的name
	 */
	var checkControl = $(".checkControl");
	checkControl.click(function() {
		var groupName = $(this).attr("group");
		var groupList = $(":checkbox[name='" + groupName + "']");
		groupList.each(function() {
			var $checkbox = $(this);
			$checkbox.attr('checked', checkControl.is(":checked"));
		});
	});

	/**
	 * 表格操作
	 */
	var $tableToolAdd=$("#table-tool-add");
	if($tableToolAdd)
		{
		$tableToolAdd.click(function() {
			var $linkhref = $tableToolAdd.attr("href");
			window.location=$linkhref;
		});
		}

	
	var $tableTooles = $(".table-tool");
	$tableTooles.each(function() {
		$(this).click(
				function() {
					var $tools = $(this);
					var $group = $tools.attr("group");
					var $linkhref = $tools.attr("href");
					var $single=$tools.attr("single");
					if ($group) {
						var ids="";
						$("input:checked").filter("[name='" + $group + "']")
								.each(function(i) {
									var val = $(this).val();
									ids += i == 0 ? val : "," + val;
								});
						if (ids!="") {
							window.location=$linkhref+ids;
						} else {
                       alert("最少选择一个");
						}
					} else {
						 bootbox.confirm("Are you sure?", function(result) {
							alert(result); 
						 });
						//window.location=$linkhref;
					}
				});
	});
	// <button class="btn btn-primary btn-sm table-tool" group="ids"
	// href="/add">

});
