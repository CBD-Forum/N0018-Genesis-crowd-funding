<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>

<script type="text/javascript" src="<%=path%>/js/dictionary/dictionary.js"></script>

	<form id="list_search">
		<div class="seach_div">
			<div><label>类型编码:</label><input id="typeCode" name="typeCode" /></div>
			<div><label>类型名称:</label><input id="typeName" class="easyui-combobox" name="typeName"
							url="<%=path %>/dictionary/getAllType.html" valueField="typeName"
							 textField="typeName" /></div>
			<div><label>编码:</label><input name="code" type="text"/></div>
			<div><label>名称:</label><input name="displayName" type="text"/></div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><a id="resetBtn" class="easyui-linkbutton searchBtn">重置</a></div>
		</div>
	</form>
	<table id="dictionary"></table>
	<div id="edit" class="add">
		<form id="dicForm" action="" method="post">
			<input type="hidden" name="id" />
			<div class="x-form-item" >
				<label class="x-form-item-label">类型编码:</label>
				<div class="x-form-element">
					<input type="text" id="a_typeCode" name="typeCode" placeholder="类型编码"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">类型名称:</label>
				<div class="x-form-element">
					<input type="text" id="a_type" name="typeName" placeholder="类型名称"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">编码:</label>
				<div class="x-form-element">
					<input type="text" id="a_code" name="code" placeholder="编码"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">名称:</label>
				<div class="x-form-element">
					<input type="text" id="a_name" name="displayName" placeholder="名称"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">描述:</label>
				<div class="x-form-element">
					<textarea rows="5" id="add_area" name="description" placeholder="描述"></textarea>
				</div>
			</div>
			
			<div class="psb" style="margin-top:10px; margin-bottom: 10px;"><a id="saveBtn" class="easyui-linkbutton searchBtn">保存</a><a id="closeBtn" class="easyui-linkbutton searchBtn">取消</a></div>
		</form>
	</div>
	
	<!-- 判断权限 -->
	
	<!-- 添加 -->
	<security:authorize access="hasPermission(null, \'security.operation.system_dictionary_add\')">
		<input type="hidden" id="system_dictionary_add" />
	</security:authorize>
	
	<script type="text/javascript">
	function operateData(val, row, index) {
		var returnStr = '';
		//修改
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.system_dictionary_modify\')"><a onclick=editWin("' + row.id + '")>修改</a></security:authorize>';
		//删除
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.system_dictionary_delete\')"><a onclick=removeDic("'+ row.id + '")>删除</a></security:authorize>';
		return returnStr;
	}
	</script>
