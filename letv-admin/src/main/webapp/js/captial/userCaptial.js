$(function(){
	searchData("/bill/getUserBill.html");
	//查询按钮事件
    $("#searchBtn").click(function(){
    	queryToGrid("captialTable", "list_search");
    });
});
function searchData(durl){
	//获取字典数据
	var columns = [[
					{field:'tradeId',title:'交易单号',width:150,align:'center',sortable:true},
					{field:'tradeTime',title:'交易时间',width:140,align:'center',sortable:true},
					{field:'realName',title:'用户名',width:140,align:'center',sortable:true},
					{field:'mobile',title:'手机号',width:140,align:'center',sortable:true},
					{field:'tradeDirectionName',title:'交易方向',width:80,align:'center',sortable:true},
					{field:'tradeTypeName',title:'交易类型',width:80,align:'center',sortable:true},
					{field:'userId',title:'用户',width:80,align:'center',sortable:true},
					{field:'oppositeSide',title:'交易对方',width:200,align:'center',sortable:true},
					{field:'displayAmt',title:'交易金额('+digital_goods_unit+')',width:100,align:'right',sortable:true},
					{field:'balance',title:'交易后余额('+digital_goods_unit+')',width:100,align:'right',sortable:true,formatter:NumberFormat},
					{field:'frozenAmt',title:'交易后冻结金额('+digital_goods_unit+')',width:110,align:'right',sortable:true,formatter:NumberFormat},
					{field:'detail',title:'详情',width:400,sortable:true},
					{field:'parentId',title:'上级单号',width:140,align:'center',sortable:true}
				]];
	
	var exportBtn = { 
	     	text: '导出', 
	        iconCls: 'icon-redo', 
	        handler: function() {
	        	commonExportExcel('finance_capitalManage_userCapital_export');
	        } 
	      };
	var operateBtns=[];
	if ($('#finance_capitalManage_userCapital_export').length>0) {
		operateBtns.push(exportBtn);
	}
	if (operateBtns.length==0) {
		operateBtns=null;
	}
	
	$('#captialTable').datagrid({
		url: path + durl,
		columns: columns,
		height:bodyHeight-260,
		rownumbers : true,
		singleSelect: true,
		sortName : 'tradeTime',
		sortOrder : 'desc',
		pagination: true,
		toolbar:operateBtns
	});
}

function NumberFormat(val,row,index){
	if(!val){
		val = 0;
	}
	return  val.toFixed(2);
}
