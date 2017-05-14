<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="<%=path%>/js/message/messageTemplate.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>模版名称:</label><input name="tplName" type="text"/></div>
			<div><label>节点名称:</label><input name="nodeName" type="text"/></div>
			<div><label>发送方式:</label>
				<select name="tplType">
					<option value="">-=请选择=-</option>
					<option value="email">邮件</option>
					<option value="mobile">短信</option>
					<option value="message">站内信</option>
				</select>
			</div>
			<div><label>状态:</label>
				<select name="tplStatus">
					<option value="">-=请选择=-</option>
					<option value="0">可选</option>
					<option value="1">必须</option>
				</select>
			</div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><input type="reset" class="easyui-linkbutton searchBtn" style="width:104px;height:28px;" value="重置"/></div>
		</div>
	</form>
	<table id="messageTemplateTable"></table>
	
	<div id="add">
		<form id="tplForm" method="post">
			<input type="hidden" name="id"/>
			<div class="x-form-item">
				<label class="x-form-item-label">模版名称:</label>
				<div class="x-form-element">
					<input type="text" name="tplName" placeholder="模版名称"/>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">发送方式:</label>
				<div class="x-form-element">
					<select name="tplType">
						<option value="">-=请选择=-</option>
						<option value="email">邮件</option>
						<option value="mobile">短信</option>
						<option value="message">站内信</option>
					</select>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">状态:</label>
				<div class="x-form-element">
					<select name="tplStatus">
						<option value="">-=请选择=-</option>
						<option value="0">可选</option>
						<option value="1">必须</option>
					</select>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">节点名称:</label>
				<div class="x-form-element">
					<input id="tplCode"  name="tplCode" />
				</div>
			</div>
			
			<div class="x-form-item" id="nodeDescription" style="display: none;">
				<label class="x-form-item-label">节点参数说明:</label>
				<div class="x-form-element">
					<label></label>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">模版内容:</label>
				<div class="x-form-element">
					<textarea rows="5" name="tplContent" placeholder="模版内容"></textarea>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">描述:</label>
				<div class="x-form-element">
					<textarea rows="5" name="tplDesprition" placeholder="描述"></textarea>
				</div>
			</div>
			
			<div class="psb">
				<a id="saveBtn" class="easyui-linkbutton searchBtn">保存</a><a id="closeBtn" class="easyui-linkbutton searchBtn">取消</a>
			</div>
		</form>
	</div>
	<!-- 判断权限 -->
	
	<!-- 添加 -->
	<security:authorize access="hasPermission(null, 'security.operation.message_tpl_add')">
		<input type="hidden" id="message_tpl_add"/>
	</security:authorize>
	<script type="text/javascript">
		function operateData(val,row,index){
			var returnStr='';
			//编辑
			returnStr += '<security:authorize access="hasPermission(null, \'security.operation.message_tpl_modify\')"><a href="javascript:editWin(\''+row.id+'\')">编辑</a></security:authorize>';
			//删除
			returnStr += '<security:authorize access="hasPermission(null, \'security.operation.message_tpl_delete\')"><a href="javascript:remove(\''+row.id+'\')">删除</a></security:authorize>';
			return returnStr;
		}
	</script>
	