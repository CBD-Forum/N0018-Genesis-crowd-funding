<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="<%=path%>/js/log/auditLogList.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>用户名:</label><input name="userId" type="text"/></div>
			<div><label>操作模块:</label><input id="operateModel" name="operateModel" class="easyui-combobox" url="<%=path %>/dictionary/audit_operate_model.html"/></div>
			<div><label>操作类型:</label><input id="operateType" name="operateType" class="easyui-combobox" url="<%=path %>/dictionary/audit_operate_type.html"/></div>
			<div><label>操作结果:</label>
				<select name="operateResult">
		        	<option value="">-=请选择=-</option>
		        	<option value="success">成功</option>
		        	<option value="fail">失败</option>
		        </select>
			</div>
			<div class="psb" style="float:right;">
				<a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a>
				<a id="resetBtn" class="easyui-linkbutton searchBtn">重置</a>
			</div>
		</div>
	</form>
	
	<table id="auditLogTable"></table>
	
	<!-- 判断权限 -->
	<!-- 添加 -->
	<security:authorize access="hasPermission(null, \'security.operation.user_merchant_add\')">
		<input type="hidden" id="user_merchant_add"/>
	</security:authorize>
	
	
	<script type="text/javascript">
	//添加操作列
	function operateData(val,row,index){
		var returnStr='';
		//判断是否用【详细】的权限
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.user_merchant_detail\')"><a  onClick=showUserDetail("'+row.id+'")>详细</a></security:authorize>';
		//判断是否有【编辑】的权限
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.user_merchant_modify\')"><a href="'+path+'/merchant.merchantadd.html?id='+row.id+'">编辑</a></security:authorize>';
		//判断是否有 【重置密码】 的权限
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.user_merchant_resetPwd\')"><a onClick=resetPassword("'+row.id+'")>重置密码</a></security:authorize>';
		//判断是否有 【启用/禁用】 的权限
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.user_merchant_changeStatus\')">';
		if (row.status=='normal') {
			returnStr+='<a onClick=modifySecurity("'+row.userId+'","disable")>禁用</a>';
		}else{
			returnStr+='<a onClick=modifySecurity("'+row.userId+'","normal")>启用</a>';
		}
		returnStr+='</security:authorize>';
		//【调整授信】
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.user_merchant_adjust\')"><a onclick=showAdjustForm("'+row.userId+'","'+row.creditLimit+'","'+row.secuDepositScale+'")>调整授信</a></security:authorize>';
		//【企业开户】
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.user_merchant_register\')"><a onclick=openAccount(\''+JSON.stringify(row)+'\')>企业开户</a></security:authorize>';
		return returnStr;
	}
	</script>
