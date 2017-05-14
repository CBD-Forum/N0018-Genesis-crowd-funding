<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="<%=path%>/js/recharge/feeRechargeList.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>充值状态:</label><input id="state" name="state" class="easyui-combobox" url="<%=path %>/dictionary/recharge_state.html" panelHeight="auto"/></div>
			<div><label>充值时间:</label><input type="text" name="startCreateTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/> &nbsp;~ <input type="text" name="endCreateTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/></div>
			<div><label>到账时间:</label><input type="text" name="startCompleteTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/> &nbsp;~ <input type="text" name="endCompleteTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/></div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><a id="resetBtn" class="easyui-linkbutton searchBtn">重置</a></div>
		</div>
	</form>
	<table id="rechargeTable"></table>
	
	<div id="add">
		<form id="rechargeForm" target="_blank" method="post" action="http://www.yeepay.com/" style="padding-top: 20px;">
			<input type="hidden" name="id"/>
			<div class="x-form-item">
				<label class="x-form-item-label">金额:</label>
				<div class="x-form-element">
					<input type="text" name="rechargeAmt" placeholder="金额"/>
				</div>
			</div>
			<div class="psb">
				<a id="saveBtn" class="easyui-linkbutton searchBtn">保存</a><a id="closeBtn" class="easyui-linkbutton searchBtn">取消</a>
			</div>
		</form>
	</div>
	
	<security:authorize access="hasPermission(null, 'security.operation.finance_platform_recharge')">
		<input type="hidden" id="finance_platform_recharge"/>
	</security:authorize>
	
	<security:authorize access="hasPermission(null, 'security.operation.finance_platform_export')">
		<input type="hidden" id="finance_platform_export"/>
	</security:authorize>