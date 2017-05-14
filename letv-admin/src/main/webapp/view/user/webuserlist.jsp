<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="<%=path%>/js/user/webuserList.js"></script>
	<form id="list_search">
		<div class="seach_div">
		
			<div><label>用户名:</label><input name="userId" type="text"/></div>
			<div><label>真实姓名:</label><input name="realName" type="text"/></div>
			<div><label>手机号:</label><input name="mobile" type="text"/></div>
			<div><label>邮箱:</label><input name="email" type="text"/></div>
			<%-- <div><label>用户等级:</label><input name="userLevel" type="text" class="easyui-combobox" url="<%=path %>/dictionary/user_level.html" panelHeight="auto"/></div> --%>
			<div><label>用户类型:</label><input name="memberType" type="text" class="easyui-combobox" url="<%=path %>/dictionary/member_type.html" panelHeight="auto"/></div>
			<div><label>注册开始时间:</label><input type="text" name="startCreateTime" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/></div>
			<div><label>注册结束时间:</label><input type="text" name="endCreateTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/></div>
						
			
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><input type="reset" class="easyui-linkbutton searchBtn" style="width:104px;height:28px;" value="重置"/></div>
		</div>
	</form>
	<table id="userTable"></table>
	
<script type="text/javascript">

function operateData(val,row,index){
	//详情、、重置密码、锁定（恢复） normal lock disable
	var returnStr='';
	//判断是否用【详细】的权限
	returnStr+='<security:authorize access="hasPermission(null, \'security.operation.webuser_detail\')"><a href="'+path+'/user.webUserDetail.html?id='+row.userId+'">详细</a></security:authorize>';
	//判断是否有【编辑】的权限
	returnStr+='<security:authorize access="hasPermission(null, \'security.operation.webuser_modify\')"><a href="'+path+'/user.webUserEdit.html?id='+row.id+'&userId='+row.userId+'">编辑</a></security:authorize>';
	//判断是否有 【重置密码】 的权限
	returnStr+='<security:authorize access="hasPermission(null, \'security.operation.webuser_resetPwd\')"><a onClick=resetPassword("'+row.id+'")>重置密码</a></security:authorize>';
	//删除
	returnStr+='<security:authorize access="hasPermission(null, \'security.operation.webuser_remove\')"><a onClick=removeUser("'+row.id+'")>删除</a></security:authorize>';
	//判断是否有 【启用/禁用】 的权限
	returnStr+='<security:authorize access="hasPermission(null, \'security.operation.webuser_changeStatus\')">';
	if (row.status=='normal') {
		returnStr+='<a onClick=modifySecurity("'+row.userId+'","disable")>禁用</a>';
	}else{
		returnStr+='<a onClick=modifySecurity("'+row.userId+'","normal")>启用</a>';
	}
	if (row.cusUserLevel=='lead') {
		if(row.isCelebrity=='1'){
			returnStr+='<security:authorize access="hasPermission(null, \'security.operation.modifyIsCelebrity\')"><a onClick=modifyIsCelebrity("'+row.userId+'","0")>取消设为明星</a></security:authorize>';
		}else{
			returnStr+='<security:authorize access="hasPermission(null, \'security.operation.modifyIsCelebrity\')"><a onClick=modifyIsCelebrity("'+row.userId+'","1")>设为明星</a></security:authorize>';
		}
	}
	
	
	//变更用户等级
	//returnStr+='<security:authorize access="hasPermission(null, \'security.operation.webuser_change_grade\')"><a href="'+path+'/user.webCrowdfundUserEdit.html?id='+row.userId+'">变更用户等级</a></security:authorize>';
	returnStr+='</security:authorize>';
	return returnStr;
}
//-->
</script>
<!-- 判断权限   end -->
	