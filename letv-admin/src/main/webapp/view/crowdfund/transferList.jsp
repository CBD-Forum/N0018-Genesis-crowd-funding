<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
var loanNo = '${loanNo}';
var loanType = '${loanType}';
</script>
<script type="text/javascript" src="<%=path%>/js/crowdfund/transferList.js"></script>
<script type="text/javascript" src="<%=path%>/js/crowdfund/loanCommon.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>项目编号:</label><input  name="loanNoLike" type="text"/></div>
			<div><label>项目名称:</label><input  name="loanNameLike" type="text"/></div>
			<div><label>转让人:</label><input name="transferUserLike" type="text"/></div>
			<div><label>购买人:</label><input name="buyUserIdLike" type="text"/></div>
			<div><label>转让状态:</label>
			<input id="transferState" type="text" style="height: 24px;" name="transferState" class="easyui-combobox" url="<%=path %>/dictionary/transfer_product_state.html" panelHeight="auto"/>
			</div>
			<div><label>审核状态:</label>
			<input id="transferAuditState" type="text" style="height: 24px;" name="transferAuditState" class="easyui-combobox" url="<%=path %>/dictionary/transfer_product_audit_state.html" panelHeight="auto"/>
			</div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><input type="reset" class="easyui-linkbutton searchBtn" style="width:104px;height:28px;" value="重置"/></div>
		</div>
	</form>
	<table id="progectTable"></table>
	<div id="loanBonusAudit">
		<table id="loanBonusAuditTable"></table>
	</div>
	
	

	
	
	<script type="text/javascript">
<!--
function operateData(val,row,index){
	var returnStr='';
	//判断是否用【详细】的权限
	//通过
	if(row.transferAuditState == 'auditing'){
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.buy_transfer_audit\')"><a onClick=auditTransfer("'+row.transferNo+'","passed")>审核通过</a></security:authorize>';
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.buy_transfer_audit\')"><a onClick=auditTransfer("'+row.transferNo+'","refuse")>审核拒绝</a></security:authorize>';
	}
	return returnStr;
}
//-->
</script>