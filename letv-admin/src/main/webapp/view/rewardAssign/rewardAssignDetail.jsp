<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
	var path = "<%=path%>";
</script>
<script type="text/javascript" src="<%=path%>/js/rewardAssign/rewardAssignDetail.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>项目编号:</label><input type="text" name="loanNo" /></div>
			<div><label>收款人:</label><input name="getUserLike" type="text"/></div>
			<div><label>发放开始时间:</label><input name="assignStartTime" class="Wdate" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/></div>
			<div><label>发放结束时间:</label><input type="text" class="Wdate" name="assignEndTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/></div>
			<div><label>投资人:</label><input type="text" name="investor" /></div>
			<div><label>分红次数:</label><input name="bonusNum" type="text"/></div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><input type="reset" class="easyui-linkbutton searchBtn" style="width:104px;height:28px;" value="重置"/></div>
		</div>
	</form>
	
	<!-- 判断权限 -->
	<%-- <security:authorize access="hasPermission(null, 'security.operation.finance_rewardSend_detilList_export')">
		<input type="hidden" id="finance_rewardSend_detilList_export"/>
		<form id="exprotForm" action="<%=path%>/rewardAssign/exportExcel.html" method="get">
			<input id="e_rewardType" name="rewardType" type="hidden"/>
			<input id="e_getUserLike" name="getUserLike" type="hidden"/>
			<input id="e_assignStartTime" name="assignStartTime" type="hidden"/>
			<input id="e_assignEndTime" name="assignEndTime" type="hidden"/>
			<input id="e_investor" name="investor" type="hidden"/>
			<input id="e_loanNo" name="loanNo" type="hidden"/>
		</form>
	</security:authorize> --%>
	
	<!-- 判断权限 -->
	<security:authorize access="hasPermission(null, 'security.operation.finance_rewardSend_detilList_export')">
		<input type="hidden" id="finance_rewardSend_detilList_export"/>
	</security:authorize>
	<table id="assignTable"></table>
	
	
	<script type="text/javascript">
		function operateData(val,row,index){
			var returnStr = '';
			//补发分红
		 	returnStr+='<security:authorize access="hasPermission(null, \'security.operation.rereissueBonus\')"><a onclick=rereissueBonus("'+row.id+'")>补发分红</a></security:authorize>';
			return returnStr;
		}
	</script>
