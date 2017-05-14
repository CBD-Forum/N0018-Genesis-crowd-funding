$(function(){
	//获取字典数据
	var columns = [[
					{field:'displayName',title:'名称',width:200,sortable:true},
					{field:'code',title:'值',width:300,sortable:true,formatter:function(val,row,index){
						if (row.displayName=='email_password' || row.displayName == 'ftp_password' 
							|| row.displayName == 'sms_1_password' || row.displayName == 'sms_2_password') {
							return '******';
						}else{
							return val;
						}
					}},
					{field:'description',title:'描述',align:'center'},
					{field:'operat',title:'操作',width:120,align:'center',formatter:operateData}
				]];
	$('#sysConfigTable').datagrid({
		url: path + "/sysconfig/getlist.html",
		columns: columns,
		pagination: true,
		height : bodyHeight - 192,
		rownumbers : true,
		striped : true,
		singleSelect: true,
		toolbar:operateBtns
	});
	
	//查询按钮
	$('#searchBtn').click(function(){
		queryToGrid("sysConfigTable", "list_search");
	});
	
	//重置查询条件
	$('#resetBtn').click(function(){
		$('#list_search').form('clear');
	});
  
	//关闭添加窗口
	$('#closeBtn').click(function(){
		$("#add").show().dialog('close');
	});
	
	
	$("#sysConfigForm").validate({
		rules : {
			displayName : {
				required: true,
                maxlength:32
			},
			code : {
				required: true,
                maxlength:230
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
        	var url=path + '/sysconfig/save.html';
    		if ($('#sysConfigForm input[name="id"]').val()!='') {
    			url=path + '/sysconfig/modify.html';
    		}
    		$('#sysConfigForm').form('submit', {
    			url : url,
    			success : function(data) {
    				var obj = $.parseJSON(data);
    				if (obj.success) {
    					$("#add").dialog('close');
    					queryToGrid("sysConfigTable", "list_search");
    				}
    			}
    		});
        }
    });
	
	// 添加
	$('#saveBtn').click(function() {
		$('#sysConfigForm').submit();
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
            $("#sysConfigForm").form('clear');
        }
	});
}


//显示修改字典窗口
function editWin(id) {
	$('#sysConfigForm').form('clear');
	$('#sysConfigForm').form('load', path + '/sysconfig/getById.html?id=' + id);
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
 * 删除
 */
function removeRow(id) {
	// 删除
	$.messager.confirm('提示', '您确定要删除已选的信息吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + '/sysconfig/remove.html',
				data : {
					'id' : id
				},
				success : function() {
					queryToGrid("sysConfigTable", "list_search");
				},
				complete: function(XHR, TS){
					if ('error'==TS && '403'== XHR.status) {
						$.messager.alert('提示', '您没有删除数据的权限','error');
					}
				}
			});
		}
	});
}

/**
 * 重新加载缓存
 */
function reloadCache() {
	$.ajax({
		type : "POST",
		url : path + '/sysconfig/reloadCache.html',
		data : {},
		success : function(data) {
			if (data) {
				$.messager.alert('提示', "加载成功，包含：系统配置、后台LOGO配置、第三方接口配置。");
			}else{
				$.messager.alert('提示', "加载失败");
			}
		},
		complete: function(XHR, TS){
			if ('error'==TS && '403'== XHR.status) {
				$.messager.alert('提示', '您没有删除数据的权限','error');
			}
		}
	});
}