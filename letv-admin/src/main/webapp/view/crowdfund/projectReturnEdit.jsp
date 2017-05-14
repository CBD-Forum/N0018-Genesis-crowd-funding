<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
var loanNo = '${loanNo}';
</script>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/webuploader.css"/>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/crowdfund/projectReturnEdit.js"></script>
	<div id="add" style="display: block; overflow: auto; width: 100%;">
		<form id="userForm" method="post" style="margin-top:20px;">
			
			<div class="x-form-item">
				<label class="x-form-item-label">项目方出资总额:</label>
				<div class="x-form-element">
					<input id="projectFinanceAmt" name="projectFinanceAmt" type="text" />元
				</div>
			</div>
			
			
			<div class="x-form-item">
				<label class="x-form-item-label">投资人出资金额:</label>
				<div class="x-form-element">
					<input name="investFinanceAmt" id="investFinanceAmt" type="text"/>元
				</div>
			</div>
			
			
			<div class="x-form-item">
				<label class="x-form-item-label">项目方分红比例:</label>
				<div class="x-form-element">
					<input name="projectBonusRatio" id="projectBonusRatio" type="text"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">投资方分红比例:</label>
				<div class="x-form-element">
					<input name="investBonusRatio" id="investBonusRatio" type="text"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">融资总份数:</label>
				<div class="x-form-element">
					<input name="financeNum" id="financeNum" type="text"/>份
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">单笔投资人最低投资金额:</label>
				<div class="x-form-element">
					<input name="miniInvestAmt" id="miniInvestAmt" type="text"/>元
				</div>
			</div>
		</form>
	</div>
	<div class="psb" style="margin-top:30px;">
		<a id="saveBtn" class="easyui-linkbutton searchBtn">确定</a>
		<a id="returnBtn" href="javascript:history.go(-1);" class="easyui-linkbutton searchBtn">返回</a>
	</div>
	