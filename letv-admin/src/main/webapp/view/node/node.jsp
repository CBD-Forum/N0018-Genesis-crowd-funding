<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="<%=path%>/js/node/node.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>编码:</label><input name="code" type="text"/></div>
			<div><label>标题:</label><input name="title" type="text"/></div>
			<div><label>副标题:</label><input name="subtitle" type="text"/></div>
			<div><label>关键词:</label><input name="keywords" type="text"/></div>
			<div><label>状态:</label><input name="status" class="easyui-combobox" url="<%=path %>/dictionary/node_node_status.html" panelHeight="auto"/></div>
			<div><label>节点类型:</label><input name="nodeType1" class="easyui-combobox" panelHeight="200px;" url="<%=path %>/node/getTypeList.html" valueField="code"  textField="name" data-options="emptyItem:{name:'-=请选择=-',code:'',selected:true}" panelHeight="auto" height="100px"/></div>
			<div class="psb" style="float:right;">
				<a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a>
				<a id="resetBtn" class="easyui-linkbutton searchBtn">重置</a>
			</div>
		</div>
	</form>
	<table id="nodeTable"></table>
	
	
	<security:authorize access="hasPermission(null, 'security.operation.content_manage_add')">
		<input type="hidden" id="content_manage_add"/>
	</security:authorize>
	<script type="text/javascript">
		function operateData(val,row,index){
			var returnStr = '';
// 			if (row.nodeType=='ggjj' || row.nodeType=='gltd' || row.nodeType=='zxdt' || row.nodeType=='wzgg') {
				//预览
				returnStr+='<security:authorize access="hasPermission(null, \'security.operation.content_manage_preview\')"><a onclick=previewNode("'+row.id+'","'+row.nodeType+'")>预览</a></security:authorize>';
// 			}
			
			
			//修改
			returnStr+='<security:authorize access="hasPermission(null, \'security.operation.content_manage_modify\')"><a href="'+path+'/node.nodeadd.html?id='+row.id+'">修改</a></security:authorize>';
			//删除
			returnStr+='<security:authorize access="hasPermission(null, \'security.operation.content_manage_delete\')"><a onclick=removeNode("'+row.id+'")>删除</a></security:authorize>';
			return returnStr;
		}
	</script>
