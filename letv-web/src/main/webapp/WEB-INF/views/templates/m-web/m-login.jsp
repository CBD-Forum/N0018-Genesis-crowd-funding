<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes" />
<META HTTP-EQUIV="Expires" CONTENT="0"> 
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"> 
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link rel="stylesheet" href="<%=path %>/style/web.css" />
<link rel="stylesheet" href="<%=path %>/style/m-ruoshui.css" />
<script type="text/javascript" src="<%=path %>/js/common/jquery.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/common.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/m-validate.js"></script>
<script type="text/javascript" src="<%=path %>/js/m/login.js"></script>

<title>登录</title>
<script type="text/javascript">
	var path = "<%=path %>" ;
</script>
</head>

<body>
<div class="head_top back1">
    <p>登录</p>
    <div class="back"></div>
    <div id="indexHw" class="right"></div>
</div>
<div class="mt50" style="padding-top:15px;">
	<div class="tipdiv" id="loginTip"></div>
	<ul class="gy_login">
	    <li class="gy_text"><input type="text" id="loginUserId" placeholder="请输入您的用户名或手机号" nullMessage="用户名或手机号不能为空" /></li>
	    <li class="gy_text"><input type="password" id="loginPassword" placeholder="请输入您的密码" nullMessage="密码不能为空"/></li>
	    <li class="clearfix validate" id="webBody3">
	    <input id="loginValiCode" style="width:60%" type="text" placeholder="请输入验证码" class="fl"/><img alt="点击刷新" id="imageStream1" style="float:left; margin-left:20px; margin-top:5px;"
									src="<%=path%>/security/securityCodeImage.html?+new Date().getTime()"
									onclick="this.src='<%=path%>/security/securityCodeImage.html?'+new Date().getTime()" />
				</li>
	    <li class="clearfix gy_lal">
	    	<p class="fl"><a href="<%=path%>/common/m-findPass.html"><c>忘记密码？</c></a></p>
	        <p class="fr"><a href="<%=path%>/common/m-register.html"><c>立即注册</c></a></p>
	    </li>
	    <li class="gy_zc_but"><a class="registerbtn" href="javascript:void(0);" id="loginBtn">登录</a></li>
	</ul>
</div>
</body>
</html>