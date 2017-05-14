$(function(){
	
	$('#addContractTpl').width(bodyWidth-170);
	$('#addContractTpl').height(bodyHeight-185);
	
	if (conId && conId!='') {
		$("input[name='contractNo']").attr("disabled", "disabled");
		$.ajax({
			url: path + '/contract/getById.html?id=' + conId,
			type: "post",
			dataType: "json",
			success: function(data){
				ue.ready(function(){
					ue.setContent(data.templateContent);    
				});
				$('#contractTemplateForm').form('load', data);
			},
			error: function(){
				
			}
		});
	}
	
	$('#saveBtn').click(function() {
		$("#contractTemplateForm").submit();
	});
	
	
	$("#contractTemplateForm").validate({
		rules : {
			contractNo : {
				required: true,
                maxlength:64
			},
			contractName : {
				required: true,
				maxlength:64
			}
		},
		messages : {
			contractNo : {
				required: "请填写合同编号",
                maxlength:"合同编号不能超过64个字符"
			},
			contractName : {
				required: "请填写合同名称",
				maxlength:"合同名称不能超过64个字符"
			}
		},
        submitHandler:function(form){
        	var url=path + '/contract/save.html';
    		if (conId && conId!='') {
    			url=path + '/contract/modify.html';
    		}
    		$('#contractTemplateForm').form('submit', {
    			url : url,
    			queryParams: {
    				"templateContent": ue.getContent(),
    				"id":conId
    			},
    			success : function(data) {
    				var obj = $.parseJSON(data);
    				if (obj.success) {
    					$.messager.alert('提示','保存成功！<a href="'+path+'/contract.contractTemplate.html">返回列表</a>');   
    				}
    				if (!obj.success) {
    					if (obj.msg) {
    						$.messager.alert('提示', obj.msg);
    					}
    				}
    			}
    		});
        }
    }); 
	
	
});