<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
var loanNo = '${loanNo}';
</script>
<script type="text/javascript" src="<%=path%>/js/crowdfund/loanLeadList.js"></script>
	<table id="progectTable"></table>
	
	<script type="text/javascript">
		function operateData(val,row,index){
			var returnStr = '';
			
			returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_preheat_release\')"><a onclick=cancelLoanLeader("'+row["id"]+'")>撤销领投人</a></security:authorize>';
			
			return returnStr;
		}
	</script>