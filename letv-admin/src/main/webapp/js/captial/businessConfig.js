$(function(){
	//获取字典数据
	var columns = [[
	                {field:'id',title:'id',width:0,hidden:true},
					{field:'displayName',title:'名称',width:200,align:'center',sortable:true},
					{field:'code',title:'编码',width:200,align:'center',sortable:true},
					{field:'description',title:'描述',width:280,align:'center'},
					{field:'operat',title:'操作',width:120,align:'center',formatter:operateData}
				]];
	$('#bussConfigTable').datagrid({
		url: path + "/businessconfig/getlist.html",
		columns: columns,
		height : bodyHeight - 192,
		pagination: true,
		rownumbers : true,
		striped : true,
		singleSelect: true,
		toolbar: operateBtns
	});
	
	//查询按钮
	$('#searchBtn').click(function(){
		queryToGrid("bussConfigTable", "list_search");
	});
	
	//重置查询条件
	$('#resetBtn').click(function(){
		$('#list_search').form('clear');
	});
  
	$('#closeBtn').click(function(){
		$("#add").show().dialog('close');
	});
	
	
	$("#bussConfigForm").validate({
		rules : {
			displayName : {
				required: true,
                maxlength:32
			},
			code : {
				required: true,
                maxlength:32
			}
		},
		messages : {
			displayName : {
				required : "请填写名称"
			},
			code : {
				required : "请填写编码"
			}
		},				
        submitHandler:function(form){
        	var url=path + '/businessconfig/save.html';
    		if ($('#bussConfigForm input[name="id"]').val()!='') {
    			url=path + '/businessconfig/modify.html';
    		}
    		$('#bussConfigForm').form('submit', {
    			url : url,
    			success : function(data) {
    				var obj = $.parseJSON(data);
    				if (obj.success) {
    					$("#add").show().dialog('close');
    					queryToGrid("bussConfigTable", "list_search");
    				}else{
    					$.messager.alert('提示', obj.msg);
    				}
    			}
    		});
        }
    });
	
	
	
	// 添加字典
	$('#saveBtn').click(function() {
		$('#bussConfigForm').submit();
	});
});
//显示添加字典窗口
function showAdd() {
	$("#add").show().dialog({
		title: "添加",
		height: 300,
		width: 500,
		modal : true,
		onClose: function () {
            $("#bussConfigForm").form('clear');
        }
	});
}


//显示修改字典窗口
function editWin(id) {
	$('#bussConfigForm').form('clear');
	$('#bussConfigForm').form('load', path + '/businessconfig/getById.html?id=' + id);
	$("#add").show().dialog({
		title: "修改",
		height: 300,
		width: 500,
		modal : true,
		onClose: function () {
            $("#sysConfigForm").form('clear');
        }
	});
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
				url : path + '/businessconfig/remove.html',
				data : {
					'id' : id
				},
				success : function() {
					queryToGrid("bussConfigTable", "list_search");
				}
			});
		}
	});
}