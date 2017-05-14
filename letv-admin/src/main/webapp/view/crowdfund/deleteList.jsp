<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="<%=path%>/js/crowdfund/deleteList.js"></script>
<script type="text/javascript" src="<%=path%>/js/crowdfund/loanCommon.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>项目编号:</label><input id="s_loanNo" name="loanNoLike" type="text"/></div>
			<div><label>项目名称:</label><input id="s_loanName" name="loanName" type="text"/></div>
			<div><label>发起人:</label><input id="s_loan_name" name="loanUserLike" type="text"/></div>
			<div><label>发起人真实姓名:</label><input id="s_loan_realname" name="loanUserNameLike" type="text"/></div>
			<div><label>项目类型:</label><input id="s_stutas" type="text" style="height: 24px;" name="loanType" class="easyui-combobox" url="<%=path %>/dictionary/crowdFundType.html" panelHeight="auto"/></div>
			<div><label>所在省:</label><select id="s_provice" name="province"><option></option></select></div>
			<div><label>所在市:</label><select id="s_city" name="city"><option></option></select></div>
			<div><label>所在县:</label><select id="s_county" name="county"><option></option></select></div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><input type="reset" class="easyui-linkbutton searchBtn" style="width:104px;height:28px;" value="重置"/></div>
		</div>
	</form>
	<table id="progectTable"></table>
	<div id="loanAudit">
		<table id="loanAuditTable"></table>
	</div>
	<div id="loanBack">
		<table id="loanBackTable"></table>
	</div>
	
	<%@ include file="stockback.jsp" %>
	<script type="text/javascript">
	$("#stockback").hide();
	function operateData1(val,row,index){
		var returnStr = '';
		//支持列表
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_cancel_support\')"><a onclick=supportList("'+row["loanNo"]+'")>支持列表</a></security:authorize>';
		//项目详情
			returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_cancel_detail\')"><a onclick=LoanDetail("'+row["loanNo"]+'","'+row["loanType"]+'")>项目详情</a></security:authorize>';
		//项目回报
		if("stock"!=row["loanType"]){
			returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_cancel_back\')"><a onclick=openBackSet("'+row["loanNo"]+'","'+row["loanType"]+'")>项目回报</a></security:authorize>';
		}
		return returnStr;
	}
	</script>