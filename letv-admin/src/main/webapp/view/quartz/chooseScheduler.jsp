<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
	var path = "<%=path%>";
	var jobName="<%=request.getParameter("jobName")%>";
	var jobGroup="<%=request.getParameter("jobGroup")%>";
</script>
<script type="text/javascript" src="<%=path%>/js/quartz/chooseScheduler.js"></script>
	<form id="frmChooseScheduler" name="chooseSchedulerForm" method="get" action="<%=path%>/schedule/scheduleControl.action">
	<input type="hidden" id="command" name="command" value=""/>
	<table>
		<tr>
			<td width="200">调度</td>
			<td>
			<select id="schedulerName" name="schedulerName" onchange="submit()">
			</select>
			</td>
		</tr>
		<tr>
			<td>计划名称</td><td></td>
		</tr>
		<tr>
			<td>状态</td><td></td>
		</tr>
		<tr>
			<td>启动时间</td><td></td>
		</tr>
		<tr>
			<td>已执行Job数</td><td></td>
		</tr>
		<tr>
			<td>持久类型</td><td></td>
		</tr>
		<tr>
			<td>线程池大小</td><td></td>
		</tr>
		<tr>
			<td>版本</td><td></td>
		</tr>
	</table>	
		<span id="controls">
		<button name="play" value="start" type="submit" title="Start Scheduler"><img type="image"  value="start" src="<%=path%>/style/icons/Play24.gif" alt="Start Scheduler" title="Start Scheduler" /></button>
		<button name="pause" value="pause" type="submit" title="Pause Scheduler"><img type="image" value="pause" src="<%=path%>/style/icons/Pause24.gif"  alt="Pause Scheduler"  /></button> 
		<button name="stop" value="stop" type="submit" title="Stop Scheduler"><img type="image" value="stop" src="<%=path%>/style/icons/Stop24.gif"  alt="Stop Scheduler"  /></button>
		<button name="waitAndStop" value="waitAndStopScheduler" type="submit"  title="Stop scheduler, but wait for jobs to complete"><img type="image" value="waitAndStopScheduler" src="<%=path%>/style/icons/Stop24.gif"  /> &nbsp;</button>
		</span>
		<br/>
	设置此调度作为当前的调度<br>(Set this scheduler as current scheduler): <input type="submit" class="submit" value="set" property="btnSetSchedulerAsCurrent"/>
	</form>
	<hr/>
	当前执行的Job(Currently executing jobs)
	<table><tr>
	<td>Job组</td>
	<td>Job名称</td>
	<td>备注</td>
	<td>Job的Java类</td>
	</tr>
	</table>
	<table>
		<tr>
			<td width="30">
				<img src="<%=path%>/style/icons/Pause24.gif" value="btnPauseAllJobs" alt="Pause all jobs"/>
			</td>
			<td width="30">
				<img src="<%=path%>/style/icons/Play24.gif" value="btnResumeAllJobs" alt="Resume all jobs"/>
			</td>
		</tr>
	</table>
	<hr/>
	<p>备注: <i><pre></pre></i></p>