<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
	var path = "<%=path%>";  
</script>
<script type="text/javascript" src="<%=path%>/js/reward/rewardNode.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div>
				<label>优惠券节点:</label>
			    <input name="node" class="easyui-combobox" url="<%=path %>/dictionary/reward_source.html" panelHeight="auto"/>
			</div>
			<div class="psb" style="float:right;">
				<a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a>
				<a id="resetBtn" class="easyui-linkbutton searchBtn">重置</a>
			</div>
		</div>
	</form>
	<table id="rewardNodeTable"></table>
	
	<div id="add">
		<form id="rewardNodeForm" method="post">
			<input type="hidden" name="id"/>
			<div class="x-form-item">
			    <label class="x-form-item-label">优惠券节点:</label>
			    <div class="x-form-element">
			        <select id="s_node" name="node"><option></option></select>
			    </div>
			</div>
			<!--  <div class="x-form-item">
			    <label class="x-form-item-label">优惠券编号:</label>
			    <div class="x-form-element">
			        <input name="rewardNo" type="text" readOnly="true"/>
			        <span id="t_rewardNo" style="color:red"></span>
			    </div>
			</div>-->
			<div class="x-form-item">
			    <label class="x-form-item-label">优惠券名称:</label>
			    <div class="x-form-element">
			        <select id="s_rewardName" name="rewardNo"><option></option></select>
			        <span id="t_rewardNo" style="color:red"></span>
			    </div>
			</div>
			<div class="x-form-item" id="d_rewardType">
			    <label class="x-form-item-label">优惠券类型:</label>
			    <div class="x-form-element">
			        <input id="s_rewardType" readOnly="true" name="rewardType" valueField="value"  textField="text" class="easyui-combobox" url="<%=path %>/dictionary/reward_type.html"/>
			    </div>
			</div>
			<div class="x-form-item" id="d_rewardAmt">
			    <label class="x-form-item-label">金额:</label>
			    <div class="x-form-element">
			        <input name="rewardAmt" type="text" readOnly="true"/>
			    </div>
			</div>
			<div class="x-form-item" id="d_lowerLimit">
			    <label class="x-form-item-label">使用下限(元):</label>
			    <div class="x-form-element">
			        <input name="lowerLimit" type="text" readOnly="true"/>
			    </div>
			</div>
			<div class="x-form-item" id="d_effectiveDays">
			    <label class="x-form-item-label">有效期(天):</label>
			    <div class="x-form-element">
			        <input name="effectiveDays" type="text" readOnly="true"/>
			    </div>
			</div>
			<div class="x-form-item">
			    <label class="x-form-item-label">发放数量:</label>
			    <div class="x-form-element">
			        <input name="rewardNum" id="rewardNum" type="text"/>
			        <span id="rewardNumspan" style="color:red"></span>
			    </div>
			</div>
			<div class="x-form-item">
			    <label class="x-form-item-label">发放开始时间:</label>
			    <div class="x-form-element">
			        <input id="startTime" name="assignStartTime" type="text" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/>
			        <span id="t_startTime" style="color:red"></span>
			    </div>
			</div>
			<div class="x-form-item">
			    <label class="x-form-item-label">发放结束时间:</label>
			    <div class="x-form-element">
			        <input id="endTime" name="assignEndTime" type="text" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/>
			        <span id="t_endTime" style="color:red"></span>
			    </div>
			</div>
			<div class="x-form-item">
			    <label class="x-form-item-label">发放状态:</label>
			    <div class="x-form-element">
			        <input id="s_state" name="state" id="state" type="text" class="easyui-combobox" url="<%=path %>/dictionary/reward_node_state.html" panelHeight="auto"/>
			        <span id="t_state" style="color:red"></span>
			    </div>
			</div>
			<div class="psb">
				<a id="saveBtn" class="easyui-linkbutton searchBtn">保存</a><a id="closeBtn" class="easyui-linkbutton searchBtn">取消</a>
			</div>
		</form>
	</div>
	
	<security:authorize access="hasPermission(null, 'security.operation.reward_coupon_send_add')">
		<input type="hidden" id="reward_coupon_send_add"/>
	</security:authorize>
	<script type="text/javascript">
		function operateData(val,row,index){
			//详情、、重置密码、锁定（恢复） normal lock disable
			var returnStr='<security:authorize access="hasPermission(null, \'security.operation.reward_coupon_send_modify\')"><a onclick=modify("'+row.id+'")>修改</a></security:authorize>';
			returnStr+='<security:authorize access="hasPermission(null, \'security.operation.reward_coupon_send_delete\')"><a onclick=removeRewardNode("'+row.id+'")>删除</a></security:authorize>';
			return returnStr;
		}
	</script>
	