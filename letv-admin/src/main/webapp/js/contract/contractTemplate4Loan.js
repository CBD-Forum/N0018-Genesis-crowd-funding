$(function(){
	//获取字典数据
	var columns = [[
					{field:'contractNo',title:'项目编号',width:150,align:'center',sortable:true},
					{field:'loanName',title:'项目名称',width:180,align:'center'},
					{field:'contractName',title:'模板名称',width:180,align:'center'},
					{field:'contractTypeName',title:'模板类型',width:180,align:'center'},
					{field:'statusName',title:'状态',align:'center'},
					{field:'description',title:'描述',width:250,align:'center'},
					{field:'createTime',title:'录入时间',width:130,align:'center'},
					{field:'updateTime',title:'发表时间',width:130,align:'center'},
					{field:'operat',title:'操作',width:120,align:'center',formatter:operateData}
				]];
	$('#contractTemTable').datagrid({
		url: path + "/contract/getlist.html?isTpl=1",
		columns: columns,
		pagination: true,
		height : bodyHeight - 200,
		singleSelect: true,
		rownumbers : true
	});
	
	//查询按钮
	$('#searchBtn').click(function(){
		queryToGrid("contractTemTable", "list_search");
	});
	
	//重置按钮
	$('#resetBtn').click(function(){
		$('#list_search').form('reset');
	});
});