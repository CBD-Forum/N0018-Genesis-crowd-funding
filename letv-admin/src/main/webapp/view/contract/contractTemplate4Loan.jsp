<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="<%=path%>/js/contract/contractTemplate4Loan.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>项目编号:</label><input name="contractNo" type="text"/></div>
			<div><label>模板名称:</label><input name="contractName" type="text"/></div>
			<div><label>模板类型:</label><input id="contractType" name="contractType" class="easyui-combobox" url="<%=path %>/dictionary/contract_template_type.html" panelHeight="auto"/></div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><a id="resetBtn" class="easyui-linkbutton searchBtn">重置</a></div>
		</div>
	</form>
	<table id="contractTemTable"></table>
	<script type="text/javascript">
	function operateData(val,row,index){
		return '<security:authorize access="hasPermission(null, \'security.operation.contract_loan_download\')"><a href="'+path+'/contract/getView.html?id='+row.id+'">下载</a></security:authorize>';
	}
	</script>
