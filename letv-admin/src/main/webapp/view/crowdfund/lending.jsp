<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="<%=path%>/js/crowdfund/lending.js"></script>
<script type="text/javascript" src="<%=path%>/js/crowdfund/loanCommon.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>项目编号:</label><input id="s_loanNo" name="loanNoLike" type="text"/></div>
			<div><label>项目名称:</label><input id="s_loanName" name="loanName" type="text"/></div>
			<div><label>发起人:</label><input id="s_loan_name" name="loanUserLike" type="text"/></div>
			<div><label>发起人真实姓名:</label><input id="s_loan_realname" name="loanUserNameLike" type="text"/></div>
			<div><label>项目类型:</label><input id="s_stutas" type="text" style="height: 24px;" name="loanType" class="easyui-combobox" url="<%=path %>/dictionary/crowdFundType.html" panelHeight="auto"/></div>
			<div><label>所在省:</label><select id="s_provice" name="province"><option></option></select></div>
			<div><label>所在市:</label><select id="s_city" name="city"><option></option></select></div>
			<div><label>所在县:</label><select id="s_county" name="county"><option></option></select></div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><input type="reset" class="easyui-linkbutton searchBtn" style="width:104px;height:28px;" value="重置"/></div>
		</div>
	</form>
	<table id="progectTable"></table>
	<div id="loanAudit">
		<table id="loanAuditTable"></table>
	</div>
	
	<div id="loanBack">
		<table id="loanBackTable"></table>
	</div>
	
	<!-- 延期 -->
	<div id="deferLoan" class="add">
		<form id="deferLoanForm" method="post">
			<input type="hidden" name="id"/>
			<div class="x-form-item">
				<label class="x-form-item-label">项目编号:</label>
				<div class="x-form-element">
					<label name="loanNo" id="defer_loanNo"></label>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">项目名称:</label>
				<div class="x-form-element">
					<label name="loanName" id="defer_loanName"></label>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">融资截至时间:</label>
				<div class="x-form-element">
					<label id="defer_loanName"></label>
					<input type="text" name="fundEndTime" id="fundEndTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/>
				</div>
			</div>
			
			<div class="psb" style="margin-top:30px;">
				<a id="deferBtn" class="easyui-linkbutton searchBtn">延期</a>
				<a id="deferCloseBtn" class="easyui-linkbutton searchBtn">取消</a>
			</div>
		</form>
	</div>
	
	
	<div id="modifyLoanLevel">
		<form id="modifyLoanLevelForm" method="post" style="margin-top:20px;">
			<input id="loanNo2" name="loanNo" type="hidden"/>
			
			<div class="x-form-item">
				<label class="x-form-item-label">项目等级:</label>
				<div class="x-form-element">
							 <select id="loanLevel" name="loanLevel" style="width:243px">
							 <option value="1">1</option>
							  <option value="2">2</option>
							   <option value="3">3</option>
							    <option value="4">4</option>
							     <option value="5">5</option>
							 </select>
						</div>
			</div>
			
			<div class="psb" style="margin-top:30px;"><a  onclick="modifyLoanLevel();" class="easyui-linkbutton searchBtn">保存</a><a  onclick="cancelModifyLoanLevel();" class="easyui-linkbutton searchBtn">取消</a></div>
		</form>
	</div>
	<%@ include file="stockback.jsp" %>
	<script type="text/javascript">
	$("#stockback").hide();
	function operateData(val,row,index){
		var returnStr = '';
		//returnStr+= '<security:authorize access="hasPermission(null, \'security.operation.crowdfund_loanLevel_modify\')"><a onclick=showModifyLoanLevel("'+row["loanNo"]+'")>修改项目等级</a></security:authorize>'; 
		returnStr+= '<security:authorize access="hasPermission(null, \'security.operation.crowdfund_lending_lend\')"><a onclick=lendLoan("'+row["loanNo"]+'")>放款</a></security:authorize>'; 
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_lending_cancel\')"><a onclick=cancelLoan("'+row["loanNo"]+'")>流标</a></security:authorize>';
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_lending_delay\')"><a onclick=openDefer("'+row["loanNo"]+'","'+row["loanName"]+'")>延期</a></security:authorize>';
/* 		if(row["loanType"]=='product'){
			returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_user_prize\')"><a href="'+path+'/crowdfund.crowdfundUserPrizeList.html?loanNo='+row["loanNo"]+'">抽奖列表</a></security:authorize>';	
		} */
		//returnStr+='<a onclick=openDefer("'+row["loanNo"]+'","'+row["loanName"]+'")>延期</a>';
		return returnStr;
	}

	function operateData1(val,row,index){
		var returnStr = '';
		//支持列表
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_lending_support\')"><a onclick=supportList("'+row["loanNo"]+'","'+row["loanType"]+'")>支持列表</a></security:authorize>';
		//项目详情
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_lending_detail\')"><a onclick=LoanDetail("'+row["loanNo"]+'","'+row["loanType"]+'")>项目详情</a></security:authorize>';
		//项目回报
		if("stock"!=row["loanType"]){
			returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_lending_back\')"><a onclick=openBackSet("'+row["loanNo"]+'","'+row["loanType"]+'")>项目回报</a></security:authorize>';
		}//审批流程
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_lending_auditList\')"><a onclick=openAudit("'+row.loanNo+'")>审核流程</a></security:authorize>';
		return returnStr;
	}
	</script>