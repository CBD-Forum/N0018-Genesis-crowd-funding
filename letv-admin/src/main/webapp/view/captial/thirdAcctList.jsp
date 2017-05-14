<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="<%=path%>/js/captial/thirdAcct.js"></script>
	<table id="thirdAcctTable"></table>
	<span id = "msg" display="false">${result.msg}</span>
    <div id="transfer" class="add">
		<form id="transferForm" target="_blank" method="post" action="<%=path%>/third/queryAcct/sendSysTransfer.html">
			<div class="x-form-item">
			    <label class="x-form-item-label">转出账户:</label>
			    <div class="x-form-element">
			        <input name="outChildAcct" valueField="value"  textField="text" class="easyui-combobox" url="<%=path %>/dictionary/sys_out_third_acct.html"/>
			    </div>
			</div>
			<div class="x-form-item">
			    <label class="x-form-item-label">转入账户:</label>
			    <div class="x-form-element">
			        <input name="inChildAcct"  valueField="value"  textField="text" class="easyui-combobox" url="<%=path %>/dictionary/sys_in_third_acct.html"/>
			    </div>
			</div>
			<div class="x-form-item">
			    <label class="x-form-item-label">转账金额:</label>
			    <div class="x-form-element">
			        <input name="transferAmt" type="text"/>
			    </div>
			</div>
			<div class="psb">
				<a id="saveBtn" class="easyui-linkbutton searchBtn">转账</a><a id="closeBtn" class="easyui-linkbutton searchBtn">取消</a>
			</div>
		</form>
	</div>
	
	<security:authorize access="hasPermission(null, \'security.operation.finance_thirdAccount_transfer\')">
		<input type="hidden" id="finance_thirdAccount_transfer"/>
	</security:authorize>
