<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%
	String path = request.getContextPath();
	String userId = session.getAttribute("userId") == null ? null
			: session.getAttribute("userId").toString();
	String level = session.getAttribute("investorLevel")==null?"0":session.getAttribute("investorLevel").toString();
	String userLevel = session.getAttribute("userLevel")==null?null:session.getAttribute("userLevel").toString();
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
<title>关于爱筹网</title>
<script type="text/javascript">
	var path = "<%=path%>";
	var userId = "<%=userId%>";
	var investorLevel = "<%=level%>";
	var userLevel = "<%=userLevel%>";
</script>
</head>
<style type="text/css">
	html, body{height:100%;}
</style>

<body class="bgch">
<div class="head_top back1">
    <p>关于爱筹网</p>
    <a class="back"></a>
</div>
<div class="equity-info mt50" style="border-bottom:0px; margin-top:60px;">
	<div class="aboutbox"></div>
</div>
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
<script type="text/javascript" src="<%=path %>/js/m/nodeList_info.js"></script>
</body>
</html>