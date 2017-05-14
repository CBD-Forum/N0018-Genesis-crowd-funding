<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/webuploader.css"/>
<script type="text/javascript" src="<%=path%>/js/crowdfund/preheat.js"></script>
<script type="text/javascript" src="<%=path%>/js/crowdfund/loanCommon.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/ajaxfileupload.js"></script>

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
	<div id="loanBack">
		<table id="loanBackTable"></table>
	</div>
	<div id="loanAudit">
		<table id="loanAuditTable"></table>
	</div>
	<div id="uploadDiv">
		<form id="uploadform" method="post" style="margin-top:20px;margin-left: -14px">
			<input id="loanNo2" name="loanNo" type="hidden"/>
			<input id="filePath" type="hidden"/><!-- 隐藏file标签 -->
			<input id="fileToUpload" style="display: none" type="file" name="file"><br/> 
			<div class="x-form-item">
				<div class="x-form-element" style="padding:0;text-align:center;line-height:24px;">
					<a style="text-align:center;margin-top:25px;" id="upload" class="easyui-linkbutton searchBtn">上传合同</a>  
				</div>
			</div>
			<div class="x-form-item">
				<div class="x-form-element" >
					<a href="javascript:void(0);" id="fileToDownload" style="margin-left: 20px">查看项目合同</a>
				</div>
			</div>
		</form>
	</div>
	<input type="hidden" id="loanNo"></input>
	<input type="hidden" id="loanType"></input>
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
	<%@ include file="stockback.jsp" %>
	<script type="text/javascript">
	$("#stockback").hide();
	function operateData(val,row,index){
		var returnStr = '';
		//删除项目
		
		if(row.loanType=='stock' || row.loanType=='product'){
			if(row.supportNum==0){
				returnStr+= '<security:authorize access="hasPermission(null, \'security.operation.crowdfund_preheat_delete\')"><a onclick=deleteLoan("'+row["loanNo"]+'")>删除</a></security:authorize>';
			}
		}
		//编辑
		returnStr+= '<security:authorize access="hasPermission(null, \'security.operation.crowdfund_preheat_edit\')"><a onclick=editLoan("'+row["loanNo"]+'","'+row["loanType"]+'")>编辑</a></security:authorize>';
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_preheat_release\')"><a onclick=release("'+row["loanNo"]+'","'+row["prepayAmt"]+'","'+row["loanType"]+'")>发布</a></security:authorize>';
		//returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_funding_support\')"><a onclick=openBackSupport("'+row["loanNo"]+'","'+row["loanType"]+'")>后台投标</a></security:authorize>';
		//returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_preheat_release\')"><a onclick=uploadContract("'+row["loanNo"]+'","'+row["loanContract"]+'")>上传合同</a></security:authorize>';
		
		return returnStr;
	}

	function operateData1(val,row,index){
		var returnStr = '';
		var loanType = row["loanType"];
		if(loanType == 'stock'){
			//项目领投人
			returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_preheat_lead\')"><a onclick=loanLeaderList("'+row["loanNo"]+'")>项目领投人</a></security:authorize>';
			//预购列表
		//	returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_preheat_support\')"><a onclick=preSupportsList("'+row["loanNo"]+'")>预购列表</a></security:authorize>';
			//认购定金列表
			returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_preheat_preorder\')"><a onclick=investSupportList("'+row["loanNo"]+'")>认购列表</a></security:authorize>';
		}
		//项目详情
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_preheat_detail\')"><a onclick=LoanDetail("'+row["loanNo"]+'","'+row["loanType"]+'")>项目详情</a></security:authorize>';
		//审批流程
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_preheat_auditList\')"><a onclick=openAudit("'+row.loanNo+'")>审核流程</a></security:authorize>';
		//预览
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_preheat_preview\')"><a onclick=openPreview("'+row["loanNo"]+'","'+row["loanType"]+'","'+row["loanState"]+'")>预览</a></security:authorize>';
		return returnStr;
	}
	</script>