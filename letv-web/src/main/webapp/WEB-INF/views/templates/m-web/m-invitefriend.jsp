<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%
	String path = request.getContextPath();
	String userId = session.getAttribute("userId") == null ? null
			: session.getAttribute("userId").toString();
	String realName = (String)session.getAttribute("realName");
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
<title>邀请好友</title>
<script type="text/javascript">
	var path = "<%=path %>";
	var userId = "<%=userId%>";
	var realName = "<%=realName%>";
</script>
</head>

<body>
<div class="head_top back1">
    <p>邀请好友</p>
    <div class="back"></div>
</div>
<div class="mt50 gy-inviter" style="padding-top:15px; padding-bottom:60px;">
	<div class="yqhy_dx_tit">短信发送邀请码<a href="javascript:void(0);" id="showInfo">查看短信内容</a></div>
	<ul class="gy_login">
	    <li class="gy_text"><input type="text" id="phone" placeholder="请输入手机号" nullMessage="手机号不能为空" /></li>
	    <li class="gy-valicode">
	    	<input type="text" placeholder="验证码" id="valicode">
			<img alt="点击刷新" id="imageStream1" src="<%=path%>/security/securityCodeImage.html?+new Date().getTime()"
									onclick="this.src='<%=path%>/security/securityCodeImage.html?'+new Date().getTime()" />
	    </li>
	    <li class="gy_zc_but" style="padding-top:4px;"><a class="registerbtn" href="javascript:void(0);" id="inviteBtn">免费发送</a></li>
	</ul>
</div>
<div id="bgpop" class="bgpop"></div>
<div id="agreeBoxInfo" class="agreeboxinfo">
	<div class="agree_close"></div>
	<div id="agreeContent" class="agree_con"></div>
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
<script type="text/javascript" src="<%=path %>/js/m/invitefriend.js"></script>
</body>
</html>