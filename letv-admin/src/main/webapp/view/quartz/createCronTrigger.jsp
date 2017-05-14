<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
	var path = "<%=path%>";
</script>
<script type="text/javascript" src="<%=path%>/js/quartz/jobList.js"></script>
	<div class="mpath"><b>当前位置：</b><span>系统配置</span> &gt; <span>调度任务</span> &gt; <span>创建Cron触发器</span></div>
	<div id="add">
		<form id="jobForm" method="post" action="<%=path%>/quartz/job/saveJob.html">
			<input type="hidden" name="id"/>
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
					<span title="如果任务设置了 RequestsRecovery，那么它在调度器发生硬停止（例如，当前进程 crash，或者机器宕机）后，当调度器再次启动的时候将会重新执行">?</span>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">持久性:</label>
				<div class="x-form-element">
					<input type="checkbox" name="durable" style="width: 20px; border: 0;" />
					<span title="持久性，如果 Job 是非持久性的，那么执行完 Job 后，如果没有任何活动的 Trigger 与之关联，那么将会被调度器自动删除">?</span>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">备注:</label>
				<div class="x-form-element">
					<textarea rows="5" name="description" placeholder="描述"></textarea>
				</div>
			</div>
			
			<div class="psb">
				<a id="saveBtn" class="easyui-linkbutton searchBtn">保存</a><a id="closeBtn" class="easyui-linkbutton searchBtn">取消</a>
			</div>
		</form>
	</div>