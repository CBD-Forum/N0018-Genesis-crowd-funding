<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="<%=path%>/js/crowdfund/crowdfundList.js"></script>
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
			<div><label>项目状态:</label><input id="s_stutas" type="text" style="height: 24px;" name="loanState" class="easyui-combobox" url="<%=path %>/dictionary/crowdfund_state.html" panelHeight="auto"/></div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><input type="reset" class="easyui-linkbutton searchBtn" style="width:104px;height:28px;" value="重置"/></div>
		</div>
	</form>
	<table id="progectTable"></table>
	<div id="loanBack">
		<table id="loanBackTable"></table>
	</div>
	<div id="loanAudit">
		<table id="loanAuditTable"></table>
	</div>
	<%@ include file="stockback.jsp" %>
	<script type="text/javascript">
	$("#stockback").hide();
	function operateData(val,row,index){
		var returnStr = '';
		var loanType = row["loanType"];
		if(loanType == 'stock' || loanType == 'house'){
			//项目领投人
			returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_preheat_lead\')"><a onclick=loanLeaderList("'+row["loanNo"]+'")>项目领投人</a></security:authorize>';
			//预购列表
			//returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_preheat_support\')"><a onclick=preSupportsList("'+row["loanNo"]+'")>预购列表</a></security:authorize>';
		}
		//支持列表
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_list_supportList\')"><a onclick=supportList("'+row["loanNo"]+'","'+row["loanType"]+'")>支持列表</a></security:authorize>';
		//项目详情
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_list_detail\')"><a onclick=LoanDetail("'+row["loanNo"]+'","'+row["loanType"]+'")>项目详情</a></security:authorize>';
	
		//项目回报
		if("stock"!=loanType){
			returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_list_back\')"><a onclick=openBackSet("'+row["loanNo"]+'","'+row["loanType"]+'")>项目回报</a></security:authorize>';
		}
		//审批流程
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_all_auditList\')"><a onclick=openAudit("'+row.loanNo+'")>审核流程</a></security:authorize>';
		/* //预览
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_list_preview\')"><a onclick=openPreview("'+row["loanNo"]+'","'+row["loanType"]+'","'+row["loanState"]+'")>预览</a></security:authorize>'; */
		return returnStr;
	}
	</script>