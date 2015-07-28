
Cms = {};
Cms.cat = function(url,name, options) {
	var idHidden = $("#" + name+ "Id");
	var nameText = $("#" + name + "Name");
	var clickButton = $("#" + name + "Button");
	var dialogDiv = null;
	var destroy = function() {
		dialogDiv.dialog("destroy");
		dialogDiv.empty();
	};
	var applayValue = function() {
		var id = $("#treeSelectedId").val();
		idHidden.val(id);
		var nameVal = $("#treeSelectedName").val();
		nameText.val(nameVal);
	 
	};
	var settings = {
		title : "Select Node",
		width : 300,
		height : 400,
		modal : true,
		position : {
			my : "center center",
			at : "center center",
			of : window
		},
		buttons : [ {
			id : "catDialogOk",
			text : "OK",
			click : function() {
				applayValue();
				destroy();
				if(options && options.ok && typeof options.ok=="function") {
					options.ok(idHidden.val(),nameText.val());
				}
			}
		}, {
			text : "Cancel",
			click : function() {
				destroy();
				if(options && options.cancel && typeof options.cancel=="function") {
					options.cancel();
				}
			}
		} ]
	};
	$.extend(settings, options);

	clickButton.click(function() {
		var params = {"d":new Date()*1};
		if(idHidden.val()!="") {
			params.id = idHidden.val();
		}
		params = $.extend(settings.params,params);
		if (!dialogDiv) {
			dialogDiv = $("<div style='display:none;'>").appendTo(document.body);
		}
		dialogDiv.load(url+"&"+ $.param(params), function() {
			dialogDiv.dialog(settings);
		});
	});
};
