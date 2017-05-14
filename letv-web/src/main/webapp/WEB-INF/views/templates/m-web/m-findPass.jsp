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
<script type="text/javascript" src="<%=path %>/js/m/findPass.js"></script>

<title>找回密码</title>
<script type="text/javascript">
	var path = "<%=path %>" ;
</script>
</head>

<body>
<div class="head_top back1">
    <p>找回密码</p>
    <div class="back"></div>
</div>
<div class="mt50" style="padding-top:15px;">
	<div class="tipdiv" id="findPassTip"></div>
	<ul class="gy_login forget_pass">
	    <li class="gy_text"><input type="text" id="username" placeholder="请输入您的手机号码" nullMessage="手机号码不能为空" maxlength="11" /></li>
        <li class="btu-put" id="regeter2Svali" >
       		<input type="text" placeholder="请先输入验证码" id="regiterValiCode" maxlength="6"/>
       		<img alt="点击刷新" id="imageStream2" style="cursor:pointer;position:relative;margin-left:16px;+margin-left:316px;+margin-top:-55px;"
									src="<%=path%>/security/securityCodeImage.html"
									onclick="this.src='<%=path%>/security/securityCodeImage.html?'+new Date().getTime()" />
       		<a href="javascript:void(0);" class="r2s" id="sendViliimgBtn" style="display:none;">确&nbsp;定</a>
	    </li>
	    <li class="yanzheng clearfix">
       		<input type="text" placeholder="请输入手机验证码" nullMesaage="手机验证码不能为空" id="phoneCode" maxlength="6" />
       		<a href="javascript:void(0);" id="getVertify">获取验证码</a>
        </li>
	    <li class="gy_text"><input type="password" id="password" placeholder="请输入修改密码" nullMessage="修改密码不能为空"/></li>
	    <li class="gy_text"><input type="password" id="password2" placeholder="请重复输入密码" nullMessage="确认密码不能为空"/></li>
	    <li class="gy_zc_but"><a class="registerbtn" href="javascript:void(0);" id="findPwdSubmit">完成</a></li>
	</ul>
</div>

<!-- 整体遮盖曾 -->
<div id="bgpop" class="bgpop"></div>
<input type="hidden" id="findUserId">
</body>
</html>