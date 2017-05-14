<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
	var path = "<%=path%>";
	var loanNo = '${loanNo}'
</script>
<script type="text/javascript" src="<%=path%>/js/crowdfund/bonusList.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>收款人:</label><input name="getUserLike" type="text"/></div>
			<div><label>发放开始时间:</label><input name="assignStartTime" class="Wdate" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/></div>
			<div><label>发放结束时间:</label><input type="text" class="Wdate" name="assignEndTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/></div>
		    <div><label>付款人:</label><input type="text" name="payUser" /></div>  
			<div><label>分红次数:</label><input name="bonusNum" type="text"/></div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><input type="reset" class="easyui-linkbutton searchBtn" style="width:104px;height:28px;" value="重置"/></div>
		</div>
	</form>
	<table id="assignTable"></table>
