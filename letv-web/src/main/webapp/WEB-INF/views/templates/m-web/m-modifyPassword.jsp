<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%
	String path = request.getContextPath();
	String userId = session.getAttribute("userId") == null ? null
			: session.getAttribute("userId").toString();
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
<script type="text/javascript" src="<%=path %>/js/common/jquery.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/common.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/m-validate.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/TouchSlide.1.1.js"></script>
<title>个人设置</title>
<script type="text/javascript">
	var path = "<%=path%>";
	var userId = "<%=userId%>";
</script>
</head>

<body>
<div class="head_top back1">
    <p>修改密码</p>
    <div class="back"></div>
</div>
<div class="mt50" style="padding-top:15px;">
	<div class="tipdiv" id="passwordTip"></div>
	<ul class="gy_login">
	    <li class="gy_text"><input type="password" id="pass" placeholder="请输入您的原始密码" nullMessage="原始密码不能为空" /></li>
	    <li class="gy_text"><input type="password" id="newPass" placeholder="请输入您的密码" nullMessage="新密码不能为空"/></li>
	    <li class="gy_text"><input type="password" id="okPass" placeholder="请确认您的密码" nullMessage="确认密码不能为空"/></li>
	    <li class="gy_zc_but"><a class="registerbtn" id="savePass" href="javascript:void(0);">保存</a></li>
	</ul>
</div>
<ul class="foot_pos">
	<li><a href="<%=path %>/common/m-index.html"><p class="home"></p>首页</a></li>
	<li><a href="<%=path%>/common/m-projectList-stock.html?isProjectPay=1"><p class="pro"></p>项目融资</a></li>
	<li><a href="<%=path%>/common/m-projectList.html?type=entity"><p class="cp"></p>产品众筹</a></li>
	<%if(userId == null){ %>
    <li><a href="<%=path%>/common/m-login.html"><p class="my"></p>我的</a></li>
    <%}else{ %>
    <li><a class="col1" href="<%=path%>/common/m-myCenter.html"><p class="myl"></p>我的</a></li>
    <%} %>
</ul>
<script type="text/javascript" src="<%=path %>/js/m/modifyPassword.js"></script>
</body>
</html>