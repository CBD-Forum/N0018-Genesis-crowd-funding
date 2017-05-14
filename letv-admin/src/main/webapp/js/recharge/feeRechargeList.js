$(function(){
	//获取字典数据
	var columns = [[
					{field:'orderId',title:'订单号',width:150,align:'center',sortable:true},
					{field:'userId',title:'用户名',width:260,align:'center'},
					{field:'rechargeAmt',title:'充值金额(元)',width:100,align:'right',sortable:true},
					{field:'actualAmt',title:'到账金额(元)',width:100,align:'right'},
					{field:'rechargeFee',title:'手续费(元)',width:100,align:'right'},
					{field:'feeAcctNo',title:'手续费支付方',align:'center'},
					{field:'bankName',title:'充值方式',align:'center'},
					{field:'createTime',title:'充值时间',width:160,align:'center',sortable:true},
					{field:'completeTime',title:'到账时间',width:160,align:'center',sortable:true},
					{field:'stateName',title:'充值状态',align:'center'}
				]];
	
	var addBtn = {
	     	text: '充值', 
	        iconCls: 'icon-add', 
	        handler: function() { 
//	        	showRechargeWin();
	        	window.open("http://www.yeepay.com/");
	        } 
	      };
	var exportBtn = { 
	     	text: '导出', 
	        iconCls: 'icon-redo', 
	        handler: function() { 
	        	commonExportExcel('finance_platform_export');
	        } 
	      };
	var operateBtns=[];
	if ($('#finance_platform_recharge').length>0) {
		operateBtns.push(addBtn);
	}
	if ($('#finance_platform_export').length>0) {
		operateBtns.push(exportBtn);
	}
	if (operateBtns.length==0) {
		operateBtns=null;
	}
	
	$('#rechargeTable').datagrid({
		url: path + "/recharge/getMerlist.html",
		columns: columns,
		pagination: true,
		singleSelect: true,
		height : bodyHeight - 230,
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
	
	//充值
	$('#saveBtn').click(function(){
		$('#rechargeForm').submit();
	});
	
	//关闭充值窗口
	$('#closeBtn').click(function(){
		$("#add").dialog('close');
	});
	
});

function showRechargeWin(){
	$("#add").show().dialog({
		title: "充值",
		height: 200,
		width:480,
		modal : true,
		onClose: function () {
			$('#rechargeForm').form('clear');
        }
	});
}
