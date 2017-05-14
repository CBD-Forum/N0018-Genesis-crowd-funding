var sendUser;
var ue;
$(function(){
	//获取字典数据
	var columns = [[
					{field:'userId',title:'用户名',width:150,align:'center',sortable:true},
					{field:'realName',title:'真实姓名',width:150,align:'center',sortable:true},
					{field:'mobile',title:'手机号',align:'center'},
					{field:'email',title:'邮箱',align:'center'},
					{field:'createTime',title:'注册时间',align:'center'},
					{field:'statusName',title:'状态',width:50,align:'center'}
				]];
	
	var SMSBtn = { 
			type:'menubutton',
	     	text: '短信推送', 
	        iconCls: 'icon-add', 
	        menu:'#mmSMS',
	        handler: function() { 
	        } 
	      };
	var emailBtn = {
			type:'menubutton',
	     	text: '邮件推送', 
	        iconCls: 'icon-add', 
	        menu:'#mmEmail',
	        handler: function() { 
	        }  
	      };
	var stationBtn = {
			type:'menubutton',
	     	text: '系统消息推送', 
	        iconCls: 'icon-add', 
	        menu:'#mmStation',
	        handler: function() { 
	        }  
	      };
	var operateBtns=[];
	
	operateBtns.push(SMSBtn);
	operateBtns.push(emailBtn);
	operateBtns.push(stationBtn);
	if (operateBtns.length==0) {
		operateBtns=null;
	}
	
	$('#userTable').datagrid({
		url: path + "/user/getlist.html",
		columns: columns,
		rownumbers : true,
		singleSelect: true,
		height:bodyHeight-265,
		pagination: true,
		toolbar:operateBtns
	});
	
	ue = UE.getEditor('emailContent');
	
	//查询按钮事件
    $("#searchBtn").click(function(){
    	queryToGrid("userTable", "list_search");
    });
    
    //清空查询条件内容
    $('#reset').click(function(){
    	$('#list_search').form('reset');
    });
    
    //关闭发送短信窗口
    $('#sendSMSCloseBtn').click(function(){
    	$("#sendSMS").dialog('close');
    });
    
    //关闭发送邮件窗口
    $('#sendEmailCloseBtn').click(function(){
    	$('#sendEmail').dialog('close');
    });
    //关闭发送站内信窗口
    $('#sendStationCloseBtn').click(function(){
    	$('#sendStation').dialog('close');
    });
    
    //发送短信
    $('#sendSMSBtn').click(function(){
    	var param={};
    	if (sendUser == 'select') {
    		param = serializeObject($('#list_search'));
		}
    	if (sendUser == 'one') {
    		var row = $('#userTable').datagrid('getSelected');
    		param.userId=row.userId;
    	}
	   $('#sendSMSForm').form('submit', {
			url : path+'/user/sendSMS.html',
			queryParams: param,
			success : function(data) {
				var obj = $.parseJSON(data);
				if (obj.success) {
					$("#sendSMS").dialog('close');
				}
				if (!obj.success) {
					if (obj.msg) {
						$.messager.alert('提示', obj.msg);
					}
				}
			}
		});
    });
    
    
  //发送邮件
   $('#sendEmailBtn').click(function(){
    	var param={};
    	if (sendUser == 'select') {
    		param = serializeObject($('#list_search'));
		}
    	if (sendUser == 'one') {
    		var row = $('#userTable').datagrid('getSelected');
    		param.userId=row.userId;
    	}
    	param.emailContent=ue.getContent();
	   $('#sendEmailForm').form('submit', {
			url : path+'/user/sendEmail.html',
			queryParams: param,
			success : function(data) {
				var obj = $.parseJSON(data);
				if (obj.success) {
					$("#sendEmail").dialog('close');
				}
				if (!obj.success) {
					if (obj.msg) {
						$.messager.alert('提示', obj.msg);
					}
				}
			}
		});
    });
   
 //发送站内信
   $('#sendStationBtn').unbind("click").click(function(){
		var sendLoanMask=null;
	   	var param={};
	   	if (sendUser == 'select') {
	   		param = serializeObject($('#list_search'));
			}
	   	if (sendUser == 'one') {
	   		var row = $('#userTable').datagrid('getSelected');
	   		param.userId=row.userId;
	   	}
	   	if($("#StationContent").val()==""){
	   		$.messager.alert('提示',"请输入站内信内容");
	   		return false;
	   	}
	   	if($("#StationContent").val().length >60){
	   		$.messager.alert('提示','站内信内容不能超过60个字！');
	   		return false;
	   	}
	   	
	   	$.messager.confirm('提示', '您确定要发送站内信吗？', function(r) {
		   if(r){
			   $('#sendStationForm').form('submit', {
					url : path+'/user/sendStation.html',
					queryParams: param,
					success : function(data) {
						var obj = $.parseJSON(data);
						if (obj.success) {
							$("#sendStation").dialog('close');
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
   });
});


function showSendSMSWin(user) {
	sendUser = user;
	if (sendUser == 'one') {
		var row = $('#userTable').datagrid('getSelected');
		if (!row) {
			$.messager.alert('提示', '请选择要发送的用户');
			return;
		}
		if (!row.mobile) {
			$.messager.alert('提示', '该用户没有配置手机号码，无法发送。');
			return;
		}
	}
	$("#sendSMS").show().dialog({
		title: "短信推送",
		height: 250,
		width: 500,
		modal : true,
		onClose: function () {
            $("#sendSMSForm").form('clear').form('reset');
        }
	});
}

function showSendEmailWin(user) {
	sendUser = user;
	if (sendUser == 'one') {
		var row = $('#userTable').datagrid('getSelected');
		if (!row) {
			$.messager.alert('提示', '请选择要发送的用户');
			return;
		}
		if (!row.email) {
			$.messager.alert('提示', '该用户没有邮箱，无法发送。');
			return;
		}
	}
	$("#sendEmail").show().dialog({
		title: "邮件推送",
		height: 570,
		width: 900,
		modal : true,
		onClose: function () {
			$("#sendEmailForm").form('clear').form('reset');
		}
	});
}

function showSendStationWin(user) {
	sendUser = user;
	if (sendUser == 'one') {
		var row = $('#userTable').datagrid('getSelected');
		if (!row) {
			$.messager.alert('提示', '请选择要发送的用户');
			return;
		}
	}
	$("#sendStation").show().dialog({
		title: "站内信推送",
		height: 300,
		width: 500,
		modal : true,
		onClose: function () {
			$("#sendStationForm").form('clear').form('reset');
		}
	});
}