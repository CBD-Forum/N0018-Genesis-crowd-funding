<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
var loanNo = '${loanNo}';
var loanType = '${loanType}';
</script>
<script type="text/javascript" src="<%=path%>/js/captial/refundEarnestList.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>投资单号:</label><input id="orderId" name="orderIdLike" type="text"/></div>
			<div><label>投资人:</label><input id="s_support_user" name="supportUserLike" type="text"/></div>
			<div><label>投资人真实姓名:</label><input id="s_support_user_name" name="supportUserName" type="text"/></div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><input type="reset" class="easyui-linkbutton searchBtn" style="width:104px;height:28px;" value="重置"/></div>
		</div>
	</form>
	<table id="progectTable"></table>
	
	<script type="text/javascript">
<!--
function operateData(val,row,index){
	var returnStr='';
	//判断是否用【详细】的权限
	//通过
	returnStr+='<security:authorize access="hasPermission(null, \'security.operation.refund_check\')"><a onClick=auditAuth("'+row.orderId+'","passed")>审核通过</a></security:authorize>';
	returnStr+='<security:authorize access="hasPermission(null, \'security.operation.refund_check\')"><a onClick=auditAuth("'+row.orderId+'","refuse")>审核拒绝</a></security:authorize>';
	return returnStr;
}
//-->
</script>