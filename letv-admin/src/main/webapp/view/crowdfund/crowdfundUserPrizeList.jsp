<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
var loanNo = '${loanNo}';
</script>
<script type="text/javascript" src="<%=path%>/js/crowdfund/crowdfundUserPrizeList.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>项目编号:</label><input id="loanNo" name="loanNo" type="text"/></div>
			<div><label>中奖名单:</label>
				<select name="isPrize" >
					<option value="">==-请选择-==</option>
					<option value="1">中奖名单</option>
				</select>
			</div>
			
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><input type="reset" class="easyui-linkbutton searchBtn" style="width:104px;height:28px;" value="重置"/></div>
		</div>
	</form>
	<table id="prizeTable"></table>
	<script type="text/javascript">
	</script>