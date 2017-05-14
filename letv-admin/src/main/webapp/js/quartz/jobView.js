$(function(){
	$.ajax({
		url: path + "/quartz/job/viewJob.html",
		type: "post",
		dataType: "json",
		data: {
			"jobName":jobName,
			"jobGroup":jobGroup
		},
		success: function(data){
			var jobDetail=data['jobDetail'],jobTriggers=data['jobTriggers'],jobDataMapKeys=data['jobDataMapKeys'];
			var group=jobDetail['group'],name=jobDetail['name'],jobClass=jobDetail['jobClass'],description=jobDetail['description'],requestsRecovery=jobDetail['requestsRecovery'],durability=jobDetail['durability'];
			$('table:eq(0) tr:eq(0) td:eq(1)').text(group);
			$('table:eq(0) tr:eq(1) td:eq(1)').text(name);
			$('table:eq(0) tr:eq(2) td:eq(1)').text(jobClass);
			$('table:eq(0) tr:eq(3) td:eq(1)').text(description==null?"":description);
			if (requestsRecovery) {
				 $("table:eq(0) tr:eq(4) td:eq(1) [name='requestsRecovery']").attr("checked", true);
			}
			if (durability) {
				$("table:eq(0) tr:eq(5) td:eq(1) [name='durability']").attr("checked", true);
			}
			
			var jobDataMap=jobDetail['jobDataMap'];
			for ( var i = 0,length=jobDataMapKeys.length; i < length; i++) {
				$('<tr><td>'+jobDataMapKeys[i]+'</td><td>'+jobDataMap[jobDataMapKeys[i]]+'</td></tr>').insertAfter($("table:eq(2) tr:eq(0)"));
			}
			
			for ( var i = 0,length=jobTriggers.length; i < length; i++) {
				var jobTrigger=jobTriggers[i];
				$('<tr><td><a href="'+path+'/quartz/job/unSchedule.html?jobName='+name+'&jobGroup='+group+'&triggerGroup='+jobTrigger['triggerGroup']+'&triggerName='+jobTrigger['triggerName']+'">删除触发器</a></td><td>'+jobTrigger['triggerGroup']+'</td><td>'+jobTrigger['triggerName']+'</td><td>'+jobTrigger['type']+'</td><td>'+jobTrigger['nextFireTime']+'</td></tr>').insertAfter($("table:eq(3) tr:eq(0)"));
			}
			
			//操作
			var operateHtml='<a href="'+path+'/quartz/jobCreate.jsp?jobName='+name+'&jobGroup='+group+'">编辑</a>'
					+'&nbsp;&nbsp;<a href="'+path+'/quartz/job/removeJob.html?jobName='+name+'&jobGroup='+group+'">删除</a>'
					+'&nbsp;&nbsp;<a href="'+path+'/quartz/job/runNow.html?jobName='+name+'&jobGroup='+group+'">单次执行</a>'
					+'&nbsp;&nbsp;<a href="'+path+'/jobs/startSchedule.action?jobName='+name+'&jobGroup='+group+'">任务计划</a>'
					+'&nbsp;&nbsp;<a href="'+path+'/jobs/startCronSchedule.action?jobName='+name+'&jobGroup='+group+'">任务计划 cron</a>'
					+'&nbsp;&nbsp;<a href="'+path+'/jobs/startUICronSchedule.action?jobName='+name+'&jobGroup='+group+'">任务计划 UI</a>';
			$('#operate').html(operateHtml);
			
		},
		error: function(){
			alert(11);
		}
	});
});

