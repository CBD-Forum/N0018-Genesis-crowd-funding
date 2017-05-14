<%@page import="com.fbd.core.common.utils.SysConfigCache"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	var path = "<%=path%>";
</script>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/style.css"/>
<script type="text/javascript" src="<%=path%>/js/common/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/common.js"></script>
<script type="text/javascript" src="<%=path%>/js/user/login.js"></script>
<style type="text/css">.login_bg{width:600px;height:450px;background:url(<%=path%><%=SysConfigCache.getInstance().findValue("admin_login_url") %>) no-repeat;float:left;}</style>
<title><%=SysConfigCache.getInstance().findValue("admin_tilte_info") %>-登录</title>
</head>
<body class="loginbody">
	<div class="title"><%=SysConfigCache.getInstance().findValue("admin_tilte_info") %></div>
	<div class="box">
		<div class="login_bg"></div>
		<div class="login_div">
			<div id="msg" style="border: solid 1px #eed4d8; display:none; background-color:rgb(242, 222, 222); color: #b94a48; line-height:40px; font-size:14px; width:280px; padding-left:20px; margin-left:60px; margin-top: 50px;"></div>
			<input id="errorMsg" type="hidden" value="${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}">
			<form method="post" id="regeditForm" style="position:absolute; top: 250px;">
				<div class="form_div">
					<label>员工号：</label><input tabindex="1" type="text" name="j_employeeNo" id="employeeNo" placeholder="员工号"/>
					<div class="inputt" style="margin-left:115px;"></div>
				</div>
				<div class="form_div">
					<label>用户/手机号：</label><input tabindex="2" type="text" name="j_username" id="userId" placeholder="用户名"/>
					<div class="inputt" style="margin-left:115px;"></div>
				</div>
				<div class="form_div">
					<label>密码：</label><input tabindex="3" type="password" name="j_password" id="password" placeholder="密码"/></span>
					<div class="inputt" style="margin-left:115px;"></div>
				</div>
				<div class="form_div" id="vilidiv" style="display:none;">
					<label>验证码：</label>
					<input type="text" maxlength="4" name="validateCode" id="valiCode" placeholder="验证码" style="width:100px;"/>
					<img alt="点击刷新" id="imageStream1" style="position:relative;top:8px;"
								src="<%=path%>/securityCodeImage.html?+new Date().getTime()"
								onclick="this.src='<%=path%>/securityCodeImage.html?'+new Date().getTime()" /><span id="verifyShow"></span>
					<div class="inputt"></div>
				</div>
				<div class="form_div" style="margin-top:30px;">
					<a id="loginBtn" class="button" tabindex="4" style="width:240px;">登录</a>
					<!-- <input type="submit" class="button" value="登录"/> -->
				</div>
				<a href="<%=path%>/view/login/forgotPassword.jsp" style="margin-left:260px;font-size:14px;">忘记密码</a>
			</form>
		</div>
	</div>
</body>
</html>