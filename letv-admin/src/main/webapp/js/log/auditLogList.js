$(function(){
	//获取字典数据
	var columns = [[
//					{field:'operat',title:'操作',align:'center',formatter:operateData},
					{field:'userId',title:'用户名',width:150,align:'center',sortable:true},
					{field:'userTypeText',title:'用户类型',width:80,align:'center',sortable:true},
					{field:'operateTime',title:'操作时间',width:150,align:'center',sortable:true},
					{field:'ipAddress',title:'IP地址',width:130,align:'center',sortable:true},
					{field:'operateModeText',title:'操作模块',width:100,align:'center'},
					{field:'operateTypeText',title:'操作类型',width:100,align:'center'},
					{field:'operateResult',title:'操作结果',width:100,align:'center',
						formatter:function(val,row,index){
							if (val == 'success') {
								return '成功';
							}else{
								return '失败';
							}
					}},
					{field:'resInfo',title:'异常信息',width:370,align:'center'}
				]];
	
	$('#auditLogTable').datagrid({
		url: path + "/log/getOperateLoglist.html",
		columns: columns,
		rownumbers : true,
		height:bodyHeight-230,
		singleSelect: true,
		pagination: true
	});
	
	//查询按钮事件
    $("#searchBtn").click(function(){
    	queryToGrid("auditLogTable", "list_search");
    });
    
    //重置
    $('#resetBtn').click(function(){
    	$('#list_search').form('reset');
    });
});