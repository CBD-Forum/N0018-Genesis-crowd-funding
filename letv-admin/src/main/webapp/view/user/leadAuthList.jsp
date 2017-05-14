<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="<%=path%>/js/user/leadAuthList.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>用户名:</label><input name="userIdLike" type="text"/></div>
			<div><label>真实姓名:</label><input name="realName" type="text"/></div>
			<div><label>手机号:</label><input name="mobile" type="text"/></div>
			<div><label>身份证号码:</label><input name="certNo" type="text"/></div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><input type="reset" class="easyui-linkbutton searchBtn" style="width:104px;height:28px;" value="重置"/></div>
		</div>
	</form>
	<table id="userTable"></table>
	
<script type="text/javascript">
<!--
function operateData(val,row,index){
	var returnStr='';
	//判断是否用【详细】的权限
	returnStr+='<security:authorize access="hasPermission(null, \'security.operation.leadAuth_userdetail\')"><a href="'+path+'/user.webUserDetail.html?id='+row.userId+'">详情</a></security:authorize>';
	//通过
	returnStr+='<security:authorize access="hasPermission(null, \'security.operation.leadAuth_pass\')"><a onClick=auditAuth("'+row.id+'","passed")>通过</a></security:authorize>';
	//拒绝
	returnStr+='<security:authorize access="hasPermission(null, \'security.operation.leadAuth_refuse\')"><a onClick=auditAuth("'+row.id+'","refused")>拒绝</a></security:authorize>';
	return returnStr;
}
//-->
</script>
<!-- 判断权限   end -->
	