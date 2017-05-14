<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="<%=path%>/js/user/userList.js"></script>
	<form id="list_search">
		<div class="seach_div">
		
			<div><label>用户名:</label><input name="userId" type="text"/></div>
			<div><label>真实姓名:</label><input name="realName" type="text"/></div>
			<div><label>手机号:</label><input name="mobile" type="text"/></div>
			<div><label>邮箱:</label><input name="email" type="text"/></div>
			<div><label>注册开始时间:</label><input type="text" name="startCreateTime" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/></div>
			<div><label>注册结束时间:</label><input type="text" name="endCreateTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/></div>
			<div class="psb" style="float:right;"><a id="searchBtn" class="easyui-linkbutton searchBtn">查询</a><input type="reset" class="easyui-linkbutton searchBtn" style="width:104px;height:28px;" value="重置"/></div>
		</div>
	</form>
	<table id="userTable"></table>
	<div id="userDetail" style="display: none;">
		<fieldset style="width: 350px; float:left; display: inline; margin-left: 10px;">
			<legend>基本信息</legend>
		                用户名: <label id="userId"></label> <br/>
		                第三方账户: <label id="thirdAccount"></label> <br/>
		                邮箱: <label id="email"></label> <br/>
		                真实姓名: <label id="realName"></label> <br/>
		                昵称: <label id="nickName"></label> <br/>
		                性别: <label id="sex"></label> <br/>
		                生日: <label id="birthday"></label> <br/>
		              微博账号: <label id="weiboNo"></label> <br/>
		     手机号     : <label id="mobile"></label> <br/>
		     电话号     : <label id="tel"></label> <br/>
		      证件类型    : <label id="certificateType"></label> <br/>
		      证件号    : <label id="certificateNo"></label> <br/>
		      所在省    : <label id="provinceName"></label> <br/>
		      所在城市    : <label id="cityName"></label> <br/>
		      所在县    : <label id="countyName"></label> <br/>
		     家庭住址     : <label id="homeAddress"></label> <br/>
		  <div>头像:</div>  <img id="photo" alt="头像" style="width: 100px; height: 100px;" /> <br/>
		     认证时间     : <label id="authTime"></label> <br/>
		        用户等级  : <label id="userLevel"></label> <br/>
		     开户时间     : <label id="openAccTime"></label> <br/>
		     邀请码     : <label id="inviteCode"></label> <br/>
		   注册时间      : <label id="createTime"></label> <br/>
		    是否为借款人      : <label id="isBorrower"></label> <br/>
		    紧急联系人      : <label id="emergencyContact"></label> <br/>
		   紧急联系人电话       : <label id="emergencyPhone"></label> <br/>
		     紧急联系人关系     : <label id="emergencyRelation"></label> <br/>
		      家庭住址邮编    : <label id="postCode"></label> <br/>
  		</fieldset>
		
		<fieldset style="width: 300px; float:left; display: inline; margin-left: 10px;">
			<legend>财务统计</legend>
		            可用余额: <label id="balance"></label> <br/>
		             冻结金额: <label id="frozen_amt"></label> <br/>
		             总投资额: <label id="investMoney"></label> <br/>
		             总待收本金: <label id="repayingPrincipal"></label> <br/>
		             累积收益: <label id="repayedInterest"></label> <br/>
		             待收收益: <label id="repayingInterest"></label> <br/>
		             总充值金额: <label id="actual_amt"></label> <br/>
		             已提现金额: <label id="amt"></label> <br/>
  		</fieldset>
  		
  		<fieldset style="width: 300px; float:left; display: inline; margin-left: 10px;">
			<legend>安全信息</legend>
			<!-- 
		            安全问题1: <label id="security_question1"></label> <br/>
		             答案1: <label id="security_answer1"></label> <br/>
		            安全问题2: <label id="security_question2"></label> <br/>
		             答案2: <label id="security_answer2"></label> <br/>
		    -->
		             上次登录时间: <label id="last_login_time"></label> <br/>
		             当日密码错误次数: <label id="login_failed_num"></label> <br/>
		             登录失败日期: <label id="login_failed_time"></label> <br/>
		            最近禁用时间: <label id="disable_time"></label> <br/>
		             用户类型: <label id="userTypeText"></label> <br/>
		             状态: <label id="statusName"></label> <br/>
  		</fieldset>
	</div>
	
	<!-- 判断权限   begin -->
	<!-- 判断是否用【详细】的权限 -->
	<security:authorize access="hasPermission(null, 'security.operation.user_investUser_detail')">
		<input type="hidden" id="detail_auth"/>
	</security:authorize>
	
	<!-- 判断是否有【编辑】的权限 -->
	<security:authorize access="hasPermission(null, 'security.operation.user_investUser_modify')">
		<input type="hidden" id="modify_auth"/>
	</security:authorize>
	
	<!-- 判断是否有 【重置密码】 的权限 -->
	<security:authorize access="hasPermission(null, 'security.operation.user_investUser_resetPwd')">
		<input type="hidden" id="resetPwd_auth"/>
	</security:authorize>
	
	<!-- 判断是否有 【启用/禁用】 的权限 -->
	<security:authorize access="hasPermission(null, 'security.operation.user_investUser_changeStatus')">
		<input type="hidden" id="changeStatus_auth"/>
	</security:authorize>
	<!-- 判断权限   end -->
	
	<!-- 判断权限   begin -->
<script type="text/javascript">
<!--
function operateData(val,row,index){
	//详情、、重置密码、锁定（恢复） normal lock disable
	var returnStr='';
	//判断是否用【详细】的权限
	returnStr+='<security:authorize access="hasPermission(null, \'security.operation.user_investUser_detail\')"><a  onClick=showUserDetail("'+row.userId+'")>详细</a></security:authorize>';
	//判断是否有【编辑】的权限
	returnStr+='<security:authorize access="hasPermission(null, \'security.operation.user_investUser_modify\')"><a href="'+path+'/user.userEdit.html?id='+row.id+'">编辑</a></security:authorize>';
	//判断是否有 【重置密码】 的权限
	returnStr+='<security:authorize access="hasPermission(null, \'security.operation.user_investUser_resetPwd\')"><a onClick=resetPassword("'+row.id+'")>重置密码</a></security:authorize>';
	//判断是否有 【启用/禁用】 的权限
	returnStr+='<security:authorize access="hasPermission(null, \'security.operation.user_investUser_changeStatus\')">';
	if (row.status=='normal') {
		returnStr+='<a onClick=modifySecurity("'+row.userId+'","disable")>禁用</a>';
	}else{
		returnStr+='<a onClick=modifySecurity("'+row.userId+'","normal")>启用</a>';
	}
	returnStr+='</security:authorize>';
	return returnStr;
}
//-->
</script>
<!-- 判断权限   end -->
	