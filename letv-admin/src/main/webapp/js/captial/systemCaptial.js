$(function(){
	searchData("/sysbill/getSysBill.html");
	//查询按钮事件
    $("#searchBtn").click(function(){
    	queryToGrid("progectTable", "list_search");
    });
    //重置
    $('#resetBtn').click(function(){
    	$('#list_search').form('reset');
    });
    getAmtData();//查询页面金额值
});
function searchData(durl){
	//获取字典数据
	var columns = [[
					{field:'tradeId',title:'交易单号',width:120,align:'center'},
					{field:'tradeTime',title:'交易时间',width:140,align:'center'},
					{field:'tradeDirectionName',title:'交易方向',width:80,align:'center'},
					{field:'tradeTypeName',title:'交易类型',width:80,align:'center',sortable:true},
					{field:'userId',title:'用户',width:120,align:'center'},
					{field:'oppositeSide',title:'交易对方',width:120,align:'center'},
					{field:'displayAmt',title:'交易金额('+digital_goods_unit+')',width:120,align:'right',formatter:showDisplayAmt},
					{field:'balance',title:'交易后余额('+digital_goods_unit+')',width:120,align:'right',formatter:showDisplayAmtb},
					{field:'frozenAmt',title:'交易后冻结金额('+digital_goods_unit+')',width:120,align:'right',formatter:showDisplayAmtf},
					{field:'detail',title:'详情',width:150,align:'center'},
					{field:'parentId',title:'上级单号',width:150,align:'center'}
				]];
	
	var exportBtn = { 
	     	text: '导出', 
	        iconCls: 'icon-redo', 
	        handler: function() {
	        	commonExportExcel('finance_capitalManage_sysCapital_export');
	        } 
	      };
	var operateBtns=[];
	if ($('#finance_capitalManage_sysCapital_export').length>0) {
		operateBtns.push(exportBtn);
	}
	if (operateBtns.length==0) {
		operateBtns=null;
	}
	
	$('#progectTable').datagrid({
		url: path + durl,
		columns: columns,
		height:bodyHeight-290,
		rownumbers : true,
		singleSelect: true,
		pagination: true,
		toolbar:operateBtns
	});
}
function showDisplayAmt(val,row,index){
	var num = 0, symbol;
	if(row["displayAmt"].indexOf("+") != "-1"){
		symbol = row["displayAmt"].substring(0,1);
		num = Number(row["displayAmt"].substring(1,row["displayAmt"].length)).toFixed(2);
		return '<span style="color:#00FF00;">'+symbol+num+'</span>';
	}else if(row["displayAmt"].indexOf("-") != "-1"){
		symbol = row["displayAmt"].substring(0,1);
		num = Number(row["displayAmt"].substring(1,row["displayAmt"].length)).toFixed(2);
		return '<span style="color:#FF0000;">'+symbol+num+'</span>';
	}else{
		num = Number(row["displayAmt"]).toFixed(2);
		return '<span style="color:#000000;">'+num+'</span>';
	}
}
function showDisplayAmtb(val,row,index){
	return '<span style="color:#000000;">'+row["balance"].toFixed(2)+'</span>';
}
function showDisplayAmtf(val,row,index){
	return '<span style="color:#000000;">'+row["frozenAmt"].toFixed(2)+'</span>';
}
//查询页面显示金额的值
function getAmtData(){
	$.ajax({
		url: path + "/sysbill/getSysBillTotal.html",
		type: "post",
		dataType: "json",
		success: function(data){
			$("#totalAmt").text(data["totalInAmt"]+digital_goods_unit);
			$("#totalOutAmt").text(data["totalOutAmt"]+digital_goods_unit);
			$("#balance").text(data["balance"]+digital_goods_unit);
		},
		error: function(request){
			console.log("获取页面金额值异常");
		}
	})
}