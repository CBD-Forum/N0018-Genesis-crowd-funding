<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
var loanNo = '${loanNo}';
</script>
<script type="text/javascript" src="<%=path%>/js/crowdfund/loanCommon.js"></script>
<script type="text/javascript" src="<%=path%>/js/crowdfund/investSupportList.js"></script>
	<form id="list_search">
		<div class="seach_div">
<!-- 			<div><label>认购单号:</label><input id="orderId" name="orderId" type="text"/></div>
 -->			<div><label>认购人:</label><input id="s_support_user" name="supportUserLike" type="text"/></div>
			<div><label>认购人真实姓名:</label><input id="s_support_user_name" name="supportUserName" type="text"/></div>
<!-- 			<div><label>认购项目编号:</label><input id="s_support_loan_no" name="loanNo" type="text"/></div>
<!-- <!--  -->			<div><label>认购项目名称:</label><input id="s_support_loan_name" name="loanName" type="text"/></div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><input type="reset" class="easyui-linkbutton searchBtn" style="width:104px;height:28px;" value="重置"/></div>
		</div>
	</form>
	<table id="progectTable"></table>
	
	<script type="text/javascript">
		function operateData(val,row,index){
			var returnStr = '';
			var isLeader = row["isLeader"];
			if(isLeader == 0){
				returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_preheat_release\')"><a onclick=setLoanLeader("'+row["loanNo"]+'","'+row["supportUser"]+'")>置为领投人</a></security:authorize>';
			}
			
			return returnStr;
		}
	</script>