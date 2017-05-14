<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="<%=path%>/js/message/messageNode.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>编号:</label><input name="code" type="text"/></div>
			<div><label>名称:</label><input name="name" type="text"/></div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><input type="reset" class="easyui-linkbutton searchBtn" style="width:104px;height:28px;" value="重置"/></div>
		</div>
	</form>
	<table id="messageNodeTable"></table>
	
	<div id="add">
		<form id="messageNodeForm" method="post">
			<input type="hidden" name="id"/>
			<div class="x-form-item">
				<label class="x-form-item-label">编号:</label>
				<div class="x-form-element">
					<input type="text" name="code" placeholder="编号"/>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">名称:</label>
				<div class="x-form-element">
					<input type="text" name="name" placeholder="编码"/>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">状态:</label>
				<div class="x-form-element">
					<select name="status">
						<option value="">-=请选择=-</option>
					    <option value="开启">开启</option>
					    <option value="关闭">关闭</option>
					</select>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">描述:</label>
				<div class="x-form-element">
					<textarea rows="5" name="description" placeholder="描述"></textarea>
				</div>
			</div>
			
			<div class="psb">
				<a id="saveBtn" class="easyui-linkbutton searchBtn">保存</a><a id="closeBtn" class="easyui-linkbutton searchBtn">取消</a>
			</div>
		</form>
	</div>
	
	<!-- 判断权限 -->
	
	<!-- 添加 -->
	<security:authorize access="hasPermission(null, 'security.operation.message_node_add')">
		<input type="hidden" id="message_node_add"/>
	</security:authorize>
	<script type="text/javascript">
		function operateData(val,row,index){
			//详情、、重置密码、锁定（恢复） normal lock disable
			var returnStr='<security:authorize access="hasPermission(null, \'security.operation.message_node_modify\')"><a onclick=editWin("'+row.id+'")>修改</a></security:authorize>';
			return returnStr;
		}
	</script>