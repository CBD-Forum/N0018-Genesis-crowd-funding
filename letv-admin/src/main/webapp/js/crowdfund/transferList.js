$(function(){
	searchData("/crowdfunding/getBuyTransferListPage.html");
    //查询按钮事件
    $("#searchBtn").click(function(){
    	queryToGrid("progectTable", "list_search");
    });
    
});
function searchData(dUrl){
	var columns = [[
	                {field:'id',title:'id',width:0,hidden:true},
	                {field:'transferNo',title:'转让编号',width:120,align:'center',sortable:true},
					{field:'loanNo',title:'项目编号',width:120,align:'center',sortable:true},
					{field:'loanName',title:'项目名称',width:200,align:'center'},
					{field:'loanStateName',title:'项目状态',width:100,align:'center'},
					{field:'transferUser',title:'转让人',width:100,align:'center'},
					{field:'transferTime',title:'转让发起时间',width:150,align:'center'},
					{field:'buyUser',title:'购买人',width:100,align:'center'},
					{field:'buyTime',title:'购买时间',width:150,align:'center'},
					{field:'supportNo',title:'转让人订单编号',width:150,align:'center'},
					{field:'buySupportNo',title:'购买人订单编号',width:150,align:'center'},
					{field:'transferAmt',title:'转让金额('+digital_goods_unit+')',width:100,align:'center',formatter:NumberFormat},
					{field:'transFee',title:'运费',width:100,align:'center',formatter:NumberFormat},
					{field:'transferFee',title:'转让手续费',width:100,align:'center',formatter:NumberFormat},
					{field:'buyAmt',title:'购买金额('+digital_goods_unit+')',width:100,align:'center',formatter:NumberFormat},
					{field:'accountAmt',title:'到帐金额('+digital_goods_unit+')',width:100,align:'center',formatter:NumberFormat},
					{field:'transferStateName',title:'转让状态',width:100,align:'center'},
					{field:'transferAuditStateName',title:'转让审核状态',width:100,align:'center'},
					{field:'transferAuditOpinion',title:'转让审核内容',width:100,align:'center'},
					{field:'backContent',title:'回报内容',width:380,align:'center',formatter:formatCellTooltip}
				]];
	
	$('#progectTable').datagrid({
		url: path + dUrl,
		columns: columns,
		height:bodyHeight-260,
		nowrap:false,
		rownumbers : true,
		pagination: true
	});
}

function setShareRatio(val,row,index){
	return (Number(val).toFixed(4)*100).toFixed(2) + '%';
}

var sendLoanMask;
function auditTransfer(transferNo,auditState){
	
	if(auditState == 'passed'){
		$.messager.confirm('提示', '您确定要审核通过吗？', function(r) {
			$.ajax({
				type : "POST",
				url : path + '/crowdfunding/updateProductTransferAudit.html',
				data:{
					"transferAuditState" :auditState,
					"transferNo" :transferNo
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
					}
					queryToGrid("progectTable", "list_search");
				}
			});
		});
	}else if(auditState == 'refuse'){
		$.messager.confirm('提示', '您确定要审核拒绝吗？', function(r) {
			$.ajax({
				type : "POST",
				url : path + '/crowdfunding/updateProductTransferAudit.html',
				data:{
					"transferAuditState" :auditState,
					"transferNo" :transferNo
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
					queryToGrid("progectTable", "list_search");
				}
			});
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
		url:path + '/crowdfunding/getLoanAuditBonusPage.html?loanBonusId='+id,
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

