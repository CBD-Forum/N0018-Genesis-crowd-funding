<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
var loanNo = '${loanNo}';
var loanType = '${loanType}';
</script>
<script type="text/javascript" src="<%=path%>/js/captial/supportRefundAppList.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>投资单号:</label><input id="orderId" name="orderIdLike" type="text"/></div>
			<div><label>投资人:</label><input id="s_support_user" name="supportUserLike" type="text"/></div>
			<div><label>投资人真实姓名:</label><input id="s_support_user_name" name="supportUserName" type="text"/></div>
			<div><label>审核状态:</label>
			<input id="refundState" type="text" style="height: 24px;" name="refundState" class="easyui-combobox" url="<%=path %>/dictionary/refund_state.html" panelHeight="auto"/>
			</div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><input type="reset" class="easyui-linkbutton searchBtn" style="width:104px;height:28px;" value="重置"/></div>
		</div>
	</form>
	<table id="progectTable"></table>
	<div id="add">
		<form id="auditform" method="post" style="margin-top:20px;">
			<input id="loanNo" name="loanNo" type="hidden"/>
			<input id="supportNo" name="supportNo" type="hidden"/>
			<div class="x-form-item">
				<label class="x-form-item-label">审核意见:</label>
				<div class="x-form-element">
					<textarea id="auditOpinion" name="auditOpinion" style="width:240px;height:50px;"></textarea>
				</div>
			</div>
			
			<div class="psb" style="margin-top:30px;"><a id="pass" class="easyui-linkbutton searchBtn">通过</a><a id="refuse" class="easyui-linkbutton searchBtn">拒绝</a></div>
		</form>
	</div>
	
	
	<div id="loanRefundAudit">
		<table id="loanRefundAuditTable"></table>
	</div>
	
	<script type="text/javascript">
<!--
function operateData(val,row,index){
	var returnStr='';
	

	//判断是否用【详细】的权限
	if(row.refundState == 'auditing' && row.loanState=='lended' ){
		//审核
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.refund_audit\')"><a onclick=operate("'+row["loanNo"]+'","'+row["orderId"]+'")>审核</a></security:authorize>';
	}
	if(row.refundState =='refund_success' || row.refundState =='refuse' || row.refundState =='passed' ){
		//审批流程
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.refund_audit\')"><a onclick=openAudit("'+row.orderId+'")>审核流程</a></security:authorize>';
	}
	return returnStr;
}
//-->
</script>