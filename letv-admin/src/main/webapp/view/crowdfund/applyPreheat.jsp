<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="<%=path%>/js/crowdfund/applyPreheat.js"></script>
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
	<div id="loanBack">
		<table id="loanBackTable"></table>
	</div>
	<div id="loanAudit">
		<table id="loanAuditTable"></table>
	</div>
	
	<div id="add">
		<form id="auditform" method="post" style="margin-top:20px;">
			<input id="loanNo" name="loanNo" type="hidden"/>
			<div class="x-form-item" id="sDate">
				<label class="x-form-item-label">预热截止时间:</label>
				<div class="x-form-element">
					<input type="text" name="preheatEndTime" id="preheatEndTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})" style="margin-left:0; width:240px;"/>
				</div>
			</div>
			<div class="psb" style="margin-top:30px;">
			<a id="preheatbtn" class="easyui-linkbutton searchBtn">预热</a>
			<a id="cancel" class="easyui-linkbutton searchBtn">取消</a>
			</div>
		</form>
	</div>
	
	
	
	<%@ include file="stockback.jsp" %>
	<script type="text/javascript">
	$("#stockback").hide();
	function operateData(val,row,index){
		var returnStr = '';
		//编辑
		returnStr+= '<security:authorize access="hasPermission(null, \'security.operation.crowdfund_applypreheat_edit\')"><a onclick=editLoan("'+row["loanNo"]+'","'+row["loanType"]+'")>编辑</a></security:authorize>';
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_applypreheat_preheat\')"><a onclick=preheatLoan("'+row["loanNo"]+'","'+row["prepayAmt"]+'","'+row["loanPayedAmt"]+'")>预热审核</a></security:authorize>';
		return returnStr;
	}

	function operateData1(val,row,index){
		var returnStr = '';
		//项目详情
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_releasing_detail\')"><a onclick=LoanDetail("'+row["loanNo"]+'","'+row["loanType"]+'")>项目详情</a></security:authorize>';
		//项目回报
		if("stock"!=row["loanType"]){
			returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_releasing_back\')"><a onclick=openBackSet("'+row["loanNo"]+'","'+row["loanType"]+'")>回报设置</a></security:authorize>';
		}
		//审批流程
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_releasing_auditList\')"><a onclick=openAudit("'+row.loanNo+'")>审核流程</a></security:authorize>';
		//预览
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_releasing_preview\')"><a onclick=openPreview("'+row["loanNo"]+'","'+row["loanType"]+'","'+row["loanState"]+'")>预览</a></security:authorize>';
		return returnStr;
	}
	</script>