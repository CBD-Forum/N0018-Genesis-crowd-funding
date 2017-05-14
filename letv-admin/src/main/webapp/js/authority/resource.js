//顶级树节点的pid
var pid = 0;
var parents = [];
$(function(){
	$(".resourceDiv").height(bodyHeight-160);
	$('#resource').height(bodyHeight-160);
	$('#resource').tree({
		url : path + '/auth/permission/getPermissionsByPid.html?pid=' + pid,
		animate:true,
		dnd:true,
		onBeforeDrop : function (targetNode, source, point) {
			 var target = $('#resource').tree('getNode', targetNode);
		     if (target.attributes.pid != source.attributes.pid || point === 'append') {
				return false;
			}else{
				return true;
			}
	    },
		onDrop: function(targetNode, source, point){
	       var target = $('#resource').tree('getNode', targetNode);
//	       alert(target.text); 
//	       alert(source.text);
//	       alert(point);
//	       return;
//	       alert('target='+target.text+'*****source='+source.text+'*****point='+point);
//	       return;
	       $.ajax({
	   		url: path + '/auth/permission/orderBy.html',
	   		type: "post",
	   		async: false,
	   		dataType: "json",
	   		data: {
	   			'targetId':target.id,
	   			'sourceId':source.id,
	   			'point':point,
	   			
	   		},
	   		success: function(data){
	   			
	   		},
	   		error: function(){
	   			console.log("删除信息异常");
	   		}
	   	});
	       
	    },
		onContextMenu: function(e,node){
			e.preventDefault();
			$(this).tree('select',node.target);
			$('#mm').menu('show',{
				left: e.pageX,
				top: e.pageY
			});
			
			$('#sysTypeId').val(node.attributes.sysType);
			
		},
		onClick : function(node) {
			$('#uuid').val(node.id);
		},
		onBeforeExpand : function(node, param) {
			$('#resource').tree('options').url = path
					+ '/auth/permission/getPermissionsByPid.html?pid=' + node.id;
		},
		onExpand : function(node, param) {
			if (parents.length>0) {
				var index =parents.indexOf(node.id);
				if (index<parents.length-1) {
					$('#resource').tree('expand',$('#resource').tree('find',parents[index+1]).target);
				}else{
					if ($('#uuid').val() && $('#uuid').val()!='') {
						$('#resource').tree('select',$('#resource').tree('find',$('#uuid').val()).target);
					}
				}
			}
		},
		//数据加载成功后，展开第一级树节点
		onLoadSuccess : function(node, data){
			if (!node) {
				$('#resource').tree('expandAll');
			}
			
		}
	});
	
	$('#saveBtn').click(function(){
		var node = $("#resource").tree("getSelected");
		var parentNode = $('#resource').tree('getParent',node.target);
		parents = [];
		if (node.id!=1 && !$('#resource').tree('isLeaf',node.target)) {
			parents.push(node.id);
		}
		if (parentNode.id!=1) {
			parents.push(parentNode.id);
		}
		while (parentNode.id!=1) {
			parentNode = $('#resource').tree('getParent',parentNode.target);
			parents.push(parentNode.id);
		}
		parents.reverse();

		var url=path + '/auth/permission/save.html';
		if ($('#uuid').val()) {
			url=path + '/auth/permission/modify.html';
		}
		$('#permissionForm').form('submit', {
			url : url,
			queryParams: {
				"sysType":node.attributes.sysType
			},
			success : function(data) {
				var obj =$.parseJSON(data);
				if (obj.success) {
					queryToTree();
					$("#add").show().dialog('close');
					$('#uuid').val(obj.id);
				}
			},
			error:function(){
				
			}
		});
	});
	
	//点击取消按钮
	$('#closeBtn').click(function(){
		$("#add").show().dialog('close');
	});
	
});

// 添加子节点显示窗口
function addPermission(){
	//清空赋值
	$('#uuid').val('');
	var node = $("#resource").tree("getSelected");
	$('#pId').val(node.id);
	$('#pName').val(node.text);
	$("#add").show().dialog({
		title: "添加",
		height: 500,
		width: 500,
		modal : true,
		onClose: function () {
			$('#permissionForm').form('clear').form('reset');
        }
	});
}

//修改节点显示窗口
function modifyPermission(){
	var node = $("#resource").tree("getSelected");
	$('#permissionForm').form('load',node.attributes);
	var parentNode=$('#resource').tree('getParent',node.target);
	$('#pId').val(parentNode.id);
	$('#pName').val(parentNode.text);
	$("#add").show().dialog({
		title: "修改",
		height: 500,
		width: 500,
		modal : true,
		onClose: function () {
			$('#permissionForm').form('clear').form('reset');
        }
	});
}

//刷新树节点
function queryToTree(){
	$('#resource').tree('options').url = path+ '/auth/permission/getPermissionsByPid.html?pid=' + pid;
	$("#resource").tree("reload");
}

//删除权限
function removePermission(){
	$.messager.confirm('提示', '删除此节点会连同该节点下的所有权限<br/>您确定要删除已选的节点吗？', function(r) {
		if (r) {
			var node = $("#resource").tree("getSelected");
			$.ajax({
				url: path + '/auth/permission/remove.html',
				type: "post",
				dataType: "json",
				data: {
					'id':node.id
				},
				success: function(data){
					if(data["success"]){
						parents = [];
//						if (node.id!=1 && !$('#resource').tree('isLeaf',node.target)) {
//							parents.push(node.id);
//						}
						var parentNode = $('#resource').tree('getParent',node.target);
						if (parentNode.id!=1) {
							parents.push(parentNode.id);
						}
						while (parentNode.id!=1) {
							parentNode = $('#resource').tree('getParent',parentNode.target);
							parents.push(parentNode.id);
						}
						parents.reverse();
						$('#uuid').val('');
						queryToTree();
					}else{
						$.messager.alert('提示','后台代码出错');
					}
				},
				error: function(){
					console.log("删除信息异常");
				}
			});
		}
	});
}