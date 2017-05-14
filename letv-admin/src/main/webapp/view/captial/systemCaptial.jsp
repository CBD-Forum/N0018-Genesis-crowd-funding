<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
	var path = "<%=path%>";
</script>
<style type="text/css">
	.seach_div p{width:750px;margin-top:80px;}
	.seach_div p label{width:125px;font-size:14px;}
	.seach_div p span{font-size:14px;margin-left:10px;font-weight:bold;}
</style>
<script type="text/javascript" src="<%=path%>/js/captial/systemCaptial.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>交易单号:</label><input name="tradeIdLike" type="text"/></div>
			<div><label>交易类型:</label><input name="tradeType" class="easyui-combobox" url="<%=path %>/dictionary/trade_type_system.html" /></div>
			<div><label>交易开始时间:</label><input name="tradeStartTime" type="text" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/>~<label>交易结束时间:</label><input name="tradeEndTime" type="text" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/></div>
<%-- 			<div><label>子账户:</label><input name="tradeDirection" class="easyui-combobox" url="<%=path %>/dictionary/sys_third_account.html" panelHeight="auto"/></div>
 --%>			<div><label>交易方向:</label><input name="tradeDirection" class="easyui-combobox" url="<%=path %>/dictionary/trade_direction.html" panelHeight="auto"/></div>
			<p>
				<label>总收入金额:</label><span id="totalAmt"></span>
				<label>总支出金额:</label><span id="totalOutAmt"></span>
				<label>当前余额:</label><span id="balance"></span>
			</p>
			<div class="psb" style="float:right;">
				<a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a>
				<a id="resetBtn" class="easyui-linkbutton searchBtn">重置</a>
			</div>
		</div>
	</form>
	<table id="progectTable"></table>
	
	<security:authorize access="hasPermission(null, 'security.operation.finance_capitalManage_sysCapital_export')">
		<input type="hidden" id="finance_capitalManage_sysCapital_export"/>
	</security:authorize>