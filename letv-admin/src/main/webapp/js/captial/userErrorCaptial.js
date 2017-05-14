$(function(){
	searchData("/adjustAccount/getPageList.html");
	//查询按钮事件
    $("#searchBtn").click(function(){
    	queryToGrid("captialTable", "list_search");
    });
    //重置查询条件
    $('#resetBtn').click(function(){
    	$('#list_search').form('reset');
    });
    
    //关闭提交申请窗口
    $('#closeBtn').click(function(){
    	$("#sysConfigForm").form('clear').form('reset');
    	$("#tipmsg").text("");
    	$("#apply").dialog('close');
    });
    
    $("#userId").change(function(){
    	 getRealName();//获取真实姓名
    });
    
    $('#submitBtn').click(function(){
		$('#sysConfigForm').submit();
	});
    
    
	$("#sysConfigForm").validate({
		rules : {
			userId:{
				required: true
			},
			realName:{
				required: true 
			},
			adjustType:{
				required: true 
			},
			adjustAmt:{
				required: true ,
				number:true
			},
			auditOpinion:{
				required: true
			}
		},
		messages : {
			userId :{
		    	required:"请填写用户名"
		    },
		    realName :{
		    	required:"请填写真实姓名"
		    },
		    adjustType :{
		    	required:"请填写跳转方向"
		    },
		    adjustAmt :{
		    	required:"请填写调整金额",
		    	number:"请填写正确的金额"
		    },
		    auditOpinion :{
		    	required:"请填写调账说明"
		    }
		},	
        submitHandler:function(form){
        	var amt = $("input[name='adjustAmt']").val();
        	if(parseFloat(amt) <= 0){
        		$.messager.alert('提示', "调整金额必须大于0");
        		return;
        	}
        	
        	
        	var realName = $("#realName").text();
        	alert(realName);
        	if(!realName || realName == ''){
        		$.messager.alert('提示', "真实姓名不能为空");
        		return;
        	}
        	
        	var url=path + '/adjustAccount/submitAdjustApply.html';
    		$('#sysConfigForm').form('submit', {
    			url : url,
    			success : function(data) {
    				data = $.parseJSON(data);
    				if(data["success"]){
    					$("#apply").show().dialog('close');
    					$('#captialTable').datagrid('reload');
    				}else{
    					alertfn("提交申请信息失败");
    				}
    			}
    		});
        }
    });
});
function searchData(durl){
	//获取字典数据
	var columns = [[
	                {field:'operat',title:'操作',align:'center',formatter:operateData},
	                {field:'id',title:'id',width:0,hidden:true},
					{field:'userId',title:'用户名',width:120,align:'center'},
					{field:'userName',title:'真实姓名',width:140,align:'center'},
					{field:'adjustTypeText',title:'交易方向',width:80,align:'center'},
					{field:'adjustAmt',title:'金额',width:80,align:'right'},
					{field:'applyTime',title:'申请时间',width:150,align:'center',sortable:true},
					{field:'operator',title:'申请操作人',width:100,align:'center'},
					{field:'auditor',title:'审核操作人',width:100,align:'center'},
					{field:'auditTime',title:'审核时间',width:150,align:'center'},
					{field:'auditStateText',title:'状态',width:100,align:'center'},
					{field:'adjustReason',title:'申请说明',width:120},
					{field:'auditOpinion',title:'审核说明',width:120}
				]];
	
	var addBtn = { 
	     	text: '申请', 
	        iconCls: 'icon-add', 
	        handler: function() { 
	        	$("#apply").show().dialog({
	        		title: "提交调账申请",
	        		height: 380,
	        		width: 500,
	        		modal : true,
	        		onClose: function () {
	                    $("#form1").form('clear').form('reset');
	                }
	        	});
	        } 
	      };
	var operateBtns=[];
	if ($('#finance_exceptionAccount_submit').length>0) {
		operateBtns.push(addBtn);
	}
	if (operateBtns.length==0) {
		operateBtns=null;
	}
	
	$('#captialTable').datagrid({
		url: path + durl,
		columns: columns,
		height:bodyHeight-230,
		rownumbers : true,
		singleSelect: true,
		sortName : 'applyTime',
		sortOrder : 'desc',
		pagination: true,
		toolbar: operateBtns
	});
}
function showOpeDialog(id){
	$("#add").show().dialog({
		title: "调账申请审核操作",
		height: 230,
		modal : true,
		onClose: function () {
            $("#auditform").form('clear');
        }
});
$("#pass").unbind("click").click(function(){
	if(!$("#auditOpinion").val()){
		$("#auditOpinion").parent().children(".inputf").text("请输入审核意见").show();
	}else{
		$("#auditOpinion").parent().children(".inputf").hide();
		$.ajax({
			url: path + "/adjustAccount/passAdjustApply.html",
			type: "post",
			dataType: "json",
			data: {
				"id": id,
				"auditOpinion": $("#auditOpinion").val()
			},
			success: function(data){
				window.location.reload();
				$("#add").show().dialog('close');
				$('#captialTable').datagrid('reload');
			},
			error: function(request){
				console.log("获取审核信息异常");
			}
		});
	}
});
	$("#refuse").click(function(){
		if(!$("#auditOpinion").val()){
			$("#auditOpinion").parent().children(".inputf").text("请输入审核意见").show();
		}else{
			$("#auditOpinion").parent().children(".inputf").hide();
			$.ajax({
				url: path + "/adjustAccount/refuseAdjustApply.html",
				type: "post",
				dataType: "json",
				data: {
					"id": id,
					"auditOpinion": $("#auditOpinion").val()
				},
				success: function(data){
					window.location.reload();
					$("#add").show().dialog('close');
					$('#captialTable').datagrid('reload');
				},
				error: function(request){
					console.log("获取审核信息异常");
				}
			});
		}
	});
}
//通过userid获取真实姓名
function getRealName(){
	//读取真实姓名
	$("#userId").blur(function(){
		$.ajax({
			url: path + "/user/getUserRealName.html",
			type: "post",
			dataType: "json",
			data: {"userId": $(this).val()},
			success: function(data){
				if(data["success"]){
					$("#realName").text(data["msg"]);
					$("#tipmsg").hide();
					
				}else{
					$("#realName").text("");
					$("#tipmsg").show();
					$("#tipmsg").text(data["msg"]);
				}
			},
			error: function(request){
				console.log("获取真实姓名异常");
			}
		});
	});
}