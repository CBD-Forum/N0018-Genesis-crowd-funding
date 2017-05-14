$(function(){
	searchData("/crowdfunding/getUserCapitalList.html");
	//查询按钮事件
    $("#searchBtn").click(function(){
    	queryToGrid("progectTable", "list_search");
    });
});
function searchData(durl){
	//获取字典数据
	var columns = [[
					{field:'user_id',title:'用户名',width:120,align:'center'},
					{field:'real_name',title:'真实姓名',width:140,align:'center'},
					{field:'balance',title:'可用余额('+digital_goods_unit+')',width:120,align:'right'},
					{field:'frozenAmt',title:'冻结金额('+digital_goods_unit+')',width:120,align:'right'},
					{field:'totalSupportAmt',title:'累积支持金额('+digital_goods_unit+')',width:120,align:'right'},
					//{field:'totalPayAmt',title:'累积购买特卖金额(元)',width:100,align:'right'},
					//{field:'totalRechargeAmt',title:'充值金额(元)',width:110,align:'right'},
					{field:'totalWithdrawAmt',title:'已提现成功金额('+digital_goods_unit+')',width:120,align:'right'},
					{field:'totalLoanAmt',title:'筹资总额('+digital_goods_unit+')',width:120,align:'right'},
					{field:'totalCapital',title:'账户净资产('+digital_goods_unit+')',width:120,align:'right'}
				]];
	
	var exportBtn = { 
	     	text: '导出', 
	        iconCls: 'icon-redo', 
	        handler: function() {
	        	commonExportExcel('finance_capitalManage_countAmt_export');
	        } 
	      };
	var operateBtns=[];
	if ($('#finance_capitalManage_countAmt_export').length>0) {
		operateBtns.push(exportBtn);
	}
	if (operateBtns.length==0) {
		operateBtns=null;
	}
	$('#progectTable').datagrid({
		url: path + durl,
		columns: columns,
		height:bodyHeight-193,
		rownumbers : true,
		nowrap:false,
		singleSelect: true,
		pagination: true,
		toolbar:operateBtns
	});
}