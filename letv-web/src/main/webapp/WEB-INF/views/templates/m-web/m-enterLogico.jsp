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
<link rel="stylesheet" href="<%=path %>/style/m-ichou.css" />
<script type="text/javascript" src="<%=path %>/js/common/jquery.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/common.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/TouchSlide.1.1.js"></script>
<title>个人中心</title>
<script type="text/javascript">
	var path = "<%=path%>";
	var userId = "<%=userId%>";
</script>
</head>

<body>
<div class="head_top back1">
    <p>发货</p>
    <div class="back"></div>
</div>
<div class="center_pro mt50" style="padding-bottom:60px;">
	<div id="startData" class="delivery_div"></div>
	<div class="list_more" id="allMoreList" style="display: block;">点击查看更多</div>
</div>
<div id="enterDiv" class="enter_div" >
	<div class="enter_title">录入物流信息</div>
	<div class="enter_content">
		<p style="font-size:14px;"><label style="margin-left:5px;">物流单号：</label><input type="text" id="logicoNo" nullMessage="物流单号不能为空"/></p>
		<p style="font-size:14px;"><label style="margin-left:5px;">物流公司：</label><input type="text" id="logicoComp" nullMessage="请输入物流公司"/></p>
		<p style="text-align:center;margin-top:20px;"><span id="addLogicoBtn" class="button">确定</span><span id="canelLogicoBtn" class="button" style="margin-left:15px;">取消</span></p>
	</div>
</div>
<div id="bgpop" class="bgpop"></div>
<div class="foot_fxied small_foot_fixed">
	<a href="<%=path %>/common/m-index.html">
    	<span class="home"></span>
        <p>首页</p>
    </a>
	<a href="<%=path %>/common/m-projectList.html">
    	<span class="repay"></span>
        <p>项目</p>
    </a>
	<a href="<%=path %>/common/m-transferList.html">
    	<span class="more"></span>
        <p>挂牌</p>
    </a>
	<a class="cur" id="myCenter" href="javascript:void(0);">
    	<span class="my"></span>
        <p>我的</p>
    </a>
</div>
<script type="text/javascript" src="<%=path %>/js/m/enterLogico.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/jPages.js"></script>
</body>
</html>