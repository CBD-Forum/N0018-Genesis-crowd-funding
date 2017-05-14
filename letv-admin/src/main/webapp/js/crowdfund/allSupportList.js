$(function(){
	searchData("/crowdfunding/getSupportList.html");
    //查询按钮事件
    $("#searchBtn").click(function(){
    	queryToGrid("progectTable", "list_search");
    });
});
function searchData(dUrl){
	var columns = [[
	                {field:'id',title:'id',width:0,hidden:true},
	                {field:'orderId',title:'投资单号',width:120,align:'center',sortable:true},
					{field:'loanNo',title:'项目编号',width:120,align:'center',sortable:true},
					{field:'loanName',title:'项目名称',width:100,align:'center'},
					{field:'backNo',title:'回报编号',width:100,align:'center'},
					{field:'supportUser',title:'投资人',width:100,align:'center'},
					{field:'supportUserName',title:'投资人真实姓名',width:100,align:'center'},
					{field:'supportAmt',title:'投资金额',width:80,align:'center',sortable:true},
					{field:'buyNum',title:'认购份数',width:80,align:'center',sortable:true},
					{field:'shareRatio',title:'分红比例(%)',width:80,align:'center',sortable:true,formatter:setShareRatio},
					{field:'supportWayName',title:'投资方式',width:80,align:'center',sortable:true},
					{field:'payStateName',title:'投资状态',width:100,align:'right'},
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
		height:bodyHeight-265,
		nowrap:false,
		rownumbers : true,
		pagination: true
	});
}

function setShareRatio(val,row,index){
	return parseFloat(val).toFixed(2)*100 + '%';
}
