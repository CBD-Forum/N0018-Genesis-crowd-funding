<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
	<script type="text/javascript" src="<%=path%>/js/authority/resource.js"></script>
	<div class="resourceDiv" style="width: 260px;">
		<ul id="resource" style="overflow: auto;"></ul>
	</div>
	<div id="add">
		<form id="permissionForm"  method="post">
			<input type="hidden" name="id" id="uuid"/>
			<div class="x-form-item" >
				<label class="x-form-item-label">父资源名称:</label>
				<div class="x-form-element">
					<input id="pName" type="text" disabled="disabled"/>
					<input id="pId" type="hidden"  name="pid"/>
				</div>
			</div>
			
			<div class="x-form-item" >
				<label class="x-form-item-label">平台分类:</label>
				<div class="x-form-element">
					<input id="sysTypeId" type="text"  name="sysType" disabled="disabled"/>
				</div>
			</div>
			
			<div class="x-form-item" >
				<label class="x-form-item-label">资源分类:</label>
				<div class="x-form-element">
					<input id="permissionType" name="permissionType" class="easyui-combobox" url="<%=path %>/dictionary/auth_permission_type.html" panelHeight="auto"/>
				</div>
			</div>
			
			<div class="x-form-item" >
				<label class="x-form-item-label">资源Id:</label>
				<div class="x-form-element">
					<input type="text"  name="code"/>
				</div>
			</div>
			
			<div class="x-form-item" >
				<label class="x-form-item-label">资源名称:</label>
				<div class="x-form-element">
					<input type="text"  name="label"/>
				</div>
			</div>
			
			<div class="x-form-item" >
				<label class="x-form-item-label">url:</label>
				<div class="x-form-element">
					<input type="text"  name="url"/>
				</div>
			</div>
			
			<div class="x-form-item" >
				<label class="x-form-item-label">状态:</label>
				<div class="x-form-element">
					<input type="radio" name="status" value="0" style="width: 20px; border: 0; height: auto;"/>启用
					<input type="radio" name="status" value="1" style="width: 20px; border: 0; height: auto;"/>禁用
				</div>
			</div>
			
			<div class="x-form-item" >
				<label class="x-form-item-label">备注:</label>
				<div class="x-form-element">
					<textarea rows="4" id="add_area" name="description" placeholder="描述"></textarea>
				</div>
			</div>
			<div class="x-form-item" >
				<label class="x-form-item-label">顺序:</label>
				<div class="x-form-element">
					<input type="text"  name="seqNum"/>
				</div>
			</div>
			<div class="btndiv">
				<div class="psb" style="margin-top:10px; margin-bottom: 10px;"><a id="saveBtn" class="easyui-linkbutton searchBtn">保存</a><a id="closeBtn" class="easyui-linkbutton searchBtn">取消</a></div>
			</div>
		</form>
	</div>
	<div id="mm" class="easyui-menu" style="width:120px;">
		<div onclick="addPermission()" data-options="iconCls:'icon-add'">添加子节点</div>
		<div onclick="removePermission()" data-options="iconCls:'icon-remove'">删除</div>
		<div onclick="modifyPermission()" data-options="iconCls:'icon-edit'">修改</div>
	</div>