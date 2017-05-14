$(function() {
	$('#typeCode').combobox({
		url:path+'/dictionary/getAllType.html',
		valueField:"typeCode",
		textField:"typeCode"
	});
	
	// 获取字典数据
	var columns = [ [ {
		field : 'typeCode',
		title : '类型编码',
		width : 200,
		align : 'center',
		sortable : true
	}, {
		field : 'typeName',
		title : '类型名称',
		width : 200,
		align : 'center',
		sortable : true
	}, {
		field : 'code',
		title : '编码',
		width : 150,
		align : 'center',
		sortable : true
	}, {
		field : 'displayName',
		title : '名称',
		width : 150,
		align : 'center'
	}, {
		field : 'description',
		title : '描述',
		width : 150,
		align : 'center'
	}, {
		field : 'seqNum',
		title : '序号',
		width : 150,
		align : 'center'
	}, {
		field : 'operat',
		title : '操作',
		width : 120,
		align : 'center',
		formatter : operateData
	} ] ];
	
	var addBtn = { 
	     	text: '添加', 
	        iconCls: 'icon-add', 
	        handler: function() { 
	        	addWin();
	        } 
	      };
	var operateBtns=[];
	if ($('#system_dictionary_add').length>0) {
		operateBtns.push(addBtn);
	}
	if (operateBtns.length==0) {
		operateBtns=null;
	}

	$('#dictionary').datagrid({
		url : path + "/dictionary/getlist.html",
		height : bodyHeight - 230,
		columns : columns,
		striped : true,
		pagination : true,
		rownumbers : true,
		singleSelect: true,
		toolbar :operateBtns
	});
	
	//查询按钮
	$('#searchBtn').click(function(){
		queryToGrid("dictionary", "list_search");
	});
	
	//重置查询条件
	$('#resetBtn').click(function(){
		$('#list_search').form('clear');
	});
	
	
	$("#dicForm").validate({
		rules : {
			typeCode : {
				required: true,
                maxlength:64
			},
			typeName : {
				required: true,
                maxlength:64
			},
			code : {
				required: true,
                maxlength:64
			},
			displayName : {
				required: true,
                maxlength:64
			}
		},
		messages : {
			typeCode : {
				required: "请填写类型编码"
			},
			typeName : {
				required: "请填写类型名称"
			},
			code : {
				required: "请填写编码"
			},
			displayName : {
				required: "请填写名称"
			}
		},				
        submitHandler:function(form){
    		var url=path + '/dictionary/save.html';
    		if ($('#dicForm input[name="id"]').val()!='') {
    			url=path + '/dictionary/modify.html';
    		}
    		$('#dicForm').form('submit', {
    			url : url,
    			success : function(data) {
    				var obj = $.parseJSON(data);
    				if (obj.success) {
    					$("#edit").show().dialog('close');
    					queryToGrid("dictionary", "list_search");
    				}
    				if (!obj.success) {
    					if (obj.msg) {
    						$.messager.alert('提示', obj.msg);
    					}
    				}
    			}
    		});
    	
        }
    });

	$('#saveBtn').click(function() {
		$('#dicForm').submit();
	});

	// 添加框取消按钮
	$("#closeBtn").click(function() {
		$("#edit").dialog('close');
	});
});

// 显示添加字典窗口
function addWin() {
	$("#edit").show().dialog({
		title: "添加",
		height: 350,
		width: 500,
		modal : true,
		onClose: function () {
            $("#dicForm").form('clear').form('reset');
        }
	});
}

// 显示修改字典窗口
function editWin(id) {
	$('#dicForm').form('clear').form('reset');
	$('#dicForm').form('load', path + '/dictionary/getById.html?id=' + id);
	$("#edit").show().dialog({
		title: "修改",
		height: 350,
		width: 500,
		modal : true,
		onClose: function () {
            $("#dicForm").form('clear').form('reset');
        }
	});
}

/**
 * 删除数据字典
 */
function removeDic(id) {
	// 删除
	$.messager.confirm('提示', '您确定要删除已选的信息吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + '/dictionary/remove.html',
				data : {
					'id' : id
				},
				success : function() {
					queryToGrid("dictionary", "list_search");
				}
			});
		}
	});
}