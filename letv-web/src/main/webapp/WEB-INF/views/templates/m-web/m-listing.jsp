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
<title>我的挂牌</title>
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
    <p>我的挂牌</p>
    <a class="back"></a>
</div>
<div class="equity-info mt50" style="border-bottom:0px;">
	<ul id="loanType" class="invest_title_ul title_ul5 clearfix">
		<li code="canListing">可挂牌</li>
		<li code="Auditing">审核中</li>
		<li code="Audited">已审核</li>
		<li code="Listing">挂牌中</li>
		<li code="lisTingEnd">挂牌完成</li>
	</ul>
	<div id="loanBody" class="loanbody_div" style="padding:10px;">
		<div id="canListingBody"></div>
		<div id="AuditingBody"></div>
		<div id="AuditedBody"></div>
		<div id="ListingBody"></div>
		<div id="lisTingEndBody"></div>
	</div>
	<div class="list_more" id="loanPage">点击查看更多</div>
</div>
<div id="bgpop" class="bgpop"></div>
<!-- 申请挂牌弹出层 -->
<div id="alerApplyDiv" class="alerApply_div">
	<ul>
		<li>挂牌份数：<input type="text" id="totalParts" />份</li>
		<li>挂牌单价：<input type="text" id="perAmt" />元</li>
		<li>挂牌天数：<input type="text" id="transferDay" />天</li>
	</ul>
	<div class="apply_btn">
		<a id="sendListing" href="javascript:void(0);">确定</a>
		<a id="cancel" href="javascript:void(0);">取消</a>
	</div>
</div>
<script type="text/javascript" src="<%=path %>/js/m/listing.js"></script>
</body>
</html>