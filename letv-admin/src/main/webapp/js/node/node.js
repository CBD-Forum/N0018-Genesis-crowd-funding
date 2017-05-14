$(function(){
	//获取字典数据
	var columns = [[
					{field:'code',title:'编号',width:150,align:'center',sortable:true},
					{field:'title',title:'标题',width:200,align:'center'},
					{field:'subtitle',title:'副标题',width:200,align:'center'},
					{field:'nodeTypeName',title:'节点类型',align:'center',sortable:true},
					{field:'keywords',title:'关键词',width:150,align:'center'},
					{field:'statusName',title:'状态',align:'center'},
					{field:'description',title:'描述',width:250,align:'center'},
					{field:'operat',title:'操作',width:120,align:'center',formatter:operateData}
				]];
	
	var addBtn = { 
	     	text: '添加', 
	        iconCls: 'icon-add', 
	        handler: function() { 
	        	window.location.href=path+'/node.nodeadd.html';
	        } 
	      };
	var operateBtns=[];
	if ($('#content_manage_add').length>0) {
		operateBtns.push(addBtn);
	}
	if (operateBtns.length==0) {
		operateBtns=null;
	}
	
	$('#nodeTable').datagrid({
		url: path + "/node/getlist.html",
		columns: columns,
		pagination: true,
		singleSelect: true,
		height : bodyHeight - 230,
		rownumbers : true,
		toolbar: operateBtns
	});
	
	//查询按钮
	$('#searchBtn').click(function(){
		queryNodeGrid("nodeTable", "list_search");
	});
	
	$('#resetBtn').click(function(){
		$('#list_search').form('reset');
	});
});


function queryNodeGrid(id1, id2) {
	$("#" + id1).datagrid("reload",serializeNode($("#" + id2)));
}

function serializeNode(form) {
	var o = {};
	$.each(form.serializeArray(), function(index) {
				if (this['name'] == 'nodeType1') {
					this['name'] = 'nodeType';
				}
				if (o[this['name']]) {
					o[this['name']] = o[this['name']] + "," + this['value'];
				} else {
					o[this['name']] = this['value'];
				}
			});
	return o;
};

function previewNode(id,nodeType){
	//公司简介
	window.open(userWebUrl+'common/nodeList_info.html?nodeType='+nodeType);
	/*if (nodeType=='ggjj') {
		window.open(userWebUrl+'common/nodeList_info.html?nodeType=ggjj');
	}
	//管理团队
	if (nodeType=='gltd') {
		window.open(userWebUrl+'common/nodeList_info.html?nodeType=gltd');
	}
	//监管报告
	if (nodeType=='jgbg') {
		window.open(userWebUrl+'common/nodeList_info.html?nodeType=jgbg');
	}
	//最新动态
	if (nodeType=='zxdt') {
		window.open(userWebUrl+'/common/dynamic_info.html?id='+id);
	}
	//网站公告
	if (nodeType=='wzgg') {
		window.open(userWebUrl+'/common/notice_info.html?id='+id);
	}*/
	
}

function removeNode(id) {
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