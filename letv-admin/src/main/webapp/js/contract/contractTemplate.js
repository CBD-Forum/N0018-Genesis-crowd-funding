$(function(){
	//获取字典数据
	var columns = [[
					{field:'contractNo',title:'模板编号',width:150,align:'center',sortable:true},
					{field:'contractName',title:'模板名称',width:180,align:'center'},
					{field:'contractTypeName',title:'模板类型',width:180,align:'center'},
					{field:'statusName',title:'状态',align:'center'},
					{field:'description',title:'描述',width:250,align:'center'},
					{field:'createTime',title:'录入时间',width:130,align:'center'},
					{field:'updateTime',title:'发表时间',width:130,align:'center'},
					{field:'operat',title:'操作',width:120,align:'center',formatter:operateData}
				]];
	var addBtn = { 
	     	text: '添加', 
	        iconCls: 'icon-add', 
	        handler: function() { 
	        	window.location.href=path+'/contract.contractTemplateadd.html';
	        } 
	      };
	var operateBtns=[];
	if ($('#contract_tpl_add').length>0) {
		operateBtns.push(addBtn);
	}
	if (operateBtns.length==0) {
		operateBtns=null;
	}
	
	$('#contractTemTable').datagrid({
		url: path + "/contract/getlist.html?isTpl=0",
		columns: columns,
		pagination: true,
		height : bodyHeight - 200,
		singleSelect: true,
		rownumbers : true,
		toolbar: operateBtns
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


function removeContract(id) {
	// 删除
	$.messager.confirm('提示', '您确定要删除已选的信息吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + '/contract/remove.html',
				data : {
					'id' : id
				},
				success : function() {
					queryToGrid("contractTemTable", "list_search");
				}
			});
		}
	});
}