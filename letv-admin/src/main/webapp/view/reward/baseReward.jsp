<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
	var path = "<%=path%>";  
</script>
<script type="text/javascript" src="<%=path%>/js/reward/baseReward.js"></script>
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
			<div class="psb" style="float:right;">
				<a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a>
				<a id="resetBtn" class="easyui-linkbutton searchBtn">重置</a>
			</div>
		</div>
	</form>
	<table id="rewardTable"></table>
	
	<div id="add">
		<form id="rewardForm" method="post">
			<input type="hidden" name="id"/>
			<div class="x-form-item">
			    <label class="x-form-item-label">优惠券编号:</label>
			    <div class="x-form-element">
			        <input name="rewardNo" type="text"/>
			    </div>
			</div>
			<div class="x-form-item">
			    <label class="x-form-item-label">优惠券名称:</label>
			    <div class="x-form-element">
			        <input name="rewardName" type="text"/>
			    </div>
			</div>
			<div class="x-form-item">
			    <label class="x-form-item-label">优惠券类型:</label>
			    <div class="x-form-element">
			        <input id="s_rewardType" name="rewardType" valueField="value"  textField="text" class="easyui-combobox" url="<%=path %>/dictionary/reward_type.html"/>
			    </div>
			</div>
			<div class="x-form-item">
			    <label class="x-form-item-label">优惠券来源:</label>
			    <div class="x-form-element">
			        <input id="s_rewardSource" name="rewardSource"  valueField="value"  textField="text" class="easyui-combobox" url="<%=path %>/dictionary/reward_source.html"/>
			    </div>
			</div>
			<div class="x-form-item">
			    <label class="x-form-item-label">金额:</label>
			    <div class="x-form-element">
			        <input name="rewardAmt" id="rewardAmt" type="text"/>
			        <span id="rewardAmtspan" style="color:red"></span>
			    </div>
			</div>
			<div class="x-form-item">
			    <label class="x-form-item-label">使用下限(元):</label>
			    <div class="x-form-element">
			        <input name="lowerLimit" id="lowerLimit" type="text"/>
			        <span id="lowerLimitspan" style="color:red"></span>
			    </div>
			</div>
			<div class="x-form-item">
			    <label class="x-form-item-label">有效期(天):</label>
			    <div class="x-form-element">
			        <input name="effectiveDays" id="effectiveDays" type="text"/>
			        <span id="effectiveDaysspan" style="color:red"></span>
			    </div>
			</div>
			<div class="psb">
				<a id="saveBtn" class="easyui-linkbutton searchBtn">保存</a><a id="closeBtn" class="easyui-linkbutton searchBtn">取消</a>
			</div>
		</form>
	</div>
	
	<security:authorize access="hasPermission(null, 'security.operation.reward_coupon_base_add')">
		<input type="hidden" id="reward_coupon_base_add"/>
	</security:authorize>
	<script type="text/javascript">
		function operateData(val,row,index){
			//详情、、重置密码、锁定（恢复） normal lock disable
			var returnStr='<security:authorize access="hasPermission(null, \'security.operation.reward_coupon_base_modify\')"><a onclick=modify("'+row.id+'")>修改</a></security:authorize>';
			return returnStr;
		}
	</script>