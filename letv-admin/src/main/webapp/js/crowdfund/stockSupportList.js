$(function(){
	searchData("/crowdfunding/getSupportList.html?loanNo="+loanNo);
    //查询按钮事件
    $("#searchBtn").click(function(){
    	queryToGrid("progectTable", "list_search");
    });
});
function searchData(dUrl){
	var columns;
	if(loanType == 'public' || loanType == 'product'){
		columns = [[
		                {field:'id',title:'id',width:0,hidden:true},
		                {field:'orderId',title:'投资单号',width:120,align:'center',sortable:true},
						{field:'loanNo',title:'项目编号',width:120,align:'center',sortable:true},
						{field:'loanName',title:'项目名称',width:200,align:'center'},
						{field:'backNo',title:'回报编号',width:100,align:'center'},
						{field:'backContent',title:'回报内容',width:400,align:'center'},
						{field:'supportUser',title:'投资人',width:100,align:'center'},
						{field:'supportUserName',title:'投资人真实姓名',width:100,align:'center'},
						{field:'supportAmt',title:'投资金额',width:80,align:'center',sortable:true},
						{field:'supportWayName',title:'投资方式',width:80,align:'center',sortable:true},
						{field:'payStateName',title:'投资状态',width:100,align:'right'},
						{field:'stateName',title:'订单状态',width:90,align:'center'},
						{field:'supportTime',title:'投资时间',width:140,align:'center',sortable:true},
						{field:'completeTime',title:'投资完成时间',width:140,align:'center',sortable:true},
						{field:'sendTime',title:'发货时间',width:140,align:'center',sortable:true},
						{field:'sendOrderId',title:'发货单号',width:100,align:'center'},
						{field:'sendDelivery',title:'发货快递',width:100,align:'center'}
					]];
	}else{
		columns = [[
	                {field:'id',title:'id',width:0,hidden:true},
	                {field:'orderId',title:'投资单号',width:120,align:'center',sortable:true},
					{field:'loanNo',title:'项目编号',width:120,align:'center',sortable:true},
					{field:'loanName',title:'项目名称',width:200,align:'center'},
					{field:'supportUser',title:'投资人',width:100,align:'center'},
					{field:'supportUserName',title:'投资人真实姓名',width:100,align:'center'},
					{field:'leadInvestorName',title:'领投人',width:100,align:'center'},
					{field:'supportAmt',title:'认购金额('+digital_goods_unit+')',width:80,align:'center',sortable:true},
					{field:'shareRatio',title:'股权出让比例(%)',width:100,align:'center',sortable:true,formatter:setShareRatio},
					{field:'buyNum',title:'认购份数',width:80,align:'center',sortable:true},
 					{field:'supportWayName',title:'投资方式',width:80,align:'center',sortable:true},
					{field:'payStateName',title:'投资状态',width:100,align:'right'},
					{field:'stateName',title:'订单状态',width:90,align:'center'},
					{field:'supportTime',title:'投资时间',width:140,align:'center',sortable:true},
					{field:'intentionFlag',title:'是否支付意向金',width:90,align:'center',formatter:isIntention},
					{field:'intentionAmt',title:'意向金金额('+digital_goods_unit+')',width:100,align:'center'},
					{field:'intentionState',title:'意向金支付状态',width:90,align:'center',formatter:intentionState},
					{field:'intentionEndTime',title:'尾款截止时间',width:140,align:'center'},
					{field:'completeTime',title:'投资完成时间',width:140,align:'center',sortable:true},
				]];
	}
	
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
	if(val){
		return parseFloat(val).toFixed(4)*100 + '%';
	}else{
		return '0%';
	}
}

function isIntention(val,row,index){
	if(val==1){
		return '是';
	}else{
		return '否';
	}
}
function intentionState(val,row,index){
	if(val=="payed"){
		return '已支付';
	}else if(val=="returnback"){
		return '过期退款';
	}
}

