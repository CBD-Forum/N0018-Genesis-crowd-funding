$(function(){
	searchData("/crowdfunding/getSupportList.html?loanNo="+loanNo+"&refundStateName=1&sort=refundSort");
    //查询按钮事件
    $("#searchBtn").click(function(){
    	queryToGrid("progectTable", "list_search");
    });
    
    $("#auditform").validate({ 
        rules: {
        	auditOpinion: "required"
        },  
        messages: {  
        	auditOpinion: "请填写审核意见" 
        },
        submitHandler:function(form){
        	$('#auditform').form('submit', {
        		url : form.action,
        		success : function(data) {
        			$('#add').dialog("close");
        			$('#progectTable').datagrid("reload");
        			 //location.reload();
        		},
        		error: function(){
        			console.log("审核项目异常");
        		}
        	});
        }
    }); 
	
	//通过按钮事件
	$('#pass').click(function(){
	/*	$('#auditform').attr('action',path + "/crowdfunding/updateRefundState.html?loanState=lended&auditState=passed");
		$('#auditform').submit();*/
		auditPass();
	});
	//拒绝按钮
	$('#refuse').click(function(){
		$('#auditform').attr('action',path + "/crowdfundingRefund/updateRefundState.html?loanState=lended&auditState=refuse");
		$('#auditform').submit();
	});
    
});



//审核通过操作
var sendLoanMask;

function auditPass(){
	var loanNo = $("#loanNo").val();
	var supportNo = $("#supportNo").val();
	var auditOpinion = $("#auditOpinion").val();
	
	if(!auditOpinion){
		$.messager.alert("提示","请填写审核意见！");
	}
	$.messager.confirm('提示', '您确定同意退款吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + "/crowdfundingRefund/updateRefundState.html",
				data : {
					'loanNo' : loanNo,
					'supportNo' : supportNo,
					'auditOpinion':auditOpinion,
					'auditState':'passed'
				},
				beforeSend:function(XMLHttpRequest){
					sendLoanMask=getMask('正在处理退款请求，请稍候！');
					var i = 0;
				    time = setInterval(function(){
						if(i=="3600000"){
							sendLoanMask.dialog('close');
							clearInterval(time);
						}
						i+=5000;
						$.ajax({
							type : "POST",
							url : path + '/crowdfundingRefund/selectRefundPassedStatus.html',
							data : {
								'orderId' : supportNo
							},
							success : function(data) {
								if (data.success) {
									if(data.msg){
										$.messager.alert('提示', '退款处理成功');
										sendLoanMask.dialog('close');
										queryToGrid("progectTable", "list_search");
										clearInterval(time);
									}
									//显示已完成代收的数量
									if(i=="3600000"){
										$.messager.alert('提示',"退款处理失败！");
										clearInterval(time);
										sendLoanMask.dialog('close');
										queryToGrid("progectTable", "list_search");
	    							}
								}else{
									//显示已完成代收的数量
									if(i=="3600000"){
										$.messager.alert('提示',"退款处理失败！");
										clearInterval(time);
										sendLoanMask.dialog('close');
										queryToGrid("progectTable", "list_search");
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
						$("#add").show().dialog('close');
						queryToGrid("progectTable", "list_search");
						//clearInterval(time);
						
					}else{
						$.messager.alert('提示', data.msg+'，请联系管理员。');
						clearInterval(time);
						sendLoanMask.dialog('close');
					}
				},
		        complete:function(XMLHttpRequest,textStatus){
//		        	sendLoanMask.dialog('close');
		        }
			});
		}
	});
} 


function auditRefuse(){
	var loanNo = $("#loanNo").val();
	var supportNo = $("#supportNo").val();
	var auditOpinion = $("#auditOpinion").val();
	
	if(!auditOpinion){
		$.messager.alert("提示","请填写审核意见！");
	}
	$.messager.confirm('提示', '您确定拒绝退款吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + "/crowdfundingRefund/updateRefundState.html",
				data : {
					'loanNo' : loanNo,
					'supportNo' : supportNo,
					'auditOpinion':auditOpinion,
					'auditState':'refuse'
				},
				beforeSend:function(XMLHttpRequest){
					sendLoanMask=getMask('正在请求处理中，请稍候！');
					var i = 0;
				    time = setInterval(function(){
						if(i=="3600000"){
							sendLoanMask.dialog('close');
							clearInterval(time);
						}
						i+=5000;
						$.ajax({
							type : "POST",
							url : path + '/crowdfundingRefund/selectRefundPassedRefuseStatus.html',
							data : {
								'orderId' : supportNo
							},
							success : function(data) {
								if(data.msg){
									$.messager.alert('提示', '退款审核拒绝成功');
									sendLoanMask.dialog('close');
									queryToGrid("progectTable", "list_search");
									clearInterval(time);
								}
								//显示已完成代收的数量
								if(i=="3600000"){
									$.messager.alert('提示',"退款处理失败！");
									clearInterval(time);
									sendLoanMask.dialog('close');
									queryToGrid("progectTable", "list_search");
    							}
							},
					        complete:function(XMLHttpRequest,textStatus){
					        	
					        }
						});
					},5000);
		        },
				success : function(data) {
					if (data.success) {
						$("#add").show().dialog('close');
						$.messager.alert('提示', '退款审核拒绝成功');
						sendLoanMask.dialog('close');
					}else{
						$.messager.alert('提示', data.msg+'，请联系管理员。');
						sendLoanMask.dialog('close');
					}
				},
		        complete:function(XMLHttpRequest,textStatus){
//		        	sendLoanMask.dialog('close');
		        }
			});
		}
	});
} 
function searchData(dUrl){
	var columns = [[
	                	{field:'view',title:'操作',width:120,align:'left',formatter:operateData},
		                {field:'id',title:'id',width:0,hidden:true},
		                {field:'orderId',title:'投资单号',width:120,align:'center',sortable:true},
						{field:'loanNo',title:'项目编号',width:120,align:'center',sortable:true},
						{field:'loanName',title:'项目名称',width:180,align:'center'},
						{field:'backNo',title:'回报编号',width:100,align:'center'},
						{field:'backContent',title:'回报内容',width:380,align:'center',formatter:formatCellTooltip},
						{field:'supportUser',title:'投资人',width:100,align:'center'},
						{field:'supportUserName',title:'投资人真实姓名',width:100,align:'center'},
						{field:'supportAmt',title:'投资金额',width:80,align:'center',sortable:true},
						{field:'transFee',title:'运费',width:80,align:'center',sortable:true},
						{field:'supportWayName',title:'投资方式',width:80,align:'center',sortable:true},
						{field:'payStateName',title:'投资状态',width:100,align:'right'},
						{field:'refundStateName',title:'退款状态',width:100,align:'right'},
						{field:'stateName',title:'订单状态',width:90,align:'center'},
						{field:'supportTime',title:'投资时间',width:140,align:'center',sortable:true},
						{field:'completeTime',title:'投资完成时间',width:140,align:'center',sortable:true},
						{field:'applicationContent',title:'退款理由',width:380,align:'center',formatter:formatCellTooltip},
						{field:'refuseLoanAuditTime',title:'项目方审核时间',width:140,align:'center',sortable:true},
						{field:'refuseReason',title:'项目方审核意见',width:380,align:'center',formatter:formatCellTooltip}
					]];
	
	$('#progectTable').datagrid({
		url: path + dUrl,
		columns: columns,
		height:bodyHeight-230,
		nowrap:false,
		rownumbers : true,
		pagination: true
	});
}

function setShareRatio(val,row,index){
	return (Number(val).toFixed(4)*100).toFixed(2) + '%';
}


/*function auditAuth(orderId,auditState){
	
	if(auditState == 'passed'){
		$.messager.confirm('提示', '您确定要审核通过吗？', function(r) {
			$.ajax({
				type : "POST",
				url : path + '/crowdfunding/updateRefundState.html',
				data:{
					"refundState" :auditState,
					"orderId" :orderId
				},
				success : function(data) {
					if(data.success){
						$.messager.alert("提示","审核完成！");
					}
					queryToGrid("progectTable", "list_search");
				}
			});
		});
	}else if(auditState == 'refuse'){
		$.messager.confirm('提示', '您确定要审核拒绝吗？', function(r) {
			$.ajax({
				type : "POST",
				url : path + '/crowdfunding/updateRefundState.html',
				data:{
					"refundState" :auditState,
					"orderId" :orderId
				},
				success : function(data) {
					if(data.success){
						$.messager.alert("提示","审核完成！");
					}
					queryToGrid("progectTable", "list_search");
				}
			});
		});
	}
}*/

function operate(loanNo,supportNo){
	$("input[name='loanNo']").val(loanNo);
	$("input[name='supportNo']").val(supportNo);
	$("#add").show().dialog({
		title: "退款审核操作",
		height: 230,
		modal : true,
		onClose: function () {
            $("#auditform").form('clear');
        }
	});
}


//审核流程
function openAudit(supportNo){
	var columns = [[
					{field:'loanNo',title:'项目编号',width:120,align:'center',sortable:true},
					{field:'loanName',title:'项目名称',width:110,align:'center',sortable:true},
					{field:'auditor',title:'审核人',width:100,align:'center',sortable:true},
					{field:'auditStateName',title:'操作动作',width:100,align:'center',sortable:true},
					{field:'refundStateName',title:'退款审核状态',width:100,align:'center',sortable:true},
					{field:'auditOpinion',title:'意见',width:110,align:'center',sortable:true},
					{field:'auditTime',title:'操作时间',align:'center',sortable:true}
				]];
	$('#loanRefundAuditTable').datagrid({
		url:path + '/crowdfunding/getLoanAuditRefundPage.html?supportNo='+supportNo,
		columns: columns,
		height: 314,
		rownumbers : true,
		pagination: true
	});
	$("#loanRefundAudit").show().dialog({
		title: "审批列表",
		height: 350,
		width:810,
		modal: true,
		onClose: function () {
        }
	});
}
