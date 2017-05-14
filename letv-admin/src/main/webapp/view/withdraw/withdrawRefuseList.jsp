<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="<%=path%>/js/withdraw/withdrawRefuseList.js"></script>
	<form id="list_search">
		<div class="seach_div">
		    <div><label>提现单号:</label><input name="orderId" type="text"/></div>
			<div><label>用户名:</label><input name="userId" type="text"/></div>
			<div><label>手机号:</label><input name="mobile" type="text"/></div>
			<div><label>真实姓名:</label><input name="userName" type="text"/></div>
			<div><label>申请时间:</label><input type="text" name="startApplyTime" id="startApplyTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/> &nbsp;~ <input type="text" name="endApplyTime" id="endApplyTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/><span id="timeSpan" style="color:red"></span></div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><a id="resetBtn" class="easyui-linkbutton searchBtn">重置</a></div>
		</div>
	</form>
	
	<security:authorize access="hasPermission(null, 'security.operation.finance_refuse_withdraw_export')">
		<!-- <input type="hidden" id="finance_refuse_withdraw_export"/> -->
	</security:authorize>
	
	<table id="withdrawTable"></table>
	