$(function(){
	$.ajax({
		url: path + "/quartz/job/getJob.html",
		type: "get",
		dataType: "json",
		data: {
			"jobName":jobName,
			"jobGroup":jobGroup
		},
		success: function(data){
			var jobDetail=data['jobDetail'],jobDataMapKeys=data['jobDataMapKeys'];
			$("#jobDetailForm").form("load", jobDetail);
			
			
			var jobDataMap=jobDetail['jobDataMap'];
			for ( var i = 0,length=jobDataMapKeys.length; i < length; i++) {
				$('<tr><td><input type="text" name="parameterNames" value="'+jobDataMapKeys[i]+'"/></td><td><input type="text" name="parameterValues" value="'+jobDataMap[jobDataMapKeys[i]]+'"/></td></tr>').insertAfter($("table:eq(2) tr:eq(0)"));
			}
			
		},
		error: function(){
			alert(11);
		}
	});
});

