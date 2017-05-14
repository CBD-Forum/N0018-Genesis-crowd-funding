$(function(){
	$.ajax({
		url: path + "/quartz/job/schedule.html",
		type: "post",
		dataType: "json",
		data: {
			"command":'start'
		},
		success: function(data){
			var schedulerArr=[];
			for(var i=0,tlength=data.schedulers.length;i<tlength;i++){
				schedulerArr.push('<option value="'+data.schedulers[i]+'">'+data.schedulers[i]+'</option>');
			}
			$("#schedulerName").html(schedulerArr.join(""));
			
			var scheduler = data['scheduler'];
			$('table:eq(0) tr:eq(1) td:eq(1)').text(scheduler.schedulerName);
			$('table:eq(0) tr:eq(2) td:eq(1)').text(scheduler.state);
			$('table:eq(0) tr:eq(3) td:eq(1)').text(scheduler.runningSince);
			$('table:eq(0) tr:eq(4) td:eq(1)').text(scheduler.numJobsExecuted);
			$('table:eq(0) tr:eq(5) td:eq(1)').text(scheduler.persistenceType);
			$('table:eq(0) tr:eq(6) td:eq(1)').text(scheduler.threadPoolSize);
			$('table:eq(0) tr:eq(7) td:eq(1)').text(scheduler.version);
			//计划名称:schedulerName	"scheduler"
			//状态: state	"started"
			//启动时间:runningSince	"Thu Jan 08 11:11:23 CST 2015"
			//已执行Job数:	numJobsExecuted	"179"
			//持久类型:persistenceType	"database"
			//线程池大小:threadPoolSize	"10"
			//版本 :version	"1.8.6"
			//备注：summary
			$('pre').html(scheduler.summary);
			
		},
		error: function(){
			alert(444);
		}
	});
});

