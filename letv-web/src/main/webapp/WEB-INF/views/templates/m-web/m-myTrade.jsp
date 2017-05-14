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
<div class="head_top back1" style=" border-bottom:1px solid #ccc;">
    <p>资金管理</p>
    <div class="back"></div>
</div>
<div class="cen_trade_auth" style="margin-top:50px; font-size:15px;">可用余额(元)：<label id="userBalance"></label></div>
<div id="tradeList" style="padding-bottom:40px;">
	<ul id="loanType" class="invest_title_ul title_ul2 clearfix">
		<li code="trade_type_person">收支明细</li>
		<li code="withdraw_state">提现记录</li>
	</ul>
<!-- 	<ul class="mon_ul" style="padding-bottom:60px;"></ul> -->
	<div id="detailBody" class="loanbody_div" style="padding:10px;">
		<div id="trade_type_personBody"></div>
		<div id="withdraw_stateBody"></div>
	</div>
	<div style="padding-bottom:10px" class="list_more" id="moreList">点击查看更多</div>
</div>
<div class="trade-bottom">
	<a href="<%=path%>/common/m-withdraw.html">提&nbsp;&nbsp;款</a>
</div>
<script type="text/javascript" src="<%=path %>/js/m/myTrade.js"></script>
</body>
</html>