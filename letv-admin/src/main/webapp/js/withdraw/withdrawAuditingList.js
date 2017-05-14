$(function(){
	//获取字典数据
	var columns = [[
	                {field:'operat',title:'操作',width:120,align:'center',formatter:operateData},
					{field:'orderId',title:'订单号',width:150,align:'center',sortable:true},
					{field:'userId',title:'用户名',width:200,align:'center',sortable:true},
					{field:'userName',title:'用户真实姓名',width:100,align:'center'},
					{field:'mobile',title:'手机号',width:100,align:'center'},
					{field:'amt',title:'提现金额(元)',width:100,align:'right',sortable:true},
					{field:'bankCardId',title:'银行账户',width:100,align:'right'},
					{field:'openAcctBank',title:'开户行',width:100,align:'right'},
					{field:'applyTime',title:'申请时间',width:100,align:'center'},
					{field:'actualMoney',title:'账户应扣金额(元)',width:100,align:'right'},
					{field:'fee',title:'手续费(元)',width:100,align:'right'},
				/*	{field:'feeAcctNo',title:'手续费支付方',align:'center'},*/
					{field:'stateName',title:'状态',width:100,align:'center'}
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
	$('#withdrawTable').datagrid({
		url: path + "/withdraw/getlist.html?state=auditing",
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
		queryToGrid("withdrawTable", "list_search");
	});
	
	//重置
	$('#resetBtn').click(function(){
		$('#list_search').form('clear');
	});
	
	//关闭审核窗口
	$('#closeBtn').click(function(){
		$('#add').dialog('close');
	});
	
	$("#auditingForm").validate({
		rules : {
			auditOpinion : "required"
		},
		messages : {
			auditOpinion : "请填写意见"
		},				
        submitHandler:function(form){
        	var url = '';
    		if ($('#auditingForm input[name="btnId"]').val() =='passedBtn') {
    			url=path + '/withdraw/auditPass.html';
    		}else if ($('#auditingForm input[name="btnId"]').val() =='refuseBtn') {
    			url=path + '/withdraw/auditRefuse.html';
    		}
    		$('#auditingForm').form('submit', {
    			url : url,
    			success : function(data) {
    				var obj = $.parseJSON(data);
    				if (obj.success) {
    					$("#add").show().dialog('close');
    					queryToGrid("withdrawTable", "list_search");
    				}
    			}
    		});
        }
    });
	
	// 通过
	$('#passedBtn').click(function() {
		$('#auditingForm input[name="btnId"]').val('passedBtn');
		$('#auditingForm').submit();
	});
	// 拒绝
	$('#refuseBtn').click(function() {
		$('#auditingForm input[name="btnId"]').val('refuseBtn');
		$('#auditingForm').submit();
	});
	
});


/**
 * 审核
 * @param orderId
 */
function auditing(orderId){
	$('#auditingForm input[name="orderName"]').val(orderId);
	$('#auditingForm input[name="orderId"]').val(orderId);
	$("#add").show().dialog({
		title: "审核",
		height: 300,
		width: 500,
		modal : true,
		onClose: function () {
            $("#auditingForm").form('clear');
        }
	});
}

/**
 * 查看用户详细信息
 * @param userId
 */
function showUserDetail(userId) {
	$.ajax({
		type : "POST",
		url : path + '/user/getUserDetail.html',
		data : {
			'userId' : userId
		},
		success : function(data) {
			$("#userDetail label").each(function(){
				$(this).text('');
				$(this).text(data[this.id]);
				if(this.id == 'isBorrower'){
					$(this).text(data[this.id]=='0'?'不是':'是');
				}
				if(this.id == 'sex'){
					$(this).text(data[this.id]=='M'?'男':'女');
				}
				if(this.id == 'certificateType'){
					$(this).text(data[this.id]=='00'?'身份证':'其他');
				}
			});
			$("#userDetail img").each(function(){
				if(this.id == 'photo' && data[this.id] == undefined){
					$(this).attr('src',''+fileUrl+'pic_user.png');
				}else{
					$(this).attr('src',''+fileUrl+data[this.id]);
				}
			});
		}
	});
	
	$("#userDetail").show().dialog({
		title: "用户详情",
		height: 600,
		width: 800,
		modal : true,
		onClose: function () {
//            $("#dicForm").form('clear');
        }
	});
}

function closeBtn(formId){
   // 添加框取消按钮
   $("#"+formId).dialog('close');
}

function disAgree(state,formId){
	var url= path + '/withdraw/auditState.html';
	if(formId=="finacial_auditForm"){
		$("#financal_state").val(state);
	}else{
		$("#operator_state").val(state);
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
				sendLoanMask=getMask('正在处理审核请求，请稍候！');
				var requestId = obj.requestId;
				var type='';
				findByRequestId(requestId,type,formId);
			}
			 
			if (!obj.success) {
				if (obj.msg) {
				   $.messager.alert('提示', obj.msg);	
				}
			}
        }  
    }); 
}
var sendLoanMask;
function findByRequestId(requestId,type,formId){
	var interval = setInterval(function(){
		var ttime=0;
			$.ajax({
				url: path + "/withdraw/selectQueryIsSuccess.html",
				type: "get",
				dataType: "json",
				async: false,
				data: {
					"requestId":requestId
				},
				success: function(data){
					if(ttime>100){
		   				sendLoanMask.dialog('close');
						clearInterval(interval);
	   				    $.messager.alert('提示',type + "不同意审核失败!");	
	   				    window.location.href="withdraw.withdrawAuditingList.html";
	    			}
	    			if(data["success"]){
	    				 $.messager.alert("操作提示", "审核不同意成功", "info", function () {
	 	    				clearInterval(interval);

	 	    				sendLoanMask.dialog('close');
	 	    				 if(formId=="finacial_auditForm"){
	    					     $("#finacial_auditd").dialog('close');
	    				   }else{
	    				      $("#auditd").dialog('close');
	    				   }  
	  	    		       window.location.href="withdraw.withdrawAuditingList.html";
		  	    		   

	    			 });	 
	    			}else{
	    				ttime++;
	    			}
				},
				error: function(request){
					console.log("审核请求失败，请联系管理员!");
					sendLoanMask.dialog('close');
					//window.location.href="withdraw.withdrawAuditingList.html";

				}
			});
		
	},2000);
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

//审核通过
function agree(state,formId){
	var url= path + '/withdraw/auditState.html';
	if(formId=="finacial_auditForm"){
		$("#financal_state").val(state);
	}else{
		$("#operator_state").val(state);

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

				}else{
				 $.messager.alert("操作提示", "审核同意", "info", function () {
					   if(formId=="finacial_auditForm"){
                           $("#finacial_auditd").dialog('close');
					   }else{
					      $("#auditd").dialog('close');
					   }   
			        });	
					window.location.href="withdraw.withdrawAuditingList.html";

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
//运营审核弹出框
function audit(orderId){
	$("#operator_orderId").val(orderId);
	$("#auditd").show().dialog({
		title: "审核意见",
		height: 350,
		width: 500,
		modal : true,
		onClose: function () {
        }
	});
	
}

//财务审核
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
