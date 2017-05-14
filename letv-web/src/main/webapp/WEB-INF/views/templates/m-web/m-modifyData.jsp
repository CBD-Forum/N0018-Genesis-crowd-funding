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
<link rel="stylesheet" href="<%=path %>/style/m-ruoshui.css" />
<script type="text/javascript" src="<%=path %>/js/common/jquery.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/common.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/TouchSlide.1.1.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/m-validate.js"></script>
<title>个人设置</title>
<script type="text/javascript">
	var path = "<%=path%>";
	var userId = "<%=userId%>";
</script>
</head>

<body>
<div class="head_top back1">
    <p>个人设置</p>
    <div class="back"></div>
</div>
<div class="center_s modifyinfo" style="padding-bottom:30px;">
	<ul>
		<li class="center_tx">
			<label>头像</label><span class="center_img"><img id="imghead" src="" /></span>
		</li>
		<li>
			<label>手机</label><span class="center_v" id="mobile"></span>
		</li>
		<li>
			<label>真实姓名</label><span class="center_v"><input id="realName" type="text" disabled="disabled"  placeholder="领投/跟投认证后才可以显示" style="background:#fff;width:180px;border:none;font-size:14px;"/></span>
		<!-- 	<a href="javascript:void(0);" id="realNP" class="ttcl_tip">？</a>
	        <a id="realNPcontent" style="display:none;border:1px solid #ccc;padding:1px 5px;background-color:#ddd;">温馨提示：</a> -->
		</li>
		<li>
			<label>性别</label>
			<span class="center_v" id="sex">
				<label style="margin-right:20px;"><input id="boy" type="radio" name="sex" vsex="M" checked="checked" />&nbsp;男</label>
				<label><input id="girl" type="radio" name="sex" vsex="F" />&nbsp;女</label>
			</span>
		</li>
		<li class="center_address clearfix">
			<label>所在地</label>
			<span class="fr" style="margin-right:30px;">
				<select id="province" nullMessage="省份">
	            	<option>请选择省份</option>
	            </select>&nbsp;
	            <select id="city" nullMessage="城市">
	            	<option>请选择城市</option>
	            </select>
            </span>
		</li>
		<li class="center-detail" style="height:auto;">
			<label>个人说明</label><br />
			<textarea id="selfDetail" rows="4" placeholder="请输入个人介绍"></textarea>
		</li>
	</ul>
	<div><a class="registerbtn" style="width:95%; margin:20px auto 50px;" id="submitInfo">保&nbsp;存</a></div>
</div>
<input id="userId" type="hidden" />
<%-- <ul class="foot_pos">
	<li><a href="<%=path %>/common/m-index.html"><p class="home"></p>首页</a></li>
	<li><a href="<%=path%>/common/m-projectList-stock.html?isProjectPay=1"><p class="pro"></p>项目融资</a></li>
	<li><a href="<%=path%>/common/m-projectList.html?type=entity"><p class="cp"></p>产品众筹</a></li>
	<%if(userId == null){ %>
    <li><a href="<%=path%>/common/m-login.html"><p class="my"></p>我的</a></li>
    <%}else{ %>
    <li><a class="col1" href="<%=path%>/common/m-myCenter.html"><p class="myl"></p>我的</a></li>
    <%} %>
</ul> --%>

<ul class="foot_pos">
	<li><a href="<%=path %>/common/m-index.html"><p class="home"></p>首页</a></li>
	<li><a href="<%=path %>/common/m-projectList.html"><p class="pro"></p>项目</a></li>
	<li><a href="<%=path %>/common/m-transferList.html"><p class="cp"></p>挂牌</a></li>
	<%if(userId == null){ %>
    <li><a href="<%=path%>/common/m-login.html"><p class="my"></p>我的</a></li>
    <%}else{ %>
    <li><a class="col1" href="<%=path%>/common/m-myCenter.html"><p class="myl"></p>我的</a></li>
    <%} %>
</ul> 

<!-- 整体遮盖曾 -->
<div id="bgpop" class="bgpop"></div>
<script type="text/javascript" src="<%=path %>/js/m/modifyData.js"></script>
</body>
</html>