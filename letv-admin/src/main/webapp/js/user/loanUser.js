$(function(){
	//获取字典数据
	var columns = [[
	                {field:'view',title:'操作',width:200,align:'center',formatter:operateData},
					{field:'userId',title:'用户名',width:150,align:'center',sortable:true},
					{field:'realName',title:'真实姓名',width:150,align:'center',sortable:true},
//					{field:'thirdAccount',title:'汇付账号',width:150,align:'center',sortable:true},
					{field:'mobile',title:'手机号',align:'center'},
					{field:'email',title:'邮箱',width:230,align:'center'},
					{field:'userLevel',title:'用户等级',width:100,align:'center',sortable:true},
					{field:'createTime',title:'注册时间',width:150,align:'center',sortable:true},
					{field:'statusName',title:'状态',width:50,align:'center',sortable:true}
				]];
	$('#userTable').datagrid({
		url: path + "/user/getlist.html?isBorrower=1",
		columns: columns,
		rownumbers : true,
		singleSelect: true,
		height:bodyHeight-230,
		pagination: true
	});
	
	//查询按钮事件
    $("#searchBtn").click(function(){
    	queryToGrid("userTable", "list_search");
    });
});

/**
 * 修改状态
 * @param userId
 * @param status
 */
function modifySecurity(userId,status){
	var msg ='';
	if (status=='disable') {
		msg='您确定要禁用&nbsp;'+userId+'&nbsp;吗?';
	}else{
		msg='您确定要启用&nbsp;'+userId+'&nbsp;吗?';
	}
	$.messager.confirm('提示', msg, function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + '/user/modifySecurity.html',
				data : {
					'userId' : userId,
					'userType' :'P',
					'status' : status
				},
				success : function() {
					queryToGrid("userTable", "list_search");
				}
			});
		}
	});
}

function showUserDetail(userId) {
	$.ajax({
		type : "POST",
		url : path + '/user/getLoanUserDetail.html',
		dataType:'json',
		data : {
			'userId' : userId
		},
		success : function(data) {
			$("#userDetail #user label").each(function(){
				$(this).text('');
				$(this).text(data.user[this.id]);
			});
			
			$("#userDetail #user img").each(function(){
				if (data.user[this.id] && data.user[this.id] !='') {
					$(this).attr('src',''+fileUrl+data.user[this.id]);
				}
			});
			
			$("#userDetail #detail label").each(function(){
				$(this).text('');
				$(this).text(data.detail[this.id]);
			});
			$("#userDetail #authFile label").each(function(){
				$(this).text('');
				$(this).text(data.authFile[this.id]);
			});
			
			$("#userDetail #authFile img").each(function(){
				if (data.authFile[this.id] && data.authFile[this.id] !='') {
					$(this).attr('src',''+fileUrl+data.authFile[this.id]);
				}
			});
			
			$("#userDetail #workFinancial label").each(function(){
				$(this).text('');
				$(this).text(data.workFinancial[this.id]);
			});
		}
	});
	
	$("#userDetail").show().dialog({
		title: "用户详情",
		height: 600,
		width: 800,
		modal : true,
		onClose: function () {
//            $("#dicForm").form('clear');
        }
	});
}


//重置密码
function resetPassword(id){
	// 删除
	$.messager.confirm('提示', '初始化密码为本人身份证号码的后6位，<br/>您确定要重置吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + '/user/resetPassword.html',
				data : {
					'id' : id
				},
				success : function() {
					queryToGrid("userTable", "list_search");
				}
			});
		}
	});
}