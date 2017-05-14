$(function(){
	
	$.ajax({
		type : "POST",
		url : path + '/user/selectAllUserList.html',
		success : function(data) {
			$('#mobile').combobox({
				data : data,
				valueField:'id',
				textField:'mobile' 
				 
			});
		}
	});
	
	$("#mobile").combobox({
		onChange: function (n,o) {
			 $("#user_id").val("");
			 $("#nickName").val("");
			 $("#realName").val("");
			 $.ajax({
				type : "POST",
				url : path + '/user/getById.html',
				data:{
					"id":n
				},
				success : function(data) {
					 if(data){
						 $("#nickName").val(data.nickName);
						 $("#realName").val(data.realName);
						 $("#user_id").val(data.userId);
						 
					 }
				}
			}); 
		}
	});
	
	
	//获取字典数据
	var columns = [[
	                {field:'operat',title:'操作',width:120,align:'center',formatter:operateData},
	                {field:'id',title:'id',width:0,hidden:true},
	                {field:'orderId',title:'调账单号',width:120,align:'center'},
					{field:'userId',title:'用户名',width:100,align:'center'},
					{field:'nickName',title:'用户昵称',width:100,align:'center'},
					{field:'realName',title:'用户姓名',width:100,align:'center'},
					{field:'mobile',title:'手机号',width:100,align:'center'},
					{field:'adjustAmt',title:'调账金额',width:100,align:'center'},
					{field:'adjustTypeText',title:'调账类型',width:100,align:'center'},
					{field:'operator',title:'操作人',width:100,align:'center'},
					{field:'applyTime',title:'申请时间',width:200,align:'center'},
					{field:'adjustReason',title:'调账原因',width:200,align:'center'},
					{field:'auditor',title:'审核人',width:100,align:'center'},
					{field:'auditOpinion',title:'审核意见',width:200,align:'center'},
					{field:'auditTime',title:'审核时间',width:200,align:'center'},
					{field:'auditStateText',title:'状态',width:100,align:'center'}
					
				]];
	$('#adjustAccountTable').datagrid({
		url: path + "/adjustAccount/getPageList.html",
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
		queryToGrid("adjustAccountTable", "list_search");
	});
	
	//重置查询条件
	$('#resetBtn').click(function(){
		$('#list_search').form('clear');
	});
  
	$('#closeBtn').click(function(){
		$("#add").show().dialog('close');
	});
	$('#close1Btn').click(function(){
		$("#auditWin").show().dialog('close');
	});	
	
	$("#adjustAccountForm").validate({
		rules : {
			adjustAmt : {
				required: true,
				number : true
			},
			adjustReason : {
				required: true,
                maxlength:32
			}
		},
		messages : {
			adjustAmt : {
				required : "请填写调账金额"
			},
			adjustReason : {
				required : "请填写调账原因"
			}
		},				
        submitHandler:function(form){
        	var url=path + '/adjustAccount/submitAdjustApply.html';
    		$('#adjustAccountForm').form('submit', {
    			url : url,
    			success : function(data) {
    				var obj = $.parseJSON(data);
    				if (obj.success) {
    					$("#add").show().dialog('close');
    					queryToGrid("adjustAccountTable", "list_search");
    				}else{
    					$.messager.alert('提示', obj.msg);
    				}
    			}
    		});
        }
    });
	// 添加字典
	$('#saveBtn').click(function() {
		
		var mobile = $('#mobile').combobox('getValue');
		if(!mobile) {
			$.messager.alert('提示', "请选择要调账的用户手机号码!");
			return false;
		}
		var userId = $("#user_id").val();
		if(!userId){
			$.messager.alert('提示', "用户不存在,请填写正确的手机号码!");
			return false;
		}
		
		
		var adjustType = $('#adjustType').combobox('getValue');
		if(!adjustType) {
			$.messager.alert('提示', "请选择调账类型!");
			return false;
		}
		$('#adjustAccountForm').submit();
	});
	
	
	$('#passedBtn').click(function() {
		$("#audit_state").val("passed");
		$('#adjustAccountAuditForm').submit();
	});	
	$('#refuseBtn').click(function() {
		$("#audit_state").val("refuse");
		$('#adjustAccountAuditForm').submit();
	});	
	
	$("#adjustAccountAuditForm").validate({
		rules : {
			auditOpinion : {
				required: true 
			} 
		},
		messages : {
			auditOpinion : {
				required : "请填写审核意见！"
			} 
		},				
        submitHandler:function(form){
        	var audit_state = $("#audit_state").val();
        	var url= "";
        	if("passed"==audit_state){
        		url = path + '/adjustAccount/passAdjustApply.html';
        	}else if("refuse" ==audit_state){
        		url = path + '/adjustAccount/refuseAdjustApply.html';
        	}
    		$('#adjustAccountAuditForm').form('submit', {
    			url : url,
    			success : function(data) {
    				var obj = $.parseJSON(data);
    				if (obj.success) {
    					$("#auditWin").show().dialog('close');
    					queryToGrid("adjustAccountTable", "list_search");
    				}else{
    					$.messager.alert('提示', obj.msg);
    				}
    			}
    		});
        }
    });
	
	
	
});
//显示添加字典窗口
function showAdd() {
	$("#add").show().dialog({
		title: "调账",
		height: 450,
		width: 600,
		modal : true,
		onClose: function () {
            $("#adjustAccountForm").form('clear');
        }
	});
}


//显示修改字典窗口
function auditWin(id) {
	$('#adjustAccountAuditForm').form('clear');
	$("#audit_id").val(id);
	$("#auditWin").show().dialog({
		title: "调账审核",
		height: 300,
		width: 500,
		modal : true,
		onClose: function () {
            $("#adjustAccountAuditForm").form('clear');
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
					queryToGrid("adjustAccountTable", "list_search");
				}
			});
		}
	});
}