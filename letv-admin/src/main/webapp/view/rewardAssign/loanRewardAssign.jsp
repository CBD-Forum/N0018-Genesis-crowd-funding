<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
	var path = "<%=path%>";
</script>
<script type="text/javascript" src="<%=path%>/js/rewardAssign/loanRewardAssign.js"></script>
<script type="text/javascript" src="<%=path%>/js/reward/rewardUsedList4Invest.js"></script>
<script type="text/javascript" src="<%=path%>/js/project/loanInvest_list.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>项目编号:</label><input id="s_loanNo" name="loanNo" type="text"/></div>
			<div><label>项目名称:</label><input id="s_loanName" name="loanName" type="text"/></div>
			<div><label>所在省:</label><select id="s_provice" name="loanProvince"><option></option></select></div>
			<div><label>所在市:</label><select id="s_city" name="loanCity"><option></option></select></div>
			<div><label>借款人用户名:</label><input id="s_loan_name" name="loanUser" type="text"/></div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><input type="reset" class="easyui-linkbutton searchBtn" style="width:104px;height:28px;" value="重置"/></div>
		</div>
	</form>
	<table id="progectTable"></table>
	
	<div id="loanReward">
		<table id="loanRewardTable"></table>
	</div>
	
	<!-- 投资奖励发放弹框 -->
	<div id="loanWin" class="add" style="padding:20px;">
		占投资金额奖励比例（不能超过10%）<input id="loanAssInput" type="text" style="width:80px;margin-left:10px;"/>%
		<a class="easyui-linkbutton searchBtn" id="loanAssBtn" style="margin-left:10px;">批量发放</a>
		<div class="inputf"></div>
		<input name="loanAssInput" type="hidden"/>
	</div>
	
	<!-- 奖励发放明细 -->
	<div id="assignDetWin">
		<table id="assDetTable"></table>
	</div>
	
	<security:authorize access="hasPermission(null, 'security.operation.finance_rewardSend_export')">
		<input type="hidden" id="finance_rewardSend_export"/>
	</security:authorize>
	
	<script type="text/javascript">
	function operateInvestReward(val,row,index){
		if(row.investRewardAmt == 0){
			return '<security:authorize access="hasPermission(null, \'security.operation.finance_rewardSend_invest\')"><a id="assignInvestRewardBtn" onclick=assignInvestReward("'+row.loanNo+'")>发放</a></security:authorize>';
		}else{
			return '已发放';
		}
	}
	function operateDRecommendReward(val,row,index){
		if(row.directRecommendRewardAmt == 0){
			return '<security:authorize access="hasPermission(null, \'security.operation.finance_rewardSend_directRecommend\')"><a id="assignDRecommendRewardBtn" onclick=assignDRecommendReward("'+row.loanNo+'")>发放</a></security:authorize>';
		}else{
			return '已发放';
		}
	}

	function operateINDRecommendReward(val,row,index){
		if(row.indirectRecommendRewardAmt == 0){
			return '<security:authorize access="hasPermission(null, \'security.operation.finance_rewardSend_indirectRecommend\')"><a id="assignIndRecommendRewardBtn" onclick=assignINDRecommendReward("'+row.loanNo+'")>发放</a></security:authorize>';
		}else{
			return '已发放';
		}
	}
	function operateData(val,row,index){
		var returnStr = '';
		//奖励发放明细
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.finance_rewardSend_detil\')"><a onclick=openInvest("'+row.loanNo+'")>奖励发放明细</a></security:authorize>';
		//投资记录
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.finance_rewardSend_investRecord\')"><a onclick=openInvest("'+row.loanNo+'")>投资记录</a></security:authorize>';
		return returnStr;
	}
	</script>
