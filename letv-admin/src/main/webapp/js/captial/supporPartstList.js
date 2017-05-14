$(function(){
	//获取字典数据
	var columns = [[
					{field:'loanNo',title:'项目编号',width:120,align:'center',sortable:true},
					{field:'loanName',title:'项目名称',width:120,align:'center',sortable:true},
					{field:'supportUser',title:'投资人',width:140,align:'center',sortable:true},
					{field:'realName',title:'投资人真实姓名',width:100,align:'center',sortable:true},
					{field:'mobile',title:'投资人电话',width:100,align:'center',sortable:true},
					{field:'supportAmt',title:'投资金额',width:80,align:'right',sortable:true},
					{field:'transferAmt',title:'转让金额(元)',width:80,align:'right',sortable:true},
					{field:'nowSupportAmt',title:'当前投资金额(元)',width:100,align:'right',formatter:nowSupportAmt},
					{field:'buyNum',title:'投资份数',width:100,align:'center',sortable:true},
					{field:'supportTime',title:'投资时间',align:'center',sortable:true}
				]];
	var exportBtn = { 
	     	text: '导出', 
	        iconCls: 'icon-redo', 
	        handler: function() {
	        	commonExportExcel('finance_capitalManage_supportParts_export');
	        } 
	      };
	var operateBtns=[];
	if ($('#finance_capitalManage_supportParts_export').length>0) {
		operateBtns.push(exportBtn);
	}
	if (operateBtns.length==0) {
		operateBtns=null;
	}
	$('#table').datagrid({
		columns: columns,
		url:path + '/crowdfundingInvestTransfer/getSupportPartsDetailList.html',
		height:bodyHeight-195,
		singleSelect: true,
		rownumbers : true,
		pagination: true,
		toolbar:operateBtns
	});
	
	//查询按钮
	$('#searchBtn').click(function(){
		queryToGrid("table", "list_search");
	});
	function nowSupportAmt(val,row,index){
			return row.supportAmt-row.transferAmt;
	}
});