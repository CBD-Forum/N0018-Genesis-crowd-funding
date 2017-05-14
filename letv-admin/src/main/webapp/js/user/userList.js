$(function(){
	//获取字典数据
	var columns = [[
	                {field:'view',title:'操作',width:200,align:'center',formatter:operateData},
					{field:'userId',title:'用户名',width:150,align:'center',sortable:true},
					{field:'realName',title:'真实姓名',width:150,align:'center',sortable:true},
//					{field:'thirdAccount',title:'汇付账号',width:150,align:'center',sortable:true},
					{field:'mobile',title:'手机号',align:'center'},
					{field:'email',title:'邮箱',align:'center'},
					{field:'userLevel',title:'用户等级',width:100,align:'center',sortable:true},
					{field:'createTime',title:'注册时间',width:150,align:'center',sortable:true},
					{field:'directRecommend',title:'直接推荐人',width:150,align:'center'},
					{field:'indirectRecommend',title:'间接推荐人',width:150,align:'center'},
					{field:'statusName',title:'状态',width:80,align:'center'}
				]];
	$('#userTable').datagrid({
		url: path + "/user/getlist.html",
		columns: columns,
		rownumbers : true,
		singleSelect: true,
		height:bodyHeight-230,
		pagination: true
	});
	
	//查询按钮事件
    $("#searchBtn").click(function(){
    	$.ajax({
			type : "POST",
			url : path + '/reconciliation/repay.html',
			success : function() {
				
			}
		});
    	
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

/**
 * 查看用户详细信息
 * @param userId
 */
function showUserDetail(userId) {
	$.ajax({
		type : "POST",
		url : path + '/user/getUserDetail.html',
		data : {
			'userId' : userId
		},
		success : function(data) {
			$("#userDetail label").each(function(){
				$(this).text('');
				$(this).text(data[this.id]);
				if(this.id == 'isBorrower'){
					$(this).text(data[this.id]=='0'?'不是':'是');
				}
				if(this.id == 'sex'){
					$(this).text(data[this.id]=='M'?'男':'女');
				}
				if(this.id == 'certificateType'){
					$(this).text(data[this.id]=='00'?'身份证':'其他');
				}
			});
			$("#userDetail img").each(function(){
				if(this.id == 'photo' && !data[this.id]){
					$(this).attr('src',fileUrl+'/userPhoto/default/defaultPhoto.jpg');
				}else{
					$(this).attr('src',''+fileUrl+data[this.id]);
				}
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