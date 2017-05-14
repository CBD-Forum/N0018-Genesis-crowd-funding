<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
var loanNo = '${loanNo}';
var loanType = '${loanType}';
</script>
<script type="text/javascript" src="<%=path%>/js/captial/loanBonusAuditList.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>项目编号:</label><input  name="loanNo" type="text"/></div>
			<div><label>项目名称:</label><input  name="loanName" type="text"/></div>
			<div><label>发起人真实姓名:</label><input name="realName" type="text"/></div>
			<div><label>审核状态:</label>
			<input id="bonusAuditState" type="text" style="height: 24px;" name="bonusAuditState" class="easyui-combobox" url="<%=path %>/dictionary/bonusAuditState.html" panelHeight="auto"/>
			</div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><input type="reset" class="easyui-linkbutton searchBtn" style="width:104px;height:28px;" value="重置"/></div>
		</div>
	</form>
	<table id="progectTable"></table>
	<div id="loanBonusAudit">
		<table id="loanBonusAuditTable"></table>
	</div>
	
	<div id="loanBonusDetailList">
		<table id="loanBonusDetailListTable"></table>
	</div>	
	
	
	
	<div id="add" style="display: none">
		<form id="auditform" method="post" style="margin-top:20px;">
			<input id="loanNo1" name="loanNo" type="hidden"/>
			<input id="loanBonusId" name="loanBonusId" type="hidden"/>
			<div class="x-form-item">
				<label class="x-form-item-label">审核意见:</label>
				<div class="x-form-element">
					<textarea id="auditOpinion" name="auditOpinion" style="width:240px;height:50px;"></textarea>
				</div>
			</div>
			
			<div class="psb" style="margin-top:30px;"><a id="pass" class="easyui-linkbutton searchBtn">通过</a><a id="refuse" class="easyui-linkbutton searchBtn">拒绝</a></div>
		</form>
	</div>
	
	
	<script type="text/javascript">
<!--
function operateData(val,row,index){
	var returnStr='';
	//判断是否用【详细】的权限
	//通过
// 	if(row.bonusAuditState == 'auditing'){
// 		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.refund_check\')"><a onClick=auditAuth("'+row.id+'","passed")>审核通过</a></security:authorize>';
// 		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.refund_check\')"><a onClick=auditAuth("'+row.id+'","refuse")>审核拒绝</a></security:authorize>';
// 	}

	//returnStr+='<security:authorize access="hasPermission(null, \'security.operation.operation.loan_bonus_detail\')"><a onclick=openAudit("'+row.id+'")>分红明细</a></security:authorize>';
	returnStr+='<a onclick=openBonusDetailList("'+row.id+'")>分红明细</a>';
	if(row.bonusAuditState == 'auditing'){  //根据审核状态处理数据
		//审核
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.loan_bonus_audit\')"><a onclick=operate("'+row["loanNo"]+'","'+row["id"]+'")>审核</a></security:authorize>';
	}
	if(row.bonusAuditState !='auditing'){  
		//审批流程
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.loan_bonus_audit_list\')"><a onclick=openAudit("'+row.id+'")>审核流程</a></security:authorize>';
	}
	
	return returnStr;
}
//-->
</script>