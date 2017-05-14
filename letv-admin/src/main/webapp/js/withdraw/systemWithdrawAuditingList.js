$(function(){
	//获取字典数据
	var columns = [[
	                {field:'operat',title:'操作',width:120,align:'center',formatter:operateData},
					{field:'orderId',title:'订单号',width:150,align:'center',sortable:true},
					{field:'amt',title:'提现金额(元)',width:100,align:'right',sortable:true},
					{field:'bankCardId',title:'银行账户',width:100,align:'right'},
					{field:'applyTime',title:'申请时间',width:100,align:'center'},
					{field:'fee',title:'手续费(元)',width:100,align:'right'},
					{field:'stateName',title:'充值状态',width:100,align:'center'}
				]];
	
	var exportBtn = { 
	     	text: '导出', 
	        iconCls: 'icon-redo', 
	        handler: function() {
	        	var params = {};
	        	params.state='auditing';
	        	commonExportExcel('finance_withdrawAuditing_export', params);
	        } 
	      };
	var operateBtns=[];
	if ($('#finance_withdrawAuditing_export').length>0) {
		operateBtns.push(exportBtn);
	}
	if (operateBtns.length==0) {
		operateBtns=null;
	}
	$('#systemwithdrawTable').datagrid({
		url: path + "/systemWithdraw/getList.html?state=auditing",
		columns: columns,
		pagination: true,
		singleSelect: true,
		height : bodyHeight - 230,
		rownumbers : true,
		toolbar:operateBtns
	});
	
	//查询按钮
	$('#searchBtn').click(function(){
		
		var startApplyTime = $("#startApplyTime").val();
		var endApplyTime = $("#endApplyTime").val();
		if(startApplyTime>endApplyTime){
    		$("#timeSpan").html("开始日期不能大于结束日期");
    		return false;
    	}else{
    		$("#timeSpan").html("");
    	}
		queryToGrid("systemwithdrawTable", "list_search");
	});
});	

function finacial_auditd(orderId){
	$("#financal_orderId").val(orderId);

	$("#finacial_auditd").show().dialog({
		title: "审核意见",
		height: 350,
		width: 500,
		modal : true,
		onClose: function () {
        }
	});
	
}

function closeBtn(formId){
   // 添加框取消按钮
   $("#"+formId).dialog('close');
}
function agree(state,formId){
	var url= path + '/systemWithdraw/auditState.html';
	if(formId=="finacial_auditForm"){
		$("#financal_state").val(state);
	}
	$('#'+formId).form(
			'submit',{  
        url:url,  
        onSubmit:function(){
            return $(this).form('validate');  
        },  
        success:function(data){ 
        	var obj = $.parseJSON(data);
			if (obj.success) {
				if(formId=="finacial_auditForm"){
					sendLoanMask=getMask('正在处理审核请求，请稍候！');
					var orderId =$("#financal_orderId").val();
					auditPassed(orderId);
				}
			}
			if (!obj.success) {
				if (obj.msg) {
					$.messager.alert('提示', obj.msg);					
				}
			}
        }  
    }); 
}


function auditPassed(orderId){
	var interval = setInterval(function(){
		var ttime=0;
			$.ajax({
				url: path + "/withdraw/withDrawIsSuccess.html",
				type: "get",
				dataType: "json",
				async: false,
				data: {
					"orderId":orderId
				},
				success: function(data){
				
					if(ttime>100){
		   				sendLoanMask.dialog('close');
						clearInterval(interval);
	   				    $.messager.alert('提示', data.msg);	
    					$("#finacial_auditd").dialog('close');
	   				    window.location.href="withdraw.withdrawAuditingList.html";
	    			}
	    			if(data["success"]){
	    				 $.messager.alert("操作提示", data.msg, "info", function () {
	 	    				clearInterval(interval);
	 	    				sendLoanMask.dialog('close');
	    					$("#finacial_auditd").dialog('close');				   
	  	    		       window.location.href="withdraw.withdrawAuditingList.html";

	    			 });	 
	    			}else{
	    				ttime++;
	    			}
				},
				error: function(request){
					console.log("审核请求失败，请联系管理员!");
					sendLoanMask.dialog('close');
					window.location.href="withdraw.withdrawAuditingList.html";

				}
			});
		
	},2000);
}