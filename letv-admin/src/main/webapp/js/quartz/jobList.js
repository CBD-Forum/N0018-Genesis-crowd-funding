$(function(){
	var columns = [[
	                {field:'group',title:'Job组',width:60},
					{field:'name',title:'Job名称',width:370,sortable:true},
					{field:'jobClass',title:'Job的Java类',width:350,sortable:true},
					{field:'description',title:'备注',width:150,align:'center'},
					{field:'operat',title:'操作',width:250,align:'center',formatter:operateData}
				]];
	$('#quartzJob').datagrid({
		url: path + "/quartz/job/getlist.html",
		columns: columns,
		height:bodyHeight-140,
		rownumbers : true,
		singleSelect: true,
		toolbar: [{ 
		            text: '添加', 
		            iconCls: 'icon-add', 
		            handler: function() { 
		            	showAddWin();
		            } 
		        }]
	});
	
	//添加操作列
	function operateData(val,row,index){
		return '<a onclick=openJobDetailWin("'+row.name+'","'+row.group+'")>查看</a>'+
					'<a onclick=showEditWin("'+row.name+'","'+row.group+'")>编辑</a>'+
					'<a onclick=runNow("'+row["name"]+'","'+row["group"]+'")>立即执行</a>'+
					'<a href="javascript:void(0);" onclick=removeJob("'+row.name+'","'+row.group+'")>删除</a>'+
					'<a href="javascript:void(0);" onclick=showCronTrigger("'+row.name+'","'+row.group+'")>任务计划cron</a>';
	}
	
	//关闭添加窗口
	$('#closeBtn').click(function(){
		$("#add").dialog('close');
	});
	
	//关闭添加触发器窗口
	$('#closeCronTriggerBtn').click(function(){
		$("#createCronTrigger").dialog('close');
	});
	
	$("#jobForm").validate({
		rules : {
			group : {
				required: true,
                maxlength:200
			},
			name : {
				required: true,
                maxlength:200
			},
			jobClass : {
				required: true,
                maxlength:250
			}
		},
		messages : {
			group : {
				required : "请填写Job组"
			},
			name : {
				required : "请填写Job名称"
			},
			jobClass : {
				required : "请填写Job的Java类"
			}
		},				
        submitHandler:function(form){
        	var url=path + '/quartz/job/saveJob.html';
//    		if ($('#sysConfigForm input[name="id"]').val()!='') {
//    			url=path + '/sysconfig/modify.html';
//    		}
    		$('#jobForm').form('submit', {
    			url : url,
    			success : function(data) {
    				var obj = $.parseJSON(data);
    				if (obj.success) {
    					$("#add").dialog('close');
    					$('#quartzJob').datagrid('reload');
    				}else{
    					$.messager.alert('提示', obj.msg);
    				}
    			}
    		});
        }
    });
	
	//添加job
	$('#saveBtn').click(function(){
		$('#jobForm').submit();
	});
	
	
	$("#cronTriggerForm").validate({
		rules : {
			triggerGroup : {
				required: true,
                maxlength:200
			},
			triggerName : {
				required: true,
                maxlength:200
			},
			cronExpression : {
				required: true,
                maxlength:200
			}
		},
		messages : {
			triggerGroup : {
				required : "请填写触发器组名"
			},
			triggerName : {
				required : "请填写触发器名称"
			},
			cronExpression : {
				required : "请填写Cron表达式"
			}
		},				
        submitHandler:function(form){
        	var url=path + '/quartz/job/createCronTrigger.html';
    		$('#cronTriggerForm').form('submit', {
    			url : url,
    			success : function(data) {
    				var obj = $.parseJSON(data);
    				if (obj.success) {
    					$("#createCronTrigger").dialog('close');
    				}else{
    					$.messager.alert('提示', obj.msg);
    				}
    			}
    		});
        }
    });
	
	//添加触发器
	$('#saveCronTriggerBtn').click(function(){
		$('#cronTriggerForm').submit();
	});
	
});

//添加Job窗口
function showAddWin(){
	$("#add").show().dialog({
		title: "添加调度",
		height: 400,
		width:530,
		modal : true,
		onClose: function () {
			$('#jobForm').form('clear');
        }
	});
}

//打开编辑窗口
function showEditWin(jobName,jobGroup){
	$.ajax({
		url: path + "/quartz/job/getJob.html",
		type: "get",
		dataType: "json",
		data: {
			"jobName":jobName,
			"jobGroup":jobGroup
		},
		success: function(data){
			var jobDetail=data.jobDetail,jobDataMapKeys=data.jobDataMapKeys;
			$("#jobForm").form("load", jobDetail);
			
			
			var jobDataMap=jobDetail['jobDataMap'];
			$("#jobForm input[name=parameterNames]").parent().parent().remove();
			$("#jobForm input[name=parameterValues]").parent().parent().remove();
			for ( var i = 0,length=jobDataMapKeys.length; i < length; i++) {
				var str = '<div class="x-form-item">'
						+ '	<label class="x-form-item-label">参数'+(i+1)+':</label>'
						+ '	<div class="x-form-element">'
						+ '		参数名：<input type="text" name="parameterNames" style="width: 120px;" placeholder="参数名" value="'+jobDataMapKeys[i]+'"/>'
						+ '		值：<input type="text" name="parameterValues" style="width: 120px;" placeholder="值" value="'+jobDataMap[jobDataMapKeys[i]]+'"/>'
						+ '	</div>'
						+ '</div>';
				$(str).insertAfter($("#jobForm textarea[name=description]").parent().parent());
			}
			$("#add").show().dialog({
				title: "修改调度",
				height: 550,
				width:600,
				modal : true,
				onClose: function () {
					$('#jobForm').form('clear');
		        }
			});
		},
		error: function(){
			
		}
	});
	
}

//添加Cron表达式触发器
function showCronTrigger(jobName,jobGroup){
	$('#jobName').val(jobName);
	$('#jobGroup').val(jobGroup);
	$("#createCronTrigger").show().dialog({
		title: "添加Cron表达式触发器",
		height: 450,
		width:530,
		modal : true,
		onClose: function () {
			$('#cronTriggerForm').form('clear');
        }
	});
}

function openJobDetailWin(jobName,jobGroup){
	$.ajax({
		url: path + "/quartz/job/viewJob.html",
		type: "post",
		dataType: "json",
		data: {
			"jobName":jobName,
			"jobGroup":jobGroup
		},
		success: function(data){
			var jobDetail=data.jobDetail,jobTriggers=data.jobTriggers,jobDataMapKeys=data.jobDataMapKeys;
			var group=jobDetail['group'],name=jobDetail['name'],requestsRecovery=jobDetail['requestsRecovery'],durability=jobDetail['durability'];
			
			$("#jobDetailForm .x-form-item .x-form-element label").each(function(){
				$(this).text('');
				$(this).text(jobDetail[this.id]?jobDetail[this.id]:'');
			});
			
			var jobDataMap=jobDetail.jobDataMap;
			$("#jobData tr:gt(0)").remove();
			for ( var i = 0,length=jobDataMapKeys.length; i < length; i++) {
				$('<tr><td>'+jobDataMapKeys[i]+'</td><td>'+jobDataMap[jobDataMapKeys[i]]+'</td></tr>').insertAfter($("#jobData tr:eq(0)"));
			}
			
			$("#jobTrigger tr:gt(0)").remove();
			for ( var i = 0,length=jobTriggers.length; i < length; i++) {
				var jobTrigger=jobTriggers[i];
				$('<tr><td><a href="javascript:void(0)" onclick=removeTrigger("'+name+'","'+group+'","'+jobTrigger['triggerGroup']+'","'+jobTrigger['triggerName']+'") >删除</a></td><td>'+jobTrigger['triggerGroup']+'</td><td>'+jobTrigger['triggerName']+'</td><td>'+jobTrigger['type']+'</td><td>'+jobTrigger['nextFireTime']+'</td></tr>').insertAfter($("#jobTrigger tr:eq(0)"));
			}
			
			$("#jobView").show().dialog({
				title: "调度详情",
				modal : true,
				height: 450,
				width:700,
				onClose: function () {
		        }
			});
			
		},
		error: function(){
			
		}
	});
}


//立即执行
function runNow(jobName,jobGroup){
	$.ajax({
		url: path + "/quartz/job/runNow.html",
		type: "GET",
		dataType: "json",
		data: {
			"jobName":jobName,
			"jobGroup":jobGroup
		},
		success: function(data){
			if(data["success"]){
				$.messager.alert('提示', '立即执行成功');
			}else{
				
			}
		},
		error: function(){
			console.log("立即执行异常");
		}
	});
}

//删除job
function removeJob(jobName,jobGroup){
	$.messager.confirm('提示', '您确定要删除选择的job记录吗', function(r) {
		if (r) {
			$.ajax({
				url: path + "/quartz/job/removeJob.html",
				type: "GET",
				dataType: "json",
				data: {
					"jobName":jobName,
					"jobGroup":jobGroup
				},
				success: function(data){
					if(data["success"]){
						$.messager.alert('提示', '删除成功');
						$('#quartzJob').datagrid('reload');
					}else{
						
					}
				},
				error: function(){
					console.log("立即执行异常");
				}
			});
		}
	});
}

//删除Trigger
function removeTrigger(jobName,jobGroup,triggerGroup,triggerName){
	$.messager.confirm('提示', '您确定要删除选择的触发器记录吗', function(r) {
		if (r) {
			
			$.ajax({
				url: path + "/quartz/job/unSchedule.html",
				type: "POST",
				dataType: "json",
				data: {
					"jobName":jobName,
					"jobGroup":jobGroup,
					'triggerGroup':triggerGroup,
					'triggerName':triggerName
				},
				success: function(data){
					if(data["success"]){
						$.messager.alert('提示', '删除成功');
					}else{
						
					}
				},
				error: function(){
					console.log("立即执行异常");
				}
			});
		}
	});
}