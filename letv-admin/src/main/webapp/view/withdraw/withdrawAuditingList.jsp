<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="<%=path%>/js/withdraw/withdrawAuditingList.js"></script>
	<form id="list_search">
		<div class="seach_div">
		    <div><label>提现单号:</label><input name="orderId" type="text"/></div>
			<div><label>用户名:</label><input name="userId" type="text"/></div>
			<div><label>手机号:</label><input name="mobile" type="text"/></div>
			<div><label>真实姓名:</label><input name="userName" type="text"/></div>
			<div><label>申请时间:</label><input type="text" name="startApplyTime" id="startApplyTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/> &nbsp;~ <input type="text" name="endApplyTime" id="endApplyTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/><span id="timeSpan" style="color:red"></span></div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><a id="resetBtn" class="easyui-linkbutton searchBtn">重置</a></div>
		</div>
	</form>
	<table id="withdrawTable"></table>
	
	<div id="add">
		<form id="auditingForm" method="post">
			<input type="hidden" name="btnId"/>
			<input type="hidden" name="orderId"/>
			<div class="x-form-item">
				<label class="x-form-item-label">订单号:</label>
				<div class="x-form-element">
					<input type="text" name="orderName" disabled="disabled"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">意见:</label>
				<div class="x-form-element">
					<textarea rows="5" name="auditOpinion" placeholder="意见"></textarea>
				</div>
			</div>
			
			<div class="psb">
				<a id="passedBtn" class="easyui-linkbutton searchBtn">通过</a><a id="refuseBtn" class="easyui-linkbutton searchBtn">拒绝</a><a id="closeBtn" class="easyui-linkbutton searchBtn">取消</a>
			</div>
		</form>
	</div>
	
	<security:authorize access="hasPermission(null, 'security.operation.finance_withdrawAuditing_export')">
		<input type="hidden" id="finance_withdrawAuditing_export"/>
	</security:authorize>
	

	<div id="auditd" style="display: none; overflow: auto; width: 100%;">
		<form id="auditForm" method="post">
		    <input type="hidden" name="auditType" value="operator">
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
					<a id="disAgreeBtn" class="easyui-linkbutton searchBtn" onclick="disAgree('operator_refuse','auditForm')">不同意</a>
	                <a id="closeBtn" class="easyui-linkbutton searchBtn" onclick="closeBtn('auditd')">取消</a>
                </div>
		  </form>
	 </div>
	<div id="finacial_auditd" style="display: none; overflow: auto; width: 100%;">
	    <form id="finacial_auditForm" method="post">
	            <input type="hidden" name="auditType" value="financial">
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
					 <a id="agreeBtn" class="easyui-linkbutton searchBtn" onclick="agree('passed','finacial_auditForm')">同意</a>
					 <a id="disAgreeBtn" class="easyui-linkbutton searchBtn" onclick="disAgree('refuse','finacial_auditForm')">不同意</a>
                     <a id="closeBtn" class="easyui-linkbutton searchBtn" onclick="closeBtn('finacial_auditd')">取消</a>
                </div>
              
		 </form>
	 </div>
	<script type="text/javascript">
		function operateData(val,row,index){
			var returnStr = '';
			if(row.state =='auditing'){
			    returnStr+='<security:authorize access="hasPermission(null, \'security.operation.operator_withdrawAuditing_audit\')"><a onclick=audit("'+row.orderId+'")>运营审核</a></security:authorize>';
			}else if( row.state=='operator_passed'){
				returnStr+='<security:authorize access="hasPermission(null, \'security.operation.finace_withdrawAuditing_audit\')"><a onclick=finacial_auditd("'+row.orderId+'")>财务审核</a></security:authorize>';
			}
			return returnStr;
		}
	</script>