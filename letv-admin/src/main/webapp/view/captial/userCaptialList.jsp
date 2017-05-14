<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
	var path = "<%=path%>";
</script>
<script type="text/javascript" src="<%=path%>/js/captial/userCaptialList.js"></script>
<form id="list_search">
	<div class="seach_div">
		<div><label>用户名:</label><input name="userId" type="text"/></div>
		<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><input type="reset" class="easyui-linkbutton searchBtn" style="width:104px;height:28px;" value="重置"/></div>
	</div>
</form>
<security:authorize access="hasPermission(null, 'security.operation.finance_capitalManage_countAmt_export')">
		<input type="hidden" id="finance_capitalManage_countAmt_export"/>
	</security:authorize>
<table id="progectTable"></table>