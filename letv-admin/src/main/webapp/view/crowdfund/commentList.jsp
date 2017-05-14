<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="<%=path%>/js/crowdfund/commentList.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>项目编号:</label><input id="s_loanNo" name="loanNoLike" type="text"/></div>
			<div><label>项目名称:</label><input id="s_loanName" name="loanNameLike" type="text"/></div>
			<div><label>评论人:</label><input id="s_discuss_user" name="discussUserLike" type="text"/></div>
			<div><label>评论状态:</label><input id="s_stutas" type="text" style="height: 24px;" name="state" 
			class="easyui-combobox" url="<%=path %>/dictionary/crowdFund_auditState.html" panelHeight="auto"/></div>
			<div><label>评论内容:</label><input id="content" name="content" type="text"/></div>
			
			
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a>
			<input type="reset" class="easyui-linkbutton searchBtn" style="width:104px;height:28px;" value="重置"/></div>
		</div>
	</form>
	<table id="commentTable"></table>
	<script type="text/javascript">
	function operateData(val,row,index){
		var returnStr='';
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_comment_del\')"><a onclick=del("'+row["id"]+'")>删除</a></security:authorize>';
		/*if(state=='auditing'){
			returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_comment_pass\')"><a onclick=pass("'+row["id"]+'")>通过</a></security:authorize>';
			returnStr+='<security:authorize access="hasPermission(null, \'security.operation.crowdfund_comment_refuse\')"><a onclick=refuse("'+row["id"]+'")>拒绝</a></security:authorize>';
		}*/
		return returnStr;
	}
	</script>
