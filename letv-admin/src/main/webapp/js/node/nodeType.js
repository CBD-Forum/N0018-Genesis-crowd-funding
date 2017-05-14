$(function(){
	//获取字典数据
	var columns = [[
					{field:'code',title:'编码',width:150,align:'center',sortable:true},
					{field:'name',title:'名称',width:200,align:'center'},
					{field:'description',title:'描述',width:250,align:'center'},
					{field:'operat',title:'操作',width:120,align:'center',formatter:operateData}
				]];
	
	var addBtn = { 
	     	text: '添加', 
	        iconCls: 'icon-add', 
	        handler: function() { 
	        	showAdd();
	        } 
	      };
	var operateBtns=[];
	if ($('#content_type_add').length>0) {
		operateBtns.push(addBtn);
	}
	if (operateBtns.length==0) {
		operateBtns=null;
	}
	
	$('#nodeTypeTable').datagrid({
		url: path + "/node/getTypePage.html",
		columns: columns,
		singleSelect: true,
		height : bodyHeight - 200,
		rownumbers : true,
		toolbar: operateBtns
	});
	
	//查询按钮
	$('#searchBtn').click(function(){
		queryToGrid("nodeTypeTable", "list_search");
	});
	
	//重置
	$('#resetBtn').click(function(){
		$('#list_search').form('reset');
	});
	
	$("#nodeTypeForm").validate({
		rules : {
			code : "required",
			name : "required"
		},
		messages : {
			code : "请填写编码",
			name : "请填写名称"
		},				
        submitHandler:function(form){
        	var url=path + '/node/saveType.html';
    		if ($('#nodeTypeForm input[name="id"]').val()!='') {
    			url=path + '/node/modifyType.html';
    		}
    		$('#nodeTypeForm').form('submit', {
    			url : url,
    			success : function(data) {
    				var obj = $.parseJSON(data);
    				if (obj.success) {
    					$("#add").show().dialog('close');
    					queryToGrid("nodeTypeTable", "list_search");
    				}else{
    					$.messager.alert('提示', obj.msg);
    				}
    			}
    		});
        }
    });
	
	
	
	// 添加字典
	$('#saveBtn').click(function() {
		$('#nodeTypeForm').submit();
	});
	
	//关闭添加窗口
	$('#closeBtn').click(function(){
		$("#add").dialog('close');
	});
	
	
});

//显示添加字典窗口
function showAdd() {
	$('#code').removeAttr('disabled');
	$("#add").show().dialog({
		title: "添加",
		height: 300,
		width: 500,
		modal : true,
		onClose: function () {
            $("#nodeTypeForm").form('clear').form('reset');
        }
	});
}

function editWin(id) {
	$('#code').attr('disabled','disabled');
	$("#nodeTypeForm input[name='id']").val(id);
	$('#nodeTypeForm').form('load', path + '/node/getTypeById.html?id=' + id);
	$("#add").show().dialog({
		title: "修改",
		height: 300,
		width: 500,
		modal : true,
		onClose: function () {
            $("#nodeTypeForm").form('clear').form('reset');
        }
	});
}

function removeRow(id) {
	// 删除
	$.messager.confirm('提示', '您确定要删除已选的信息吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + '/node/removeType.html',
				data : {
					'id' : id
				},
				success : function() {
					queryToGrid("nodeTypeTable", "list_search");
				}
			});
		}
	});
}