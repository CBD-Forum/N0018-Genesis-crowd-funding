<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
var loanNo = '${loanNo}';
</script>
<script type="text/javascript" src="<%=path%>/js/crowdfund/interviewList.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>项目编号:</label><input id="loanNoLike" name="loanNoLike" type="text"/></div>
			<div><label>项目发起人:</label><input id="s_support_user" name="receiveUserLike" type="text"/></div>
			<div><label>约谈发起人:</label><input id="s_support_user_name" name="applyUserLike" type="text"/></div>
			<div><label>状态:</label>
			<input id="state" type="text" style="height: 24px;" name="state" class="easyui-combobox" url="<%=path %>/dictionary/cd_interview_state.html" panelHeight="auto"/>
			</div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><input type="reset" class="easyui-linkbutton searchBtn" style="width:104px;height:28px;" value="重置"/></div>
		</div>
	</form>
	<table id="progectTable"></table>
	<script type="text/javascript">
	function operateData(val,row,index){
		var returnStr = '';
		var state = row["state"];
		if(state == 'processing'){
			returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_interview_process\')"><a onclick=operate("'+row["id"]+'")>处理</a></security:authorize>';
		}
		return returnStr;
	}
	</script>