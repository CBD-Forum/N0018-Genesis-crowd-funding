$(function(){
	searchData("/crowdfunding/getSupportList.html?loanNo="+loanNo+"&refundState=auditing");
    //查询按钮事件
    $("#searchBtn").click(function(){
    	queryToGrid("progectTable", "list_search");
    });
});
function searchData(dUrl){
	var columns = [[
	                	{field:'view',title:'操作',width:120,align:'center',formatter:operateData},
		                {field:'id',title:'id',width:0,hidden:true},
		                {field:'orderId',title:'投资单号',width:120,align:'center',sortable:true},
						{field:'loanNo',title:'项目编号',width:120,align:'center',sortable:true},
						{field:'loanName',title:'项目名称',width:100,align:'center'},
						{field:'backNo',title:'回报编号',width:100,align:'center'},
						{field:'backContent',title:'回报内容',width:100,align:'center'},
						{field:'supportUser',title:'投资人',width:100,align:'center'},
						{field:'supportUserName',title:'投资人真实姓名',width:100,align:'center'},
						{field:'supportAmt',title:'投资金额',width:80,align:'center',sortable:true},
						{field:'supportWayName',title:'投资方式',width:80,align:'center',sortable:true},
						{field:'payStateName',title:'投资状态',width:100,align:'right'},
						{field:'refundStateName',title:'退款状态',width:100,align:'right'},
						{field:'stateName',title:'订单状态',width:90,align:'center'},
						{field:'supportTime',title:'投资时间',width:140,align:'center',sortable:true},
						{field:'completeTime',title:'投资完成时间',width:140,align:'center',sortable:true},
						{field:'sendTime',title:'发货时间',width:140,align:'center',sortable:true},
						{field:'sendOrderId',title:'发货单号',width:100,align:'center'},
						{field:'sendDelivery',title:'发货快递',width:100,align:'center'}
					]];
	
	$('#progectTable').datagrid({
		url: path + dUrl,
		columns: columns,
		height:bodyHeight-195,
		nowrap:false,
		rownumbers : true,
		pagination: true
	});
}

function setShareRatio(val,row,index){
	return (Number(val).toFixed(4)*100).toFixed(2) + '%';
}


function auditAuth(orderId,auditState){
	
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
	
	
	
}
