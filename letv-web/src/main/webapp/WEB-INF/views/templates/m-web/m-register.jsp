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
<script type="text/javascript" src="<%=path %>/js/m/register.js"></script>

<title>注册</title>
<script type="text/javascript">
	var path = "<%=path%>";
</script>
</head>

<body>
<div class="head_top back1">
    <p>注册</p>
    <div class="back"></div>
    <div id="indexHw" class="right"></div>
</div>
<div style="padding-top:60px;">
	<div class="tipdiv" id="regiterTip"></div>
	<ul class="gy_login gy_zc">
	    <li class="clear text" id="regiterTip"></li>
	    	<li class="clearfix text"><input type="text" id="regiterUserId" placeholder="请输入用户名，由6-16位英文字母及数字组成"/></li>
			<li class="clearfix validate logBut" id="regeter2Svali">
	        	<input type="text" placeholder="请先输入验证码" id="regiterValiCode" class="fl" style="width:68%;"/>
	        	<img alt="点击刷新" id="imageStream2" style="cursor:pointer;margin-left:6px; margin-top:5px; float:right;"
									src="<%=path%>/security/securityCodeImage.html"
									onclick="this.src='<%=path%>/security/securityCodeImage.html?'+new Date().getTime()" />
<!-- 				<a class="r2s" href="javascript:void(0);" id="sendViliimgBtn">确定</a>					 -->
	        </li>
	        <li class="clearfix validate"><input type="text" id="mobile" placeholder="请输入您的手机号" class="fl" style="width:54%" /><a class="gain" href="javascript:void(0);" id="sendViliimgBtn" style="font-size:14px;width:100px;">获取验证码</a></li>
	        <li class="clearfix text"><input type="text" id="verifyCode" placeholder="请输入手机验证码"/><!-- <span id="sendWait" style="position:relative;top:8px;left:10px;"></span> --></li>
	        <li class="clearfix text"><input type="password" id="regiterPassword" placeholder="请输入密码，由6-16位数字、字母组成，区分大小写" style="font-size:12px;"/></li>
	        <li class="clearfix text"><input type="password" id="regiterPassword2" placeholder="请再次输入密码" style="font-size:12px;"/></li>
<!-- 	        <li class="clearfix text"><input type="text" id="inviteCode" placeholder="无邀请码不用填写"/></li> -->
	        <li class="clearfix gt_Reglal"><input type="checkbox" checked="checked" id="read"><label for="read"> 阅读并同意
	        <a href="javascript:showAgree()">《用户注册协议》</a></label></li>
	        <li class="clearfix logBut"><a class="registerbtn" href="javascript:void(0);" id="registerBtn">注册</a></li>
	        <li class="clearfix gt_center"><a href="<%=path %>/common/m-login.html"><span>已有账号？</span>登录</a></li>
	</ul>
</div>
<div class="bgpop" id="xyBgpop"></div>
<!-- 协议范文 -->
<div class="agree_box" style="z-index:106;">
	<div style="text-align: center;margin-bottom:-5px;margin-top:5px;font-size:16px;width: 90%;float: left;">用户协议</div>
	<div class="agree_close"></div>
	<div id="agreeContent" class="agree_con"></div>
	<p id="agreeTime" class="agree_date"></p>
</div>
</body>
</html>