<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
	var path = "<%=path%>";  
</script>
<script type="text/javascript" src="<%=path%>/js/reward/userReward.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div>
			      <label>优惠券类型:</label>
			      <input name="rewardType" class="easyui-combobox" url="<%=path %>/dictionary/reward_type.html" panelHeight="auto"/>
            </div>
			<div>
				<label>优惠券来源:</label>
			    <input name="rewardSource" class="easyui-combobox" url="<%=path %>/dictionary/reward_source.html" panelHeight="auto"/>
			</div>
			<div>
				<label>状态:</label>
			    <input name="rewardState" class="easyui-combobox" url="<%=path %>/dictionary/reward_state.html" panelHeight="auto"/>
			</div>
			<div>
				<label>用户名:</label>
			    <input name="userIdLike" type="text"/>
			</div>
			<div>
				<label>发放开始时间:</label>
				<input type="text" name="genStartTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})"/>
			</div>
			<div>
				<label>发放结束时间:</label>
			    <input type="text" name="genEndTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})"/>
			</div>
			<div class="psb" style="float:right;">
				<a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a>
				<a id="resetBtn" class="easyui-linkbutton searchBtn">重置</a>
			</div>
		</div>
	</form>
	<table id="userRewardTable"></table>
	
	<div id="sendRewardDiv" class="add">
		<form id="sendRewardForm" method="post">
			<input type="hidden" name="id"/>
			<div class="x-form-item">
			    <label class="x-form-item-label">优惠券节点:</label>
			    <div class="x-form-element">
			        <select id="s_node" name="node"><option></option></select>
			    </div>
			</div>
			<div class="x-form-item">
			    <label class="x-form-item-label">优惠券名称:</label>
			    <div class="x-form-element">
			        <select id="s_rewardName" name="rewardNo"><option></option></select>
			    </div>
			</div>
			<div class="x-form-item">
			    <label class="x-form-item-label">接收人用户名:</label>
			    <div class="x-form-element">
			        <input id="userId" name="userId" type="text" />
			    </div>
			</div>
			<div class="x-form-item">
			    <label class="x-form-item-label">发放时间:</label>
			    <div class="x-form-element">
			        <input id="startTime" name="genTime" type="text" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/>
			    </div>
			</div>
			
			<div class="x-form-item">
			    <label class="x-form-item-label">备注:</label>
			    <div class="x-form-element">
			    	<textarea rows="5" cols="4" name="description"></textarea>
			    </div>
			</div>
			<div class="psb">
				<a id="saveBtn" class="easyui-linkbutton searchBtn">保存</a><a id="closeBtn" class="easyui-linkbutton searchBtn">取消</a>
			</div>
		</form>
	</div>
	
	
	<security:authorize access="hasPermission(null, 'security.operation.reward_coupon_deatil_export')">
		<input type="hidden" id="reward_coupon_deatil_export"/>
	</security:authorize>
	
	
