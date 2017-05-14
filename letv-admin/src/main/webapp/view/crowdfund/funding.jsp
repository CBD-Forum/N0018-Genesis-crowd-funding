<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="<%=path%>/js/crowdfund/funding.js"></script>
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
	
	<input type="hidden" id="loanNo"></input>
	<input type="hidden" id="loanType"></input>
	<!-- 实物投资 -->
	<div id="supportback">
		<table id="backTable"></table>
		<br/>
		<div class="x-form-item">
			<label class="x-form-item-label">投资人:</label>
			<div class="x-form-element">
				<input type="hidden" id="investor" name="investor" />
				<input type="text" id="investorName" name="investorName" readOnly="true"/>
				<a id="getInvestorBtn" style="width: 80px; height:28px; margin-left: -2px; margin-top:-3px; border-left: 0;" class="easyui-linkbutton">选择</a>
			</div>
		</div>
		<div class="x-form-item">
			<label class="x-form-item-label">收货地址:</label>
			<div class="x-form-element">
			    <select id="post_address" name="postAddressNo" style="width:200px"><option></option></select>
			</div>
		</div>
		<div class="psb" style="margin-top:30px;">
		    <a id="supportbtn" class="easyui-linkbutton searchBtn">投资</a>
			<a id="supportcancel" class="easyui-linkbutton searchBtn">取消</a>
		</div>
	</div>
	
	<!-- 股权投资 -->
	<div id="stockSupportback">
	   <br/>
		<div class="x-form-item" id="sDate">
				<label class="x-form-item-label" style="width:150px">项目方出资总额:</label>
				<div class="x-form-element">
					<label id="projectFinanceAmt"></label>元
				</div>
			</div>
			
			<div class="x-form-item" id="slevel">
				<label class="x-form-item-label" style="width:150px">投资人出资金额:</label>
				<div class="x-form-element">
					<label id="investFinanceAmt"></label>元
				</div>
			</div>
			
			<div class="x-form-item" id="slevel">
				<label class="x-form-item-label" style="width:150px">项目方分红比例:</label>
				<div class="x-form-element">
					<label id="projectBonusRatio"></label>%
				</div>
			</div>
			
			<div class="x-form-item" id="slevel">
				<label class="x-form-item-label" style="width:150px">投资方分红比例:</label>
				<div class="x-form-element">
					<label id="investBonusRatio"></label>%
				</div>
			</div>
			
			<div class="x-form-item" id="slevel">
				<label class="x-form-item-label" style="width:150px">融资总份数:</label>
				<div class="x-form-element">
					<label id="financeNum"></label>份
				</div>
			</div>
			
			<div class="x-form-item" id="slevel">
				<label class="x-form-item-label" style="width:150px">单笔投资人最低投资金额:</label>
				<div class="x-form-element">
					<label id="miniInvestAmt"></label>元
				</div>
			</div>
		<br/>
		<div class="x-form-item" id="slevel">
				<label class="x-form-item-label" style="width:150px">剩余份数:</label>
				<div class="x-form-element">
					<label id="remainNum"></label>份
				</div>
			</div>
		<div class="x-form-item" id="sDate">
				<label class="x-form-item-label" style="width:150px">用户名:</label>
				<div class="x-form-element">
					<input type="hidden" id="investor2" name="investor" />
				<input type="text" id="investorName2" name="investorName" readOnly="true"/>
				<a id="getInvestorBtn2" style="width: 80px; height:28px; margin-left: -2px; margin-top:-3px; border-left: 0;" class="easyui-linkbutton">选择</a>
				</div>
		</div>
		<div class="x-form-item" id="slevel">
				<label class="x-form-item-label" style="width:150px">认购份数:</label>
				<div class="x-form-element">
					<input type="text" name="buyNum" id="buyNum"/>
				</div>
		</div>
		<div class="psb" style="margin-top:30px;">
		    <a id="stockSupportbtn" class="easyui-linkbutton searchBtn">投资</a>
			<a id="stockSupportcancel" class="easyui-linkbutton searchBtn">取消</a>
		</div>
	</div>
	
	
	<!-- 录入进展 -->
	<div id="addProgress">
		<form id="addProgressform" method="post" style="margin-top:20px;">
			<input id="loanNo2" name="loanNo" type="hidden"/>
			
			<div class="x-form-item">
				<label class="x-form-item-label">项目进展:</label>
				<div class="x-form-element">
					<textarea id="enterContent" name="enterContent" style="width:240px;height:50px;"></textarea>
				</div>
			</div>
			
			<div class="psb" style="margin-top:30px;"><a id="saveProgress" class="easyui-linkbutton searchBtn">保存</a><a id="cancelProgress" class="easyui-linkbutton searchBtn">取消</a></div>
		</form>
	</div>
	
	<script type="text/javascript">
	function operateData(val,row,index){
		var returnStr = '';
		//编辑(修改项目等级)
		returnStr+= '<security:authorize access="hasPermission(null, \'security.operation.crowdfund_funding_edit\')"><a onclick=editLoan("'+row["loanNo"]+'","'+row["loanType"]+'")>编辑</a></security:authorize>';
		//returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_funding_support\')"><a onclick=openBackSupport("'+row["loanNo"]+'","'+row["loanType"]+'")>后台投标</a></security:authorize>';
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_funding_addProgress\')"><a href='+path+'/crowdfund.progressList.html?loanNo='+row["loanNo"]+'>项目进展</a></security:authorize>';
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_funding_end\')"><a onclick=endLoan("'+row["loanNo"]+'")>手动结束</a></security:authorize>';
		return returnStr;
	}

	function operateData1(val,row,index){
		var returnStr = '';
		//支持列表
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_funding_invest\')"><a onclick=supportList("'+row["loanNo"]+'","'+row["loanType"]+'")>支持列表</a></security:authorize>';
		//项目回报
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_funding_back\')"><a onclick=openBackSet("'+row["loanNo"]+'","'+row["loanType"]+'")>项目回报</a></security:authorize>';
		//审批流程
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_funding_auditList\')"><a onclick=openAudit("'+row.loanNo+'")>审核流程</a></security:authorize>';
		//项目详情
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_funding_detail\')"><a onclick=LoanDetail("'+row["loanNo"]+'","'+row["loanType"]+'")>项目详情</a></security:authorize>';
		return returnStr;
	}
	</script>