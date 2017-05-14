<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="<%=path%>/js/admin/adminList.js"></script>
	<form id="list_search">
		<div class="seach_div">
			<div><label>用户名:</label><input name="adminId" type="text"/></div>
			<div><label>真实姓名:</label><input name="realName" type="text"/></div>
			<div><label>员工号:</label><input name="employeeNo" type="text"/></div>
			<div><label>手机号:</label><input name="mobile" type="text"/></div>
			<div><label>邮箱:</label><input name="email" type="text"/></div>
			<div><label>部门:</label><input style="height: 25px;" id="dept" name="dept" class="easyui-combobox" url="<%=path %>/dictionary/dept.html" panelHeight="auto"/></div>
			<div><label>注册开始时间:</label><input type="text" name="startCreateTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/></div>
			<div><label>注册结束时间:</label><input type="text" name="endCreateTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/></div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><a id="resetBtn" class="easyui-linkbutton searchBtn">重置</a></div>
		</div>
	</form>
	<table id="adminTable"></table>
	
	<div id="add">
		<form id="adminForm" method="post">
			<input type="hidden" name="id"/>
			<div class="x-form-item">
				<label class="x-form-item-label">用户名:</label>
				<div class="x-form-element">
					<input type="text" name="adminId" placeholder="用户名"/>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">密码:</label>
				<div class="x-form-element">
					<input type="password" name="password" id="password" placeholder="密码"/>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">确认密码:</label>
				<div class="x-form-element">
					<input type="password" name="confirmPassword" id="confirmPassword" placeholder="确认密码"/>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">真实姓名:</label>
				<div class="x-form-element">
					<input type="text" name="realName" placeholder="真实姓名"/>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">身份证号:</label>
				<div class="x-form-element">
					<input type="text" name="idCardNo" placeholder="身份证号"/>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">员工编号:</label>
				<div class="x-form-element">
					<input type="text" name="employeeNo" placeholder="员工编号"/>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">部门:</label>
				<div class="x-form-element">
					<input id="dept" name="dept" class="easyui-combobox" url="<%=path %>/dictionary/dept.html" panelHeight="auto" validType="selectValueRequired['#adminForm #dept','部门']"  />
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">邮箱:</label>
				<div class="x-form-element">
					<input type="text" name="email" placeholder="编码"/>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">手机号:</label>
				<div class="x-form-element">
					<input type="text" name="mobile" placeholder="编码"/>
				</div>
			</div>
			<div class="psb">
				<a id="saveBtn" class="easyui-linkbutton searchBtn">保存</a><a id="closeBtn" class="easyui-linkbutton searchBtn">取消</a>
			</div>
		</form>
	</div>
	
	
	<div id="roleList" style="display: none;">
		<table id="roleTable"></table>
		<input type="hidden" name="userId">
		<div class="psb" style="margin-top:10px;"><a id="saveRoleBtn" class="easyui-linkbutton searchBtn">确定</a><a id="closeRoleBtn" class="easyui-linkbutton searchBtn">取消</a></div>
	</div>
	
	<div id="adminDetail" style="display: none;">
		<fieldset style="width: 350px; float:left; display: inline;">
			<legend>基本信息</legend>
		                用户名: <label id="adminId"></label> <br/>
		                真实姓名: <label id="realName"></label> <br/>
		                员工编号: <label id="employeeNo"></label> <br/>
		                部门: <label id="deptText"></label> <br/>
		               身份证号: <label id="idCardNo"></label> <br/>
		               职位: <label id="roleName"></label> <br/>
		               邮箱: <label id="email"></label> <br/>
			     手机号     : <label id="mobile"></label> <br/>
			      创建日期   : <label id="createTime"></label> <br/>
			      创建人员    : <label id="creator"></label> <br/>
			   <!--  
			        审核人  : <label id="auditor"></label> <br/>
			      审核日期    : <label id="auditTime"></label> <br/>
			      审核状态    : <label id="auditStatus"></label> <br/>
			      -->
			      <legend>安全信息</legend>
			        上次登录时间: <label id="lastLoginTime"></label> <br/>
		             当日密码错误次数: <label id="loginFailedNum"></label> <br/>
		             登录失败日期: <label id="loginFailedTime"></label> <br/>
		             最近禁用时间: <label id="disableTime"></label> <br/>
		             用户类型: <label id="userTypeText"></label> <br/>
		             状态: <label id="statusName"></label> <br/>
  		</fieldset>
  		
  		<!--<fieldset style="width: 300px; float:left; display: inline;">
			 <legend>安全信息</legend>
			 
		            安全问题1: <label id="securityQuestion1"></label> <br/>
		             答案1: <label id="securityAnswer1"></label> <br/>
		            安全问题2: <label id="securityQuestion2"></label> <br/>
		             答案2: <label id="securityAnswer2"></label> <br/>
		    
		             上次登录时间: <label id="lastLoginTime"></label> <br/>
		             当日密码错误次数: <label id="loginFailedNum"></label> <br/>
		             登录失败日期: <label id="loginFailedTime"></label> <br/>
		             最近禁用时间: <label id="disableTime"></label> <br/>
		             用户类型: <label id="userTypeText"></label> <br/>
		             状态: <label id="statusName"></label> <br/>
  		</fieldset>-->
	</div>
	
	<!-- 判断权限 -->
	<security:authorize access="hasPermission(null, 'security.operation.user_admin_add')">
		<input type="hidden" id="user_admin_add"/>
	</security:authorize>
	
	<script type="text/javascript">
	//添加操作列
	function operateData(val,row,index){
		//详情、、重置密码、锁定（恢复） normal lock disable
		var returnStr='';
		//判断是否用【详细】的权限
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.user_admin_detail\')"><a onClick=showAdminDetail("'+row.id+'")>详细</a></security:authorize>';
		//判断是否有【编辑】的权限
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.user_admin_modify\')"><a onclick=editWin("'+row.id+'")>编辑</a></security:authorize>';
		//判断是否有 【重置密码】 的权限
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.user_admin_resetPwd\')"><a onClick=resetPassword("'+row.id+'")>重置密码</a></security:authorize>';
		//判断是否有 【启用/禁用】 的权限
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.user_admin_changeStatus\')">';
		if (row.status=='normal') {
			returnStr+='<a onClick=modifySecurity("'+row.userId+'","disable")>禁用</a>';
		}else{
			returnStr+='<a onClick=modifySecurity("'+row.userId+'","normal")>启用</a>';
		}
		returnStr+='</security:authorize>';
		//【岗位分配】
		returnStr+='<security:authorize access="hasPermission(null, \'security.operation.user_admin_configRole\')"><a onClick=showRoleList("'+row.id+'")>岗位分配</a></security:authorize>';
		return returnStr;
	}
	</script>
	
