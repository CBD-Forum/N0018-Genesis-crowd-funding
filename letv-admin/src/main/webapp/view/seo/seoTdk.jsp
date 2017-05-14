<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>

<script type="text/javascript" src="<%=path%>/js/seo/seoTdk.js"></script>

	<form id="list_search">
		<div class="seach_div">
			<div><label>编码:</label><input name="code" type="text"/></div>
			<div><label>title:</label><input name="title" type="text"/></div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><a id="resetBtn" class="easyui-linkbutton searchBtn">重置</a></div>
		</div>
	</form>
	<table id="tdkTable"></table>
	
	<div id="tdkFormDiv" class="add">
		<form id="tdkForm" method="post">
			<input type="hidden" name="id"/>
			<div class="x-form-item" >
				<label class="x-form-item-label">编码:</label>
				<div class="x-form-element">
					<input type="text" name="code" placeholder="编码"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">title:</label>
				<div class="x-form-element">
					<textarea rows="3" name="title" placeholder="title"></textarea>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">description:</label>
				<div class="x-form-element">
					<textarea rows="3" name="description" placeholder="description"></textarea>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">keyword:</label>
				<div class="x-form-element">
					<textarea rows="3" name="keyword" placeholder="keyword"></textarea>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">描述:</label>
				<div class="x-form-element">
					<textarea rows="3" name="memo" placeholder="描述"></textarea>
				</div>
			</div>
			
			<div class="psb">
				<a id="saveBtn" class="easyui-linkbutton searchBtn">保存</a><a id="closeBtn" class="easyui-linkbutton searchBtn">取消</a>
			</div>
			
		</form>
	</div>
	
	<security:authorize access="hasPermission(null, \'security.operation.spread_tdk_add\')">
		<input type="hidden" id="spread_tdk_add"/>
	</security:authorize>
	
	<script type="text/javascript">
		function operateData(val, row, index) {
			var returnStr = '';
			//修改
			returnStr+='<security:authorize access="hasPermission(null, \'security.operation.spread_tdk_modify\')"><a onclick=editWin("' + row.id + '")>修改</a></security:authorize>';
			//删除
			returnStr+='<security:authorize access="hasPermission(null, \'security.operation.spread_tdk_delete\')"><a onclick=removeRow("'+row.id+'")>删除</a></security:authorize>';
			return returnStr;
		}
	</script>