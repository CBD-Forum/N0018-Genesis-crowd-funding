<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
	var path = "<%=path%>";
	var loanNo= "${loanNo}";
</script>
<script type="text/javascript" src="<%=path%>/js/captial/supporPartstList.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>投资人用户名:</label><input name=supportUser type="text"/></div>
			<div><label>投资人真实姓名:</label><input name="supportUserName" type="text"/></div>
			<div><label>项目编号:</label><input name="loanNo" type="text"/></div>
<!-- 			<div><label>投资时间:</label><input type="text" name="startInvestTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/> &nbsp;~ <input type="text" name="endInvestTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/></div> -->
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><input type="reset" class="easyui-linkbutton searchBtn" style="width:104px;height:28px;" value="重置"/></div>
		</div>
	</form>
	<table id="table"></table>
	<security:authorize access="hasPermission(null, 'security.operation.finance_capitalManage_supportParts_export')">
		<input type="hidden" id="finance_capitalManage_supportParts_export"/>
	</security:authorize>
