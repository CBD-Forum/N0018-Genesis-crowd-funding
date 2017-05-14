<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="<%=path%>/js/withdraw/systemWithdrawAuditingList.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>申请时间:</label><input type="text" name="startApplyTime" id="startApplyTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/> &nbsp;~ <input type="text" name="endApplyTime" id="endApplyTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/><span id="timeSpan" style="color:red"></span></div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><a id="resetBtn" class="easyui-linkbutton searchBtn">重置</a></div>
		</div>
	</form>
	<table id="systemwithdrawTable"></table>
	
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
								<a id="agreeBtn" class="easyui-linkbutton searchBtn" onclick="agree('passed','finacial_auditForm')">同意</a>
								<a id="disAgreeBtn" class="easyui-linkbutton searchBtn" onclick="disAgree('refuse','finacial_auditForm')">不同意</a>
	                     		<a id="closeBtn" class="easyui-linkbutton searchBtn" onclick="closeBtn('finacial_auditd')">取消</a>
	                     
	                        </div>
				</form>
			</div>
	<script type="text/javascript">
		function operateData(val,row,index){
			var returnStr = '';
			 if( row.state=='auditing'){
				returnStr+='<security:authorize access="hasPermission(null, \'security.operation.system_withdraw_auditing\')"><a onclick=finacial_auditd("'+row.orderId+'")>财务审核</a></security:authorize>';
			}
			return returnStr;
		}
	</script>