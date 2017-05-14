$(function(){
	//获取字典数据
	var columns = [[
	                {field:'view',title:'操作',width:50,align:'center',formatter:operateData},
					{field:'code',title:'编号',width:270,align:'center',sortable:true},
					{field:'name',title:'名称',width:250,align:'center',sortable:true},
					{field:'status',title:'状态',width:60,align:'center',sortable:true},
					{field:'description',title:'参数描述',width:550}
				]];
	
	var addBtn = { 
	     	text: '添加', 
	        iconCls: 'icon-add', 
	        handler: function() { 
	        	addWin();
	        } 
	      };
	var operateBtns=[];
	if ($('#message_node_add').length>0) {
		operateBtns.push(addBtn);
	}
	if (operateBtns.length==0) {
		operateBtns=null;
	}
	
	$('#messageNodeTable').datagrid({
		url: path + "/message/getNodePage.html",
		columns: columns,
		rownumbers : true,
		height:bodyHeight-195,
		singleSelect: true,
		pagination: true,
		toolbar:operateBtns
	});
	
	$("#messageNodeForm").validate({
		rules : {
			name : {
				required: true,
                maxlength:30
			},
			code : {
				required: true,
                maxlength:30
			}
		},
		messages : {
			name : {
				required: "请填写名称"
			},
			code : {
				required: "请填写编码"
			}
		},				
        submitHandler:function(form){
        	var url=path + '/message/saveNode.html';
    		if ($('#messageNodeForm input[name="id"]').val()!='') {
    			url=path + '/message/modifyNode.html';
    		}
    		$('#messageNodeForm').form('submit', {
    			url : url,
    			success : function(data) {
    				var obj = $.parseJSON(data);
    				if (obj.success) {
    					$("#add").show().dialog('close');
    					queryToGrid("messageNodeTable", "list_search");
    				}
    			}
    		});
        }
    });
	
	// 添加
	$('#saveBtn').click(function() {
		$('#messageNodeForm').submit();
	});
	
	//查询按钮事件
    $("#searchBtn").click(function(){
    	queryToGrid("messageNodeTable", "list_search");
    });
    
    //关闭修改窗口
    $('#closeBtn').click(function(){
    	$("#add").dialog('close');
    });
});

function showWin(title){
	$("#add").show().dialog({
		title: title,
		height: 300,
		width: 500,
		modal : true,
		onClose: function () {
            $("#messageNodeForm").form('clear').form('reset');
        }
	});
}

//显示修改字典窗口
function editWin(id) {
	$('#messageNodeForm').form('clear').form('reset');
	$('#messageNodeForm').form('load', path + '/message/getNodeById.html?id=' + id);
	showWin("修改消息节点");
}

//显示修改字典窗口
function addWin() {
	showWin("添加消息节点");
}