<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="<%=path%>/js/crowdfund/crowdfundTransferList.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>项目编号:</label><input  name="loanNo" type="text"/></div>
			<div><label>挂牌编号:</label><input  name="transferNo" type="text"/></div>
			<div><label>项目名称:</label><input  name="loanName" type="text"/></div>
			<div><label>项目状态:</label><input id="s_stutas" type="text" style="height: 24px;" name="auditState" class="easyui-combobox" url="<%=path %>/dictionary/transfer_audit_state.html" panelHeight="auto"/></div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><input type="reset" class="easyui-linkbutton searchBtn" style="width:104px;height:28px;" value="重置"/></div>
		</div>
	</form>
	<table id="table"></table>
	
		<div id="add">
		<form id="auditform" method="post" style="margin-top:20px;">
			<input id="id" name="id" type="hidden"/>
			<input id="auditState" name="auditState" type="hidden"/>
			<div class="x-form-item">
				<label class="x-form-item-label">审核意见:</label>
				<div class="x-form-element">
					<textarea id="auditOpinion" name="auditOpinion" style="width:240px;height:50px;"></textarea>
				</div>
			</div>
			
			<div class="psb" style="margin-top:30px;"><a id="pass" class="easyui-linkbutton searchBtn">通过</a><a id="refuse" class="easyui-linkbutton searchBtn">拒绝</a></div>
		</form>
	</div>
	<script type="text/javascript">
	function operateData(val,row,index){
		var returnStr = '';
		//审核
		if(row["auditState"]=='auditing'){
			returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_transfer_pass\')"><a onclick=operate("'+row["id"]+'")>审核</a></security:authorize>';
			//编辑
			returnStr+= '<security:authorize access="hasPermission(null, \'security.operation.crowdfund_transfer_edit\')"><a onclick=edit("'+row["transferNo"]+'")>编辑</a></security:authorize>';
		}
		return returnStr;
	}

	</script>