<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="<%=path%>/js/withdraw/systemwithdraw.js"></script>
<style>
#add{width:auto !important;}
</style>
	<div id="add" style="display: block; overflow: auto; padding: 0;">
		<div id="baseInfo" style="padding-top:20px;padding-bottom:20px;border:1px solid #00b7ee;">  
		<form id="form" method="post">
			
				<div class="x-form-item">
						<label class="x-form-item-label">可提现金额:</label>
						<div class="x-form-element">
							<span id="withdraw_amt"></span>
						</div>
					</div>
			<div class="x-form-item">
				<label class="x-form-item-label">提现金额:</label>
				<div class="x-form-element">
					<input name="amt" id="amt" type="text" class="easyui-numberbox" precision="2" required="true" missingMessage="请填写提现金额" />
				</div>
			</div>
			
			<div class="psb">
				<a id="saveBtn" class="easyui-linkbutton searchBtn" >保存</a>
			</div>
		</form>
		</div>
		</div>
