<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="<%=path%>/js/adjust/adjustAccount.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>调账单号:</label><input name="orderId" type="text"/></div>
			<div><label>手机号码:</label><input name="mobile" type="text"/></div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><a id="resetBtn" class="easyui-linkbutton searchBtn">重置</a></div>
		</div>
	</form>
	<table id="adjustAccountTable"></table>
	<div id="add">
		<form id="adjustAccountForm" method="post">
			<input type="hidden" name="id"/>
			<input type="hidden" id="user_id" name="userId"/>
			<div class="x-form-item">
				<label class="x-form-item-label">手机号:</label>
				<div class="x-form-element">
				    <input type="text" name="mobile" id="mobile" placeholder="手机号码"/>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">用户昵称:</label>
				<div class="x-form-element">
					<input type="text" name="nickName" id="nickName" placeholder="用户昵称" disabled="disabled"/>
					<label id="nickName"></label>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">真实姓名:</label>
				<div class="x-form-element">
					<input type="text" name="realName" id="realName" placeholder="真实姓名"  disabled="disabled"/>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">调账类型:</label>
				<div class="x-form-element">
				    <input id="adjustType" type="text" style="height: 24px;" name="adjustType" class="easyui-combobox" url="<%=path %>/dictionary/adjust_type.html" panelHeight="auto"/>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">调整金额:</label>
				<div class="x-form-element">
					<input type="text" name="adjustAmt" placeholder="调整金额"/>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">调账原因:</label>
				<div class="x-form-element">
					<textarea rows="5" name="adjustReason" placeholder="调账原因"></textarea>
				</div>
			</div>
			<div class="psb">
				<a id="saveBtn" class="easyui-linkbutton searchBtn">保存</a><a id="closeBtn" class="easyui-linkbutton searchBtn">取消</a>
			</div>
		</form>
	</div>
	
	<div id="auditWin" style="display: none">
		<form id="adjustAccountAuditForm" method="post">
		    </br></br></br>
		    <input type="hidden" id="audit_id" name='id'>
		    <input type="hidden" id="audit_state" >
			<div class="x-form-item">
				<label class="x-form-item-label">审核意见:</label>
				<div class="x-form-element">
					<textarea rows="5" cols="45" name="auditOpinion" placeholder="审核意见"></textarea>
				</div>
			</div>
			</br></br>
			<div class="psb">
				<a id="passedBtn" class="easyui-linkbutton searchBtn">通过</a>
				<a id="refuseBtn" class="easyui-linkbutton searchBtn">拒绝</a>
				<a id="close1Btn" class="easyui-linkbutton searchBtn">取消</a>
			</div>
		</form>
	</div>	
	
	
	
	
	<security:authorize access="hasPermission(null, 'security.operation.adjust_user_bill_add')">
		<input type="hidden" id="adjust_user_bill_add"/>
	</security:authorize>
	
	<script type="text/javascript">
	//操作按钮
	var add = { 
		     	text: '调账', 
		        iconCls: 'icon-add', 
		        handler: function() { 
		         	showAdd();
		        } 
		      };
	
	var operateBtns=[];
	if ($('#adjust_user_bill_add').length>0) {
		operateBtns.push(add);
	}
	if (operateBtns.length==0) {
		operateBtns=null;
	}
	
	
	function operateData(val,row,index){
		var returnStr = '';
		//审核
		if(row.auditState == "auditing"){
			returnStr+='<security:authorize access="hasPermission(null, \'security.operation.adjust_user_bill_audit\')"><a onclick=auditWin("'+row.id+'")>审核</a></security:authorize>';
		}
		return returnStr;
	}
	</script>