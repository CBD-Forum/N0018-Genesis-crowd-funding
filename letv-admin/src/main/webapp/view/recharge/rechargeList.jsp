<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="<%=path%>/js/recharge/rechargeList.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>充值用户:</label><input name="userId" type="text"/></div>
			<div><label>充值状态:</label><input id="state" name="state" class="easyui-combobox" url="<%=path %>/dictionary/recharge_state.html" panelHeight="auto"/></div>
			<%-- <div><label>区块链交易状态:</label><input id="blockChainState" name="state" class="easyui-combobox" url="<%=path %>/dictionary/block_chain_state.html" panelHeight="auto"/></div> --%>
			<div><label>充值时间:</label><input type="text" name="startCreateTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/> &nbsp;~ <input type="text" name="endCreateTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/></div>
			<div><label>到账时间:</label><input type="text" name="startCompleteTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/> &nbsp;~ <input type="text" name="endCompleteTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/></div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><a id="resetBtn" class="easyui-linkbutton searchBtn">重置</a></div>
		</div>
	</form>
	
	<security:authorize access="hasPermission(null, 'security.operation.finance_user_export')">
		<input type="hidden" id="finance_user_export"/>
	</security:authorize>
	
	<table id="rechargeTable"></table>
	<div id="auditd" style="display: none; overflow: auto; width: 100%;">
					<form id="auditForm" method="post">
				<div class="x-form-item">
				</div>
				              <div class="x-form-item">
							    <label class="x-form-item-label">审核意见:</label>
							    <div class="x-form-element">
							        <textarea rows="8" cols="40" name="operatorOpinion" id="auditOpinion" class="easyui-validatebox" data-options="required:true" ></textarea>
							    </div>
							</div>
							
							<div class="x-form-item">
							    <div class="x-form-element">
							        <input name="operator" type="hidden"/>
							    </div>
							</div>
							<div class="x-form-item">
							    <div class="x-form-element">
							        <input name="operatorAuditTime" type="hidden"/>
							        <input id="operator_orderId" name="orderId" type="hidden"/>
							        <input id="operator_state" name="state" type="hidden"/>
							    </div>
							</div>		
							
							<div class="psb" style="margin-top:5px;">
								<a id="agreeBtn" class="easyui-linkbutton searchBtn" onclick="agree('operator_passed','auditForm')">同意</a>
								<a id="disAgreeBtn" class="easyui-linkbutton searchBtn" onclick="disAgree('recharge_yy_refuse','auditForm')">不同意</a>
	                     		<a id="closeBtn" class="easyui-linkbutton searchBtn" onclick="closeBtn('auditd')">取消</a>
	                     
	                        </div>
				</form>
				</div>
	<div id="finacial_auditd" style="display: none; overflow: auto; width: 100%;">
					<form id="finacial_auditForm" method="post">
				<div class="x-form-item">
				</div>
				              <div class="x-form-item">
							    <label class="x-form-item-label">审核意见:</label>
							    <div class="x-form-element">
							        <textarea rows="8" cols="40" name="financialOpinion" id="financialOpinion" class="easyui-validatebox" data-options="required:true" ></textarea>
							    </div>
							</div>
							
							<div class="x-form-item">
							    <div class="x-form-element">
							        <input name="financial_auditor" type="hidden"/>
							    </div>
							</div>
							<div class="x-form-item">
							    <div class="x-form-element">
							        <input name="financialAuditTime" type="hidden"/>
							        <input id="financal_state" name="state" type="hidden"/>
							       <input id="financal_orderId" name="orderId" type="hidden"/>
							        
							    </div>
							</div>		
							
							<div class="psb" style="margin-top:5px;">
								<a id="agreeBtn" class="easyui-linkbutton searchBtn" onclick="agree('finaical_passed','finacial_auditForm')">同意</a>
								<a id="disAgreeBtn" class="easyui-linkbutton searchBtn" onclick="disAgree('recharge_cw_refuse','finacial_auditForm')">不同意</a>
	                     		<a id="closeBtn" class="easyui-linkbutton searchBtn" onclick="closeBtn('finacial_auditd')">取消</a>
	                     
	                        </div>
				</form>
				</div>
	<script type="text/javascript">
<!--
function operateData(val,row,index){
	var returnStr='';
	//判断是否用【详细】的权限
	//通过
	if(row.state == 'recharge_success_auditing'){
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.operator_recharge_audit\')"><a onClick=audit("'+row.orderId+'")>运营审核</a></security:authorize>';
	}else if(row.state=='operator_passed'){
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.finance_recharge_audit\')"><a onClick=finacial_auditd("'+row.orderId+'")>财务审核</a></security:authorize>';
	
	}
	return returnStr;
}
//-->
</script>
	