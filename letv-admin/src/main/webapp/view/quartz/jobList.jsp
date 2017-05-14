<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<style type="text/css">
<!--
.quartzTab {
	width:100%;
	text-align:center;
	border-collapse: collapse;
	border: none;
}
.quartzTab th {
	border: solid 1px #000000;
}
.quartzTab td {
	border: solid 1px #000000;
	font-size:13px;
}
-->
</style>
<script type="text/javascript">
	var path = "<%=path%>";
</script>
<script type="text/javascript" src="<%=path%>/js/quartz/jobList.js"></script>
	<table id="quartzJob"></table>
	<div id="add">
		<form id="jobForm" method="post">
			<div class="x-form-item">
				<label class="x-form-item-label">Job组:</label>
				<div class="x-form-element">
					<input type="text" name="group" placeholder="Job组"/>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">Job名称:</label>
				<div class="x-form-element">
					<input type="text" name="name" placeholder="Job名称"/>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">Job的Java类:</label>
				<div class="x-form-element">
					<input type="text" name="jobClass" placeholder="Job的Java类"/>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">恢复:</label>
				<div class="x-form-element">
					<input type="checkbox" name="requestsRecovery" style="width: 20px; border: 0;"/>
					<!--  
					<span title="如果任务设置了 RequestsRecovery，那么它在调度器发生硬停止（例如，当前进程 crash，或者机器宕机）后，当调度器再次启动的时候将会重新执行">?</span>
					-->
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">持久性:</label>
				<div class="x-form-element">
					<input type="checkbox" name="durable" style="width: 20px; border: 0;" />
					<!--  
					<span title="持久性，如果 Job 是非持久性的，那么执行完 Job 后，如果没有任何活动的 Trigger 与之关联，那么将会被调度器自动删除">?</span>
					-->
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">备注:</label>
				<div class="x-form-element">
					<textarea rows="5" name="description" placeholder="描述"></textarea>
				</div>
			</div>
			
			<div class="psb">
				<a id="saveBtn" class="easyui-linkbutton searchBtn">保存</a>
				<a id="closeBtn" class="easyui-linkbutton searchBtn">取消</a>
			</div>
		</form>
	</div>
	
	
	<div id="createCronTrigger" class="add">
		<form id="cronTriggerForm" method="post">
			<input type="hidden" id="jobName" name="jobName"/>
			<input type="hidden" id="jobGroup" name="jobGroup"/>
			<div class="x-form-item">
				<label class="x-form-item-label">触发器组:</label>
				<div class="x-form-element">
					<input type="text" name="triggerGroup" placeholder="触发器组"/>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">触发器名称:</label>
				<div class="x-form-element">
					<input type="text" name="triggerName" placeholder="触发器名称"/>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">备注:</label>
				<div class="x-form-element">
					<textarea rows="5" name="description" placeholder="备注"></textarea>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">开始时间:</label>
				<div class="x-form-element">
					<input type="text" name="startTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">结束时间:</label>
				<div class="x-form-element">
					<input type="text" name="stopTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">Cron表达式:</label>
				<div class="x-form-element">
					<input type="text" name="cronExpression" placeholder="Cron表达式"/>
				</div>
			</div>
			
			<div class="psb">
				<a id="saveCronTriggerBtn" class="easyui-linkbutton searchBtn">保存</a>
				<a id="closeCronTriggerBtn" class="easyui-linkbutton searchBtn">取消</a>
			</div>
		</form>
	</div>
	
	
	

	<!-- 查看job详情 -->
	<div id="jobView" class="add">
		<h3>基本信息</h3>
		<form id="jobDetailForm" method="post">
			<div class="x-form-item">
				<label class="x-form-item-label">Job组:</label>
				<div class="x-form-element">
					<label id="group"></label>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">Job名称:</label>
				<div class="x-form-element">
					<label id="name"></label>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">Job的Java类:</label>
				<div class="x-form-element">
					<label id="jobClass"></label>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">恢复:</label>
				<div class="x-form-element">
					<label id="requestsRecovery"></label>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">持久性:</label>
				<div class="x-form-element">
					<label id="durable"></label>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">备注:</label>
				<div class="x-form-element">
					<label id="description"></label>
				</div>
			</div>
			<h3>变量</h3>
			<table id="jobData" class="quartzTab">
				<tr>
					<th>变量名</th>
					<th>值</th>
				</tr>
			</table>
			<br/>
			<h3>触发器</h3>
			<table id="jobTrigger" class="quartzTab">
				<tr>
					<th>操作</th>
					<th>触发器组</th>
					<th>触发器名称</th>
					<th>触发器类型</th>
					<th>下次运行时间</th>
				</tr>
			</table>
			<!--  
			<h3>Job listeners</h3>
			<table>
			</table>
			-->
		</form>
	</div>