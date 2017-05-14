$(function(){
	searchData("/crowdfunding/getBuyTransferListPage.html?transferAuditStateIn=state");
    //查询按钮事件
    $("#searchBtn").click(function(){
    	queryToGrid("progectTable", "list_search");
    });
    
});
function searchData(dUrl){
	var columns = [[
	                	{field:'view',title:'操作',width:150,align:'center',formatter:operateData},
		                {field:'id',title:'id',width:0,hidden:true},
		                {field:'transferNo',title:'转让编号',width:120,align:'center',sortable:true},
						{field:'loanNo',title:'项目编号',width:120,align:'center',sortable:true},
						{field:'loanName',title:'项目名称',width:200,align:'center'},
						{field:'transferUser',title:'转让人',width:100,align:'center'},
						{field:'buyUser',title:'购买人',width:100,align:'center'},
						{field:'buyTime',title:'购买时间',width:150,align:'center'},
						{field:'transferAmt',title:'购买金额('+digital_goods_unit+')',width:100,align:'right',formatter:NumberFormat},
						{field:'transferAuditStateName',title:'转让审核状态',width:100,align:'center'},
						{field:'transferAuditOpinion',title:'转让审核内容',width:100,align:'center'},
						{field:'backContent',title:'回报内容',width:380,align:'center',formatter:formatCellTooltip}
					]];
	
	$('#progectTable').datagrid({
		url: path + dUrl,
		columns: columns,
		height:bodyHeight-220,
		nowrap:false,
		rownumbers : true,
		pagination: true
	});
}

function setShareRatio(val,row,index){
	return (Number(val).toFixed(4)*100).toFixed(2) + '%';
}

var sendLoanMask; 

function auditTransfer(auditState){
	var transferNo=$("#auditform input[name='transferNo']").val();
	var transferAuditOpinion=$("#auditform textarea[name='transferAuditOpinion']").val();
	//alert(transferAuditOpinion);
	if(transferNo==null || transferNo.length==0){
		$.messager.alert('提示',"转让编号异常！");
	}else if(transferAuditOpinion==null || transferAuditOpinion.length==0){
		$.messager.alert('提示',"请输入审核意见！");
	}else if(auditState == 'passed'){
		$.messager.confirm('提示', '您确定要审核通过吗？', function(r) {
			if (r) {
				$.ajax({
					type : "POST",
					url : path + '/crowdfunding/updateProductTransferAudit.html',
					data:{
						"transferAuditState" :auditState,
						"transferNo" :transferNo,
						"transferAuditOpinion":transferAuditOpinion
					},
					beforeSend:function(XMLHttpRequest){
						sendLoanMask=getMask('正在处理请求，请稍候！');
						var i = 0;
					    time = setInterval(function(){
							if(i=="3600000"){
								sendLoanMask.dialog('close');
								clearInterval(time);
							}
							i+=5000;
							$.ajax({
								type : "POST",
								url : path + '/crowdfunding/queryTransferAuditStatus.html',
								data : {
									"transferAuditState" :auditState,
									"transferNo" :transferNo
								},
								success : function(data) {
									if (data.success) {
										if(data.msg){
											$.messager.alert('提示', '操作成功');
											sendLoanMask.dialog('close');
											queryToGrid("progectTable", "list_search");
											clearInterval(time);
										}
										//显示已完成代收的数量
										if(i=="3600000"){
											$.messager.alert('提示',"操作失败！");
											clearInterval(time);
											sendLoanMask.dialog('close');
											queryToGrid("progectTable", "list_search");
		    							}
									}else{
										$.messager.alert('提示',"操作失败！");
										sendLoanMask.dialog('close');
										queryToGrid("progectTable", "list_search");
										clearInterval(time);
									}
								},
						        complete:function(XMLHttpRequest,textStatus){
						        	
						        }
							});
						},5000);
			        },
					success : function(data) {
						if(data.success){
							$.messager.alert("提示","操作完成！");
						}else{
							$.messager.alert("提示",data.msg);
							clearInterval(time);
							sendLoanMask.dialog('close');
						}
						$("#auditDiv").show().dialog('close');
						queryToGrid("progectTable", "list_search");
					}
				});	
			}
		});
	}else if(auditState == 'refuse'){
		$.messager.confirm('提示', '您确定要审核拒绝吗？', function(r) {
			if (r) {
				$.ajax({
					type : "POST",
					url : path + '/crowdfunding/updateProductTransferAudit.html',
					data:{
						"transferAuditState" :auditState,
						"transferNo" :transferNo,
						"transferAuditOpinion":transferAuditOpinion
					},
					beforeSend:function(XMLHttpRequest){
						sendLoanMask=getMask('正在处理请求，请稍候！');
						var i = 0;
					    time = setInterval(function(){
							if(i=="3600000"){
								sendLoanMask.dialog('close');
								clearInterval(time);
							}
							i+=5000;
							$.ajax({
								type : "POST",
								url : path + '/crowdfunding/queryTransferAuditStatus.html',
								data : {
									"transferAuditState" :auditState,
									"transferNo" :transferNo
								},
								success : function(data) {
									if (data.success) {
										if(data.msg){
											$.messager.alert('提示', '操作成功');
											sendLoanMask.dialog('close');
											queryToGrid("progectTable", "list_search");
											clearInterval(time);
										}
										//显示已完成代收的数量
										if(i=="3600000"){
											$.messager.alert('提示',"操作失败！");
											clearInterval(time);
											sendLoanMask.dialog('close');
											queryToGrid("progectTable", "list_search");
		    							}
									}else{
										$.messager.alert('提示',"操作失败！");
										sendLoanMask.dialog('close');
										queryToGrid("progectTable", "list_search");
										clearInterval(time);
									}
								},
						        complete:function(XMLHttpRequest,textStatus){
						        	
						        }
							});
						},5000);
			        },
					success : function(data) {
						if (data.success) {
							$("#auditDiv").show().dialog('close');
							queryToGrid("progectTable", "list_search");
							//clearInterval(time);
							
						}else{
							$.messager.alert('提示', data.msg+'，请联系管理员。');
							clearInterval(time);
							sendLoanMask.dialog('close');
						}
					}
				});	
			}
		});
	}
	
}



function operate(loanNo,loanBonusId){
	$("#auditform input[name='loanNo']").val(loanNo);
	$("input[name='loanBonusId']").val(loanBonusId);
	$("#add").show().dialog({
		title: "分红审核操作",
		height: 230,
		modal : true,
		onClose: function () {
            $("#auditform").form('clear');
        }
	});
}
function auditOp(transferNo){
	//alert("transferNo:"+transferNo);
	$("#auditDiv input[name='transferNo']").val(transferNo);
	$("#auditDiv").show().dialog({
		title: "转让审核操作",
		width:350,
		height: 180,
		modal : true,
		onClose: function () {
            $("#auditform").form('clear');
        }
	});
}

//审核流程
function openAudit(id){
	var columns = [[
					{field:'loanNo',title:'项目编号',width:120,align:'center',sortable:true},
					{field:'loanName',title:'项目名称',width:110,align:'center',sortable:true},
					{field:'auditor',title:'审核人',width:100,align:'center',sortable:true},
					{field:'auditStateName',title:'操作动作',width:100,align:'center',sortable:true},
					{field:'bonusStateName',title:'分红审核状态',width:100,align:'center',sortable:true},
					{field:'auditOpinion',title:'意见',width:110,align:'center',sortable:true},
					{field:'auditTime',title:'操作时间',align:'center',sortable:true}
				]];
	$('#loanBonusAuditTable').datagrid({
		url:path + '/crowdfundingBonus/getLoanAuditBonusPage.html?loanBonusId='+id,
		columns: columns,
		height: 314,
		rownumbers : true,
		pagination: true
	});
	$("#loanBonusAudit").show().dialog({
		title: "审批列表",
		height: 350,
		width:810,
		modal: true,
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

