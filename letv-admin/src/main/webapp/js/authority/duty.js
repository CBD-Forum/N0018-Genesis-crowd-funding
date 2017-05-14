//顶级树节点的pid
var pid = 0;
$(function(){
	//获取字典数据
	var columns = [[
	                {field:'id',title:'id',width:0,hidden:true},
					{field:'code',title:'角色编码',width:200,align:'center',sortable:true},
					{field:'name',title:'角色名称',width:200,align:'center',sortable:true},
					{field:'description',title:'描述',width:200,align:'center'},
					{field:'operat',title:'操作',width:150,align:'center',formatter:operateData}
				]];
	
	//操作按钮
	var add = { 
		     	text: '添加', 
		        iconCls: 'icon-add', 
		        handler: function() { 
		        	addWin();
		        } 
		      };
	
	var operateBtns=[];
	if ($('#authority_role_add').length>0) {
		operateBtns.push(add);
	}
	if (operateBtns.length==0) {
		operateBtns=null;
	}
	
	$('#duty').datagrid({
		url: path + "/auth/role/getList.html",
		columns: columns,
		striped : true,
		pagination: true,
		height : bodyHeight - 200,
		rownumbers:true,
		singleSelect: true,
		toolbar: operateBtns
	});
	
	//查询按钮
	$('#searchBtn').click(function(){
		queryToGrid("duty", "list_search");
	});
	
	//重置查询条件
	$('#resetBtn').click(function(){
		$('#list_search').form('reset');
	});
	
	$('#closeBtn').click(function(){
		$("#add").dialog('close');
	});
	

		$("#roleForm").validate({
		rules : {
			code : {
				required: true,
                maxlength:30
			},
			name : {
				required: true,
                maxlength:30
			}
		},
		messages : {
			code : {
				required: "请填写编码"
			},
			name :  {
				required: "请填写名称"
			}
		},
		submitHandler : function(form) {
			var url = path + '/auth/role/save.html';
			if ($('#uuid').val()) {
				url = path + '/auth/role/modify.html';
			}
			$('#roleForm').form('submit', {
				url : url,
				dataType : 'json',
				success : function(data) {
					var obj = $.parseJSON(data);
					if (obj.success) {
						$("#add").show().dialog('close');
						queryToGrid("duty", "list_search");
					}
				},
				error : function() {

				}
			});
		}
	});
	
	$('#saveBtn').click(function(){
		$('#roleForm').submit();
	});
	
});

// 显示添加窗口
function addWin() {
	//隐藏tree的div，显示form的div
	$('#tree').hide();
	$('#form').show();
	// 修改win的窗口标题
	$('#roleForm').form('clear').form('reset');
	$("#add").show().dialog({
		title: "添加",
		height: 300,
		width: 500,
		modal : true,
		onClose: function () {
//            $("#dicForm").form('clear');
        }
	});
}

//显示修改窗口
function editWin(id) {
	//隐藏tree的div，显示form的div
	$('#tree').hide();
	$('#form').show();
	$('#roleForm').form('clear');
	$('#roleForm').form('load', path + '/auth/role/getById.html?id=' + id);
	$("#add").show().dialog({
		title: "修改",
		height: 300,
		width: 500,
		modal : true,
		onClose: function () {
//            $("#dicForm").form('clear');
        }
	});
}

/**
 * 删除数据字典
 */
function removeRow(id) {
	// 删除
	$.messager.confirm('提示', '删除角色会连同删除角色下的所有权限<br/>您确定要删除已选的角色吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + '/auth/role/remove.html',
				data : {
					'id' : id
				},
				success : function() {
					queryToGrid("duty", "list_search");
				}
			});
		}
	});
}

//显示编辑权限窗口
function editPermission(roleId){
	//显示tree的div，隐藏form的div
	$('#tree').show();
	$('#form').hide();
	
	$("#add").show().dialog({
		title: "编辑权限",
		height: 500,
		width: 400,
		modal : true,
		onClose: function () {
//            $("#dicForm").form('clear');
        }
	});
	
	$('#resource').html('');
	$('#resource').tree({
		url : path + '/auth/role/getPermission.html?roleId='+roleId+'&pid=' + pid,
		checkbox:true,
		animate:true,
		onClick : function(node) {
		},
		onBeforeExpand : function(node, param) {
			$('#resource').tree('options').url = path
					+ '/auth/role/getPermission.html?roleId='+roleId+'&pid=' + node.id;
		},
		//数据加载成功后，展开第一级树节点
		onLoadSuccess : function(node, data){
//			if (!node) {
				$('#resource').tree('expandAll');
//			}
		}
	});
	
	//点击确定
	$('#addPermission4RoleBtn'). unbind ('click');
	$('#addPermission4RoleBtn').click(function(){
		//选中的节点
		var checkNodes=$('#resource').tree('getChecked',['checked','indeterminate']);
		//选中的权限Id
		var permissionIds=[];
		var permissionCheckeds=[];
		for ( var i = 0; i < checkNodes.length; i++) {
			permissionIds.push(checkNodes[i].id);
			permissionCheckeds.push(checkNodes[i].checked);
		}
		//添加权限
		$.ajax({
			url: path + '/auth/role/addPermission.html',
			type: "post",
			dataType: "json",
			data: {
				'roleId':roleId,
				'permissionId':permissionIds.join(','),
				'permissionChecked':permissionCheckeds.join(',')
			},
			success: function(data){
				if(data["success"]){
					$("#add").show().dialog('close');
				}
			},
			error: function(){
				
			}
		});
	});
}