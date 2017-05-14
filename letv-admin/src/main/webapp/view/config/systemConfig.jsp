<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="<%=path%>/js/config/systemConfig.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>名称:</label><input name="displayName" type="text"/></div>
			<div><label>编码:</label><input name="code" type="text"/></div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><a id="resetBtn" class="easyui-linkbutton searchBtn">重置</a></div>
		</div>
	</form>
	<table id="sysConfigTable"></table>
	<div id="add">
		<form id="sysConfigForm" method="post">
			<input type="hidden" name="id"/>
			<div class="x-form-item">
				<label class="x-form-item-label">名称:</label>
				<div class="x-form-element">
					<input type="text" name="displayName" placeholder="名称"/>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">编码:</label>
				<div class="x-form-element">
					<input type="text" name="code" placeholder="编码"/>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">描述:</label>
				<div class="x-form-element">
					<textarea rows="3" name="description" placeholder="描述"></textarea>
				</div>
			</div>
			
			<div class="psb">
				<a id="saveBtn" class="easyui-linkbutton searchBtn">保存</a><a id="closeBtn" class="easyui-linkbutton searchBtn">取消</a>
			</div>
		</form>
	</div>
	
	<security:authorize access="hasPermission(null, 'security.operation.system_sysConfig_add')">
		<input type="hidden" id="system_sysConfig_add"/>
	</security:authorize>
	<security:authorize access="hasPermission(null, 'security.operation.system_sysConfig_reloadCache')">
		<input type="hidden" id="system_sysConfig_reloadCache"/>
	</security:authorize>
	
	<script type="text/javascript">
	//操作按钮
	var add = { 
		     	text: '添加', 
		        iconCls: 'icon-add', 
		        handler: function() { 
		         	showAdd();
		        } 
		      };
	var reload = { 
		     	text: '重新加载缓存', 
		        iconCls: 'icon-add', 
		        handler: function() { 
		        	reloadCache();
		        } 
		      };
	
	var operateBtns=[];
	if ($('#system_sysConfig_add').length>0) {
		operateBtns.push(add);
	}
	if ($('#system_sysConfig_reloadCache').length>0) {
		operateBtns.push(reload);
	}
	if (operateBtns.length==0) {
		operateBtns=null;
	}
	
	
	function operateData(val,row,index){
		//if (row.displayName.indexOf('email_')==0 || row.displayName.indexOf('sms_')==0) {
		//	return '';
		//}else{
			var returnStr = '';
			//修改
			returnStr+='<security:authorize access="hasPermission(null, \'security.operation.system_sysConfig_modify\')"><a onclick=editWin("'+row.id+'")>修改</a></security:authorize>';
			//删除
			returnStr+='<security:authorize access="hasPermission(null, \'security.operation.system_sysConfig_delete\')"><a onclick=removeRow("'+row.id+'")>删除</a></security:authorize>';
			return returnStr;
		//}
	}
	</script>