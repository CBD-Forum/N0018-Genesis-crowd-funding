<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
	var path = "<%=path%>";
</script>
<script type="text/javascript" src="<%=path%>/js/captial/userErrorCaptial.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>用户名:</label><input name="userId" type="text"/></div>
			<div><label>状态:</label><input name="auditState" class="easyui-combobox" url="<%=path %>/dictionary/adjust_audit_state.html" panelHeight="auto"/></div>
			<div><label>交易方向:</label><input name="adjustType" class="easyui-combobox" url="<%=path %>/dictionary/trade_direction.html" panelHeight="auto"/></div>
			<div><label>申请时间:</label><input name="applyStartTime" type="text" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/>&nbsp;~<input name="applyEndTime" type="text" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/></div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><a id="resetBtn" class="easyui-linkbutton searchBtn">重置</a></div>
		</div>
	</form>
	<table id="captialTable"></table>
	<div id="add">
		<form id="auditform" method="post" style="margin-top:20px;">
			<input id="loanNo" name="loanNo" type="hidden"/>
			<div class="x-form-item">
				<label class="x-form-item-label" style="position:relative;top:15px;">审核意见:</label>
				<div class="x-form-element">
					<textarea id="auditOpinion" name="auditOpinion" style="width:240px;height:50px;"></textarea>
					<div class="inputf"></span>
				</div>
			</div>
			
			<div class="psb" style="margin-top:30px;">
				<a id="pass" class="easyui-linkbutton searchBtn">通过</a>
				<a id="refuse" class="easyui-linkbutton searchBtn">拒绝</a>
			</div>
		</form>
	</div>
	
	<div id="apply" class="add">
		<form id="sysConfigForm" method="post">
			<input type="hidden" name="id"/>
			<div class="x-form-item">
				<label class="x-form-item-label">用户名:</label>
				<div class="x-form-element">
					<input type="text" name="userId" id="userId" placeholder="用户名"/>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">真实姓名:</label>
				<div class="x-form-element">
					<label name="realName" id="realName"></label>
					<label name="tipmsg" id="tipmsg"></label>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">交易方向:</label>
				<div class="x-form-element">
					<select name="adjustType" id="adjustType">
					<option value="in">收入</option>
					<option value="out">支出</option>
					</select>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">金额:</label>
				<div class="x-form-element">
					<input type="text" name="adjustAmt" id="adjustAmt" placeholder="金额"/>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">说明:</label>
				<div class="x-form-element">
					<textarea rows="5" name="auditOpinion" id="auditOpinion" placeholder="描述"></textarea>
				</div>
			</div>
			
			<div class="psb">
				<a id="submitBtn" class="easyui-linkbutton searchBtn">保存</a><a id="closeBtn" class="easyui-linkbutton searchBtn">取消</a>
			</div>
		</form>
	</div>
	
	<security:authorize access="hasPermission(null, 'security.operation.finance_exceptionAccount_submit')">
		<input type="hidden" id="finance_exceptionAccount_submit"/>
	</security:authorize>
	<script type="text/javascript">
		function operateData(val,row,index){
			//审核 	
			var returnStr= '';
			var state = row.auditState;
			if(state == 'auditing'){
			  returnStr+='<security:authorize access="hasPermission(null, \'security.operation.finance_exceptionAccount_audit\')"><a onclick=showOpeDialog(\"'+row.id+'\")>审核</a></security:authorize>';
			}
			return returnStr;
		}
	</script>
	