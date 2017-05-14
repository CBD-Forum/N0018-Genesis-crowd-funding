$(function() {
	// 获取字典数据
	var columns = [[
	                {field:'operat',width:120,title:'操作',align:'center',formatter:operateData},
					{field:'code',width:120,title:'编号',align:'center',sortable:true},
					{field:'title',width:250,title:'title',align:'center',sortable:true},
					{field:'description',width:250,title:'description',align:'center',sortable:true},
					{field:'keyword',width:250,title:'keyword',align:'center',sortable:true},
					{field:'memo',width:170,title:'描述',align:'center',sortable:true}
				]];
	
	var addBtn = { 
	     	text: '添加', 
	        iconCls: 'icon-add', 
	        handler: function() { 
	        	addWin();
	        } 
	      };
	var operateBtns=[];
	if ($('#spread_tdk_add').length>0) {
		operateBtns.push(addBtn);
	}
	if (operateBtns.length==0) {
		operateBtns=null;
	}

	$('#tdkTable').datagrid({
		url : path + "/seotdk/getlist.html",
		height : bodyHeight - 195,
		columns : columns,
		striped : true,
		pagination : true,
		rownumbers : true,
		singleSelect: true,
		toolbar : operateBtns
	});

	//查询按钮
	$('#searchBtn').click(function(){
		queryToGrid("tdkTable", "list_search");
	});
	
	//重置查询条件
	$('#resetBtn').click(function(){
		$('#list_search').form('clear').form('reset');
	});

	$('#saveBtn').click(function() {
		var url=path + '/seotdk/save.html';
		if ($("#tdkForm input[name='id']").val()!='') {
			url=path + '/seotdk/modify.html';
		}
		
		$('#tdkForm').form('submit', {
			url : url,
			success : function(data) {
				var obj = $.parseJSON(data);
				if (obj.success) {
					$("#tdkFormDiv").dialog('close');
					queryToGrid("tdkTable", "list_search");
				}
			}
		});
	});

	// 添加框取消按钮
	$("#closeBtn").click(function() {
		$("#tdkFormDiv").dialog('close');
	});
});

// 显示添加字典窗口
function addWin() {
	showWin();
}

function showWin(){
	$("#tdkFormDiv").show().dialog({
		title: "添加",
		height: 380,
		width: 500,
		modal : true,
		onClose: function () {
            $("#tdkForm").form('clear').form('reset');
        }
	});
}

// 显示修改字典窗口
function editWin(id) {
	$('#tdkForm').form('load', path + '/seotdk/getById.html?id=' + id);
	showWin();
}

/**
 * 删除数据字典
 */
function removeRow(id) {
	// 删除
	$.messager.confirm('提示', '您确定要删除已选的信息吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + '/seotdk/remove.html',
				data : {
					'id' : id
				},
				success : function() {
					queryToGrid("tdkTable", "list_search");
				}
			});
		}
	});
}