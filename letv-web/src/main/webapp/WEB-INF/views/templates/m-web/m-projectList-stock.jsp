<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%
	String path = request.getContextPath();
	String userId = session.getAttribute("userId") == null ? null
			: session.getAttribute("userId").toString();
	String level = session.getAttribute("investorLevel")==null?"0":session.getAttribute("investorLevel").toString();
	String photoUrl = session.getAttribute("userphoto") == null ? null
			: session.getAttribute("userphoto").toString(); 
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
<script type="text/javascript" src="<%=path %>/js/common/jquery.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/common.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/TouchSlide.1.1.js"></script>
<title>项目列表</title>
<script type="text/javascript">
	var path = "<%=path%>";
	var userId = "<%=userId%>";
	var investorLevel = "<%=level%>";
	var userPhoto = "<%=photoUrl%>";
	var userLevel = "<%=userLevel%>";
</script>
</head>

<body>
<div class="head_top back1">
    <p id="pName"></p>
    <div class="back"></div>
    <div id="indexHw" class="right"></div>
</div>
<ul class="loanlist_tab" id="loanTab">
	<li code="all">全部</li>
	<li code="preheat">预热</li>
	<li code="funding">众筹</li>
	<li code="success">成功</li>
</ul>
<div id="loadBody" style="margin-bottom: 65px;">
	<div id="allBody">
		<div class="body"></div>
		<input type="hidden" />
		<div class="list_more" id="allMoreList">点击查看更多</div>
	</div>
	<div id="preheatBody">
		<div class="body"></div>
		<input type="hidden" />
		<div class="list_more" id="preheatMoreList">点击查看更多</div>
	</div>
	<div id="fundingBody">
		<div class="body"></div>
		<input type="hidden" />
		<div class="list_more" id="fundingMoreList">点击查看更多</div>
	</div>
	<div id="successBody">
		<div class="body"></div>
		<input type="hidden" />
		<div class="list_more" id="successMoreList">点击查看更多</div>
	</div>
</div>
<div id="bgpop" class="bgpop"></div>
<div id="loanNoInvestor" class="noinvestortip">
	<div class="title">温馨提示</div>
	<div class="info">
		<p>您还不是若水的专业投资人，认证后可参与投资！</p>
		<div>
			<a href="/ruoshui-web/common/m-centerRZ.html?type=ltr" class="back4" style="margin-right:10px;background:#00a388;">专业投资人</a>
			<a href="/ruoshui-web/common/m-centerRZ.html?type=gtr" style="background:#37abfc;">合格投资人</a>
		</div>
	</div>
</div>
<ul class="foot_pos">
	<li><a href="<%=path %>/common/m-index.html"><p class="home"></p>首页</a></li>
	<li id="xiangmuLi"></li>
	<li><a href="<%=path%>/common/m-projectList.html?type=entity"><p class="cp"></p>产品众筹</a></li>
	<%if(userId == null){ %>
    <li><a href="<%=path%>/common/m-login.html"><p class="my"></p>我的</a></li>
    <%}else{ %>
    <li><a href="<%=path%>/common/m-myCenter.html"><p class="my"></p>我的</a></li>
    <%} %>
</ul>
<script type="text/javascript" src="<%=path %>/js/m/projectList-stock.js"></script>
</body>
</html>