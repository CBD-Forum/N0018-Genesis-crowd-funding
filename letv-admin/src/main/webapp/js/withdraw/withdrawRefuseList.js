$(function(){
	//获取字典数据
	var columns = [[
					{field:'orderId',title:'订单号',width:150,align:'center',sortable:true},
					{field:'userId',title:'用户名',width:200,align:'center'},
					{field:'userName',title:'用户真实姓名',width:150,align:'center'},
					{field:'mobile',title:'手机号',width:150,align:'center'},
					{field:'amt',title:'提现金额(元)',width:150,align:'right',sortable:true},
					{field:'bankCardId',title:'银行账户',width:150,align:'right'},
					{field:'bankName',title:'开户行',width:150,align:'right'},
					{field:'applyTime',title:'申请时间',width:150,align:'center'},
					{field:'actualMoney',title:'银行应到账金额(元)',width:80,align:'right'},
					{field:'fee',title:'手续费(元)',width:80,align:'right'} 
					/*{field:'feeAcctNo',title:'手续费支付方',align:'center'},
 					{field:'1',title:'取现渠道',align:'center'},
					{field:'stateName',title:'充值状态',width:150,align:'center'}*/
				]];
	
	var exportBtn = { 
	     	text: '导出', 
	        iconCls: 'icon-redo', 
	        handler: function() {
	        	var params = {};
	        	params.state='refuse';
	        	commonExportExcel('finance_refuse_withdraw_export', params);
	        } 
	      };
	var operateBtns=[];
	if ($('#finance_refuse_withdraw_export').length>0) {
		operateBtns.push(exportBtn);
	}
	if (operateBtns.length==0) {
		operateBtns=null;
	}
	
	$('#withdrawTable').datagrid({
		url: path + "/withdraw/getlist.html?state=refuse",
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
});


function remove(id) {
	// 删除
	$.messager.confirm('提示', '您确定要删除已选的信息吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + '/node/remove.html',
				data : {
					'id' : id
				},
				success : function() {
					queryToGrid("nodeTable", "list_search");
				}
			});
		}
	});
}