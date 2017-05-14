$(function(){
	var columns = [[
	                {field:'view',title:'操作',width:230,align:'center',formatter:operateData},
					{field:'adminId',title:'用户名',width:150,align:'center',sortable:true},
					{field:'realName',title:'真实姓名',width:150,align:'center',sortable:true},
					{field:'deptText',title:'部门名称',width:150,align:'center',sortable:true},
					{field:'employeeNo',title:'员工号',width:150,align:'center',sortable:true},
					{field:'mobile',title:'手机号',align:'center',sortable:true},
					{field:'email',title:'邮箱',align:'center',sortable:true},
					{field:'createTime',title:'注册时间',align:'center',sortable:true},
					{field:'statusName',title:'状态',width:50,align:'center',sortable:true}
				]];
	
	var addBtn = { 
	     	text: '添加', 
	        iconCls: 'icon-add', 
	        handler: function() { 
	        	showAddAdmin();
	        } 
	      };
	var operateBtns=[];
	if ($('#user_admin_add').length>0) {
		operateBtns.push(addBtn);
	}
	if (operateBtns.length==0) {
		operateBtns=null;
	}
	
	$('#adminTable').datagrid({
		url: path + "/admin/getlist.html",
		columns: columns,
		rownumbers : true,
		singleSelect: true,
		sortName : 'createTime',
		sortOrder : 'desc',
		height:bodyHeight-263,
		pagination: true,
		toolbar:operateBtns
	});
	
	
	var roleColumns = [[
	                {field:'ck',checkbox:true },
					{field:'code',title:'角色编码',width:130,align:'center',sortable:true},
					{field:'name',title:'角色名称',width:130,align:'center',sortable:true},
					{field:'description',title:'描述',width:200,align:'center'},
					{field:'operat',title:'操作',width:100,align:'center'}
				]];
	$('#roleTable').datagrid({
		columns: roleColumns,
		striped : true,
		pagination: true,
		rownumbers:true,
		onLoadSuccess:function(data){
			var tableData = data;
			$.ajax({
	    		type : "POST",
	    		dataType : 'json',
	    		url : path + '/auth/user/getRole.html',
	    		data : {
	    			'userId' : $('#roleList input[name="userId"]').val()
	    		},
	    		success : function(data) {
	    			if(tableData){
	    				$.each(tableData.rows, function(index, item){
	    					var rowItem = item;
	    					var rowIndex = index;
	    					$.each(data,function(index,item){
	    						if (rowItem.id == item.id) {
	    							$('#roleTable').datagrid('checkRow', rowIndex);
								}
	    					});
	    				});
	    			}
	    		}
	    	});
		} 
	});
	
	//查询按钮事件
    $("#searchBtn").click(function(){
    	queryToGrid("adminTable", "list_search");
    });
    
    $('#resetBtn').click(function(){
    	$('#list_search').form('clear');
    });
    
    //分配岗位
    $('#saveRoleBtn').click(function(){
    	var checkedItems = $('#roleTable').datagrid('getChecked');
    	var names = [];
    	$.each(checkedItems, function(index, item){
    		names.push(item.id);
    	});
    	$.ajax({
    		type : "POST",
    		url : path + '/auth/user/addRole.html',
    		data : {
    			'userId' : $('#roleList input[name="userId"]').val(),
    			'roleId' :names.join(",")
    		},
    		success : function() {
    			$("#roleList").show().dialog('close');
    		}
    	});
    	
    });
    
    //添加窗口的关闭
    $('#closeBtn').click(function(){
    	$("#add").dialog('close');
    });
    
    
    $("#adminForm").validate({
    	errorPlacement : function(error, element) {
    		if (element.parent().hasClass("combo")){
    			error.appendTo(element.parent());
    		}else{
    			error.appendTo(element.parent());
    		}
    	},
		rules : {
			adminId : "required",
			password : "required",
			confirmPassword : {
				required:true,
				equalTo:'#password'
			},
			realName : "required",
			idCardNo : {
				required:true
			},
			employeeNo : "required",
			dept : {
				required:true
			},
			email : {
				required:true,
				email:true
			},
			mobile : {
				required:true,
				mobile:true
			}
		},
		messages : {
			adminId : "请填写用户名",
			password : "请填写密码",
			confirmPassword : {
				required:"请输入确认密码",
				equalTo:"两次输入密码不一致"
			},
			realName : "请填写真实姓名",
			idCardNo : {
				required:"请填写身份证号"
			},
			employeeNo : "请填写员工号",
			dept : {
				required:"请填写部门"
			},
			email : {
				required:"请填写邮箱"
			},
			mobile : {
				required:"请填写手机号码"
			}
		},
        submitHandler:function(form){
        	
        	var idCardNo = $('#adminForm input[name="idCardNo"]').val()
        	if(!validateIdCard(idCardNo)){
        		return false;
        	}
        	var adminId = $('#adminForm input[name="adminId"]').val()
        	if(/[\u4E00-\u9FA5]/i.test(adminId)){
        		$.messager.alert("提示","用户名输入不正确,不能为中文");
        		return false;
        	}
        	var url=path + '/admin/save.html';
        	var params = {};
    		if ($('#adminForm input[name="id"]').val()!='') {
    			url=path + '/admin/modify.html';
    			params.adminId = $("#adminForm input[name='adminId']").val();
    		}
    		$('#adminForm').form('submit', {
    			url : url,
    			queryParams: params,
    			success : function(data) {
    				var obj = $.parseJSON(data);
    				if (obj.success) {
    					$("#add").show().dialog('close');
    					queryToGrid("adminTable", "list_search");
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
		$('#adminForm').submit();
	});
    
    //关闭权岗位分配窗口
    $('#closeRoleBtn').click(function(){
    	$("#roleList").show().dialog('close');
    });
});

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
					'userType' :'A',
					'status' : status
				},
				success : function() {
					queryToGrid("adminTable", "list_search");
				}
			});
		}
	});
}

function showAddAdmin() {
	$("#adminForm input[name='adminId']").removeAttr('disabled');
	$("#add").show().dialog({
		title: "添加管理员",
		height: 450,
		width: 550,
		modal : true,
//		buttons : [{  
//            text : '保存',  
//            handler : function(){
//            	
//            }  
//        }, {  
//            text : '取消',  
//            handler : function(){
//            	
//            }    
//        }],
		onClose: function () {
           $('#adminForm').form('clear').form('reset');
        }
	});
}

//显示修改字典窗口
function editWin(id) {
	$('#password').parent().parent().remove();
	$('#confirmPassword').parent().parent().remove();
	$("#adminForm input[name='adminId']").attr('disabled','disabled');
	$('#adminForm').form('load', path + '/admin/getById.html?id=' + id);
	$("#add").show().dialog({
		title: "修改",
		height: 400,
		width: 550,
		modal : true,
		onClose: function () {
			$('#adminForm').form('clear').form('reset');
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
				dataType : 'json',
				url : path + '/admin/initPassword.html',
				data : {
					'id' : id
				},
				success : function(data) {
					if (data.success) {
						$.messager.alert('提示', '重置成功！');
					}else{
						$.messager.alert('提示', '重置失败！');
					}
				}
			});
		}
	});
}


function showRoleList(userId) {
	$('#roleTable').datagrid('options').url=path + "/auth/role/getList.html";
	$('#roleTable').datagrid('reload');
	
	$('#roleList input[name="userId"]').val(userId);
	
	$("#roleList").show().dialog({
		title: "岗位分配",
		height: 500,
		width: 650,
		modal : true,
		onClose: function () {
//            $("#dicForm").form('clear');
        }
	});
}

//用户详情
function showAdminDetail(id) {
	$.ajax({
		type : "POST",
		url : path + '/admin/getAdminDetail.html',
//		url : path + '/third/queryAcct/queryBalance.html',
		data : {
			'id' : id
		},
		success : function(data) {
			$("#adminDetail label").each(function(){
				$(this).text(data[this.id]);
			});
		}
	});
	
	$("#adminDetail").show().dialog({
		title: "用户详情",
		height: 350,
		width: 680,
		modal : true,
		onClose: function () {
			$("#adminDetail label").text('');
        }
	});
}

function validateIdCard(code){
	//身份证号码
	if(!code){
		$.messager.alert("提示","证件号码不能为空");
		return false;
	}else{
		var num = code.toUpperCase();  
		//身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。
		if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num))) {
			$.messager.alert("提示","身份证号码不正确");
			return false;
		}
		//校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
		//下面分别分析出生日期和校验位
		var len, re;
		len = num.length;
		if (len == 15) {
			re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
			var arrSplit = num.match(re);
			
			//检查生日日期是否正确
			var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/' + arrSplit[4]);
			var bGoodDay,goodyear;
			bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]) && (thisYear >= dtmBirth.getFullYear()));
			if (!bGoodDay) {
				$.messager.alert("提示","身份证号码不正确");
				return false;
			} else {
				//验证是否为成年人（大于18周岁）
//            	if(thisYear > (dtmBirth.getFullYear() + 17)){
//            		return 0;
//            	}
				//将15位身份证转成18位
				//校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
				var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
				var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
				var nTemp = 0, i;
				num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6);
				for (i = 0; i < 17; i++) {
					nTemp += num.substr(i, 1) * arrInt[i];
				}
				num += arrCh[nTemp % 11];
				return true;
			}
		}
		if (len == 18) {
			re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
			var arrSplit = num.match(re);
			
			//检查生日日期是否正确
			var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/" + arrSplit[4]), bGoodDay,thisYear = new Date().getFullYear();
			bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
			if (!bGoodDay) {
				return true;
			} else if(dtmBirth.getFullYear() > new Date().getFullYear()){//判断出生年份是否大于当前年份
				$.messager.alert("提示","身份证号码不正确");
				return false;
			}else if(dtmBirth.getFullYear() < 1900){
				$.messager.alert("提示","身份证号码不正确");
				return false;
			}else{
				//验证是否为成年人（大于18周岁）
//          if(thisYear > (dtmBirth.getFullYear() + 17)){
//        		return 0;
//        	}
				//检验18位身份证的校验码是否正确。
				//校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
				var valnum;
				var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
				var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
				var nTemp = 0, i;
				for (i = 0; i < 17; i++) {
					nTemp += num.substr(i, 1) * arrInt[i];
				}
				valnum = arrCh[nTemp % 11];
				if (valnum != num.substr(17, 1)) {
					$.messager.alert("提示","身份证号码不正确");
					return false;
				}
				return true;
			}
		}
		$.messager.alert("提示","身份证号码不正确");
		return false; 
	}	
}


