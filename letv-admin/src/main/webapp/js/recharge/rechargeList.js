$(function(){
	//获取字典数据
	var columns = [[
	                {field:'view',title:'操作',width:70,align:'center',formatter:operateData},
					{field:'orderId',title:'订单号',width:180,align:'center',sortable:true},
					{field:'userId',title:'用户名',width:200,align:'center'},
					{field:'realName',title:'用户真实姓名',align:'center'},
					{field:'rechargeAmt',title:'充值金额(元)',align:'right',sortable:true,formatter:NumberFormat},
//					{field:'rechargeFee',title:'手续费(元)',align:'right',sortable:true},
//					{field:'feeAcctNo',title:'手续费支付方',align:'center'},
					/*{field:'bank',title:'银行名称',align:'center'},
					{field:'bankAccount',title:'银行卡号',align:'center'},*/
					{field:'createTime',title:'充值时间',width:150,align:'center',sortable:true},
					{field:'completeTime',title:'到账时间',width:150,align:'center',sortable:true},
					{field:'thirdRechargeTypeName',title:'支付方式',width:70,align:'center'},
					{field:'stateName',title:'充值状态',width:100,align:'center'} 
				/*	{field:'blockChainStateName',title:'区块链交易状态',width:100,align:'center'}*/
					
					
				]];
	
	var addBtn = { 
	     	text: '导出', 
	        iconCls: 'icon-redo', 
	        handler: function() { 
	        	commonExportExcel('finance_user_export');
	        } 
	      };
	var operateBtns=[];
	if ($('#finance_user_export').length>0) {
		operateBtns.push(addBtn);
	}
	if (operateBtns.length==0) {
		operateBtns=null;
	}
	
	
	$('#rechargeTable').datagrid({
		url: path + "/recharge/getRechargelist.html",
		columns: columns,
		pagination: true,
		singleSelect: true,
		height : bodyHeight - 260,
		rownumbers : true,
		toolbar:operateBtns
	});
	
	//查询按钮
	$('#searchBtn').click(function(){
		queryToGrid("rechargeTable", "list_search");
	});
	
	//重置查询条件
	$('#resetBtn').click(function(){
		$('#list_search').form('reset');
	});
});



function auditAuth(orderId,auditState){
	$.ajax({
		type : "POST",
		url : path + '/recharge/repay.html',
		data:{
			"state" :auditState,
			"orderId" :orderId
		},
		success : function(data) {
			if(data.success){
				$.messager.alert("提示","审核完成！");
			}
			queryToGrid("rechargeTable", "list_search");
		}
	});
}



function closeBtn(formId){
   // 添加框取消按钮
   $("#"+formId).dialog('close');
}

function disAgree(state,formId){
	var url= path + '/recharge/auditState.html';
	if(formId=="finacial_auditForm"){
		$("#financal_state").val(state);
	}else{
		$("#operator_state").val(state);
	}
 
	$('#'+formId).form('submit',{  
        url:url,  
        onSubmit:function(){
            return $(this).form('validate');  
        },  
        success:function(data){ 
        	var obj = $.parseJSON(data);
			if (obj.success) {
				
				if(formId=="finacial_auditForm"){
					$("#financialOpinion").val("");
					$("#finacial_auditd").show().dialog('close');
				}else{
					$("#auditOpinion").val("");
					$("#auditd").show().dialog('close');
				}
				queryToGrid("rechargeTable", "list_search");
			}else{
			    if (obj.msg) {
				   $.messager.alert('提示', obj.msg);	
				}
			}
        }
	}); 
}

//审核
function agree(state,formId){
	
	var url= path + '/recharge/auditState.html';
	if(formId=="finacial_auditForm"){  //财务审核
		if(state=="finaical_passed"){  //财务审核通过
			$("#financal_state").val(state);
			financalPassed();
		}else{  //运营审核通过
			$("#operator_state").val(state);
			$('#'+formId).form('submit',{  
		        url:url,  
		        onSubmit:function(){
		            return $(this).form('validate');  
		        },  
		        success:function(data){ 
		        	var obj = $.parseJSON(data);
					if (obj.success) {
						$("#auditOpinion").val("");
						$("#financialOpinion").val("");
						$("#auditd").show().dialog('close');
						queryToGrid("rechargeTable", "list_search");
					}
					if (!obj.success) {
						if (obj.msg) {
							$.messager.alert('提示', obj.msg);					
						}
					}
		        }  
		    }); 
		}
	}else{  //运营审核
		$("#operator_state").val(state);
		$('#'+formId).form('submit',{  
	        url:url,  
	        onSubmit:function(){
	            return $(this).form('validate');  
	        },  
	        success:function(data){ 
	        	var obj = $.parseJSON(data);
				if (obj.success) {
					$("#auditOpinion").val("");
					$("#financialOpinion").val("");
					$("#auditd").show().dialog('close');
					queryToGrid("rechargeTable", "list_search");
				}
				if (!obj.success) {
					if (obj.msg) {
						$.messager.alert('提示', obj.msg);					
					}
				}
	        }  
	    }); 
	}
}




/**
 * 充值审核通过
 */
var sendLoanMask;
function financalPassed(){
	
	var orderId = $("#financal_orderId").val();
	var auditOpinion = $("#financialOpinion").val();
	if(!auditOpinion){
		$.messager.alert("提示","请填写审核意见！");
	}
	$.messager.confirm('提示', '您确定审核通过吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + "/recharge/auditState.html",
				data : {
					'orderId' : orderId,
					'auditOpinion':auditOpinion,
					'auditState':'passed',
					'state':'finaical_passed'
				},
				beforeSend:function(XMLHttpRequest){
					sendLoanMask=getMask('正在处理充值请求，请稍候！');
					var i = 0;
				    time = setInterval(function(){
						if(i=="3600000"){
							sendLoanMask.dialog('close');
							clearInterval(time);
						}
						i+=5000;
						$.ajax({
							type : "POST",
							url : path + '/recharge/selectRechargeState.html',
							data : {
								'orderId' : orderId
							},
							success : function(data) {
								if (data.success) {
									if(data.msg){
										$.messager.alert('提示', '处理成功');
										sendLoanMask.dialog('close');
										$("#auditOpinion").val("");
										$("#financialOpinion").val("");
										
										$("#finacial_auditd").show().dialog('close');
										queryToGrid("rechargeTable", "list_search");
										clearInterval(time);
									}
									//显示已完成代收的数量
									if(i=="3600000"){
										$.messager.alert('提示',"处理失败！");
										clearInterval(time);
										sendLoanMask.dialog('close');
										$("#auditOpinion").val("");
										$("#financialOpinion").val("");
										$("#finacial_auditd").show().dialog('close');
										queryToGrid("rechargeTable", "list_search");
	    							}
								}else{
									//显示已完成代收的数量
									if(i=="3600000"){
										$.messager.alert('提示',"处理失败！");
										clearInterval(time);
										sendLoanMask.dialog('close');
										$("#finacial_auditd").show().dialog('close');
										queryToGrid("rechargeTable", "list_search");
	    							}
								}
							},
					        complete:function(XMLHttpRequest,textStatus){
					        	
					        }
						});
					},5000);
		        },
				success : function(data) {
					if (data.success) {
						$("#finacial_auditd").show().dialog('close');
						//queryToGrid("rechargeTable", "list_search");
					}else{
						$.messager.alert('提示', data.msg+'，请联系管理员。');
						clearInterval(time);
						sendLoanMask.dialog('close');
					}
				},
		        complete:function(XMLHttpRequest,textStatus){
		        	
		        }
			});
		}
	});
} 	

 

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

function NumberFormat(val,row,index){
	if(!val){
		val = 0;
	}
	return  val.toFixed(2);
}



