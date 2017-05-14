<%@page import="com.fbd.core.common.utils.SysConfigCache"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../layout/common_jquery.jsp"%>
<style type="text/css">
.inputt {
  margin-left: 115px;
}
.login_bg{width:600px;height:450px;background:url(<%=path%><%=SysConfigCache.getInstance().findValue("admin_login_url") %>) no-repeat;float:left;}
</style>
<script type="text/javascript" src="<%=path%>/js/login/forgotPassword.js"></script>
<title><%=SysConfigCache.getInstance().findValue("admin_tilte_info") %>-忘记密码</title>
</head>
<body class="loginbody">
	<div class="title"><%=SysConfigCache.getInstance().findValue("admin_tilte_info") %></div>
	<div class="box">
		<div class="login_bg"></div>
		<div class="login_div">
			<div id="msg" style="border: solid 1px #eed4d8; display:none; background-color:rgb(242, 222, 222); color: #b94a48; line-height:30px; font-size:14px; width:280px; padding-left:20px; margin-left:60px; margin-top: 10px;"></div>
			<form id="resetPasswordForm" method="post" style="position:absolute; top: 200px;">
				<div class="form_div">
					<label>员工号：</label><input type="text" id="employeeNo" name="employeeNo" placeholder="员工号"/>
					<div class="inputt"></div>
				</div>
				<div class="form_div">
					<label>手机号：</label><input  type="text" id="mobile" name="mobile" placeholder="手机号"/>
					<div class="inputt"></div>
				</div>
				<div class="form_div authCode">
					<label>手机验证码：</label><span class="yzm"><input type="text" name="verifyCode" id="verifyCode" class="authInput" placeholder="手机验证码"/><input type="button" class="authBtn" value="获取验证码" onclick="getAuthCode();"/></span>
					<div class="inputt"></div>
				</div>
				
				<div class="form_div">
					<label>重置密码：</label><input type="password" name="password" id="password" placeholder="重置密码"/>
					<div class="inputt"></div>
				</div>
				
				<div class="form_div">
					<label>确认密码：</label><input type="password" name="rePassword" id="rePassword" placeholder="确认密码"/>
					<div class="inputt"></div>
				</div>
				
				<div class="form_div" id="vilidiv">
					<label>验证码：</label>
					<input type="text" maxlength="4" name="valiCode" id="valiCode" placeholder="验证码" style="width:100px;"/>
					<img title="点击刷新" id="imageStream" style="position:relative;top:8px; cursor: pointer;" src="<%=path%>/securityCodeImage.html" /><span id="verifyShow"></span>
					<div class="inputt"></div>
				</div>
				<div class="form_div" style="margin-top:20px;">
					<a id="confirmBtn" class="button" tabindex="4" style="width:200px;">确认</a>
					<a style="font-size: 13px;" href="<%=path%>/view/login.jsp">返回登录</a>
				</div>
			</form>
		</div>
	</div>
</body>
</html>