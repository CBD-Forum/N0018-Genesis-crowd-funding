<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
	var path = "<%=path%>";
</script>
<script type="text/javascript" src="<%=path%>/js/captial/userCaptial.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>交易单号:</label><input name="tradeIdLike" type="text"/></div>
			<div><label>交易类型:</label><input name="tradeType" class="easyui-combobox" url="<%=path %>/dictionary/trade_type_person.html" /></div>
			<div><label>交易方向:</label><input name="tradeDirection" class="easyui-combobox" url="<%=path %>/dictionary/trade_direction.html" panelHeight="auto"/></div>
			<div><label>手机号:</label><input name="mobile" type="text"/></div>
			<div><label>用户名:</label><input name="realNameLike" type="text"/></div>
			<div><label>交易时间:</label><input name="tradeStartTime" type="text" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/>~<input name="tradeEndTime" type="text" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/></div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><input type="reset" class="easyui-linkbutton searchBtn" style="width:104px;height:28px;" value="重置"/></div>
		</div>
	</form>
	
	<security:authorize access="hasPermission(null, 'security.operation.finance_capitalManage_userCapital_export')">
		<input type="hidden" id="finance_capitalManage_userCapital_export"/>
	</security:authorize>
	
	<table id="captialTable"></table>