<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="<%=path%>/js/config/businessConfig.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>名称:</label><input name="displayName" type="text"/></div>
			<div><label>编码:</label><input name="code" type="text"/></div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><a id="resetBtn" class="easyui-linkbutton searchBtn">重置</a></div>
		</div>
	</form>
	<table id="bussConfigTable"></table>
	<div id="add">
		<form id="bussConfigForm" method="post">
			<input type="hidden" name="id"/>
			<div class="x-form-item">
				<label class="x-form-item-label">名称:</label>
				<div class="x-form-element">
					<input type="text" name="displayName" placeholder="名称"/>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">值:</label>
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
	
	<security:authorize access="hasPermission(null, 'security.operation.system_busConfig_add')">
		<input type="hidden" id="system_busConfig_add"/>
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
	
	var operateBtns=[];
	if ($('#system_busConfig_add').length>0) {
		operateBtns.push(add);
	}
	if (operateBtns.length==0) {
		operateBtns=null;
	}
	
	
	function operateData(val,row,index){
		var returnStr = '';
		//修改
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.system_busConfig_modify\')"><a onclick=editWin("'+row.id+'")>修改</a></security:authorize>';
		//删除
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.system_busConfig_delete\')"><a onclick=removeRow("'+row.id+'")>删除</a></security:authorize>';
		return returnStr;
	}
	</script>