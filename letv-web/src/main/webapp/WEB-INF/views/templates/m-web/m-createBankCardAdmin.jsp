<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%
	String path = request.getContextPath();
	String userId = session.getAttribute("userId") == null ? null : session.getAttribute("userId").toString();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes" />
<META HTTP-EQUIV="Expires" CONTENT="0">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link rel="stylesheet" href="<%=path %>/style/web.css" />
<link rel="stylesheet" href="<%=path %>/style/m-ruoshui.css" />
<script type="text/javascript" src="<%=path%>/js/common/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/m-validate.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/common.js"></script>

<title>个人中心-支付绑卡管理</title>
<script type="text/javascript">
	var path = "<%=path%>";
	var userId = "<%=userId%>";
</script>
</head>

<body class="bgch">
	<div class="head_top back1">
	    <p>支付绑卡管理</p>
	    <a class="back"></a>
	</div>
	<div class="bindBankCard mt70 mb70">
	<a href="<%=path%>/common/m-createBankCard.html" style="margin:0 0 15px 10%;display: block;text-decoration: underline;color:#3b9ee3;font-size:16px;">添加支付绑定银行卡</a>
		<ul class="cardAdmin" id="bindBankList">
		</ul>
	</div>
	<ul class="foot_pos">
		<li><a href="<%=path %>/common/m-index.html"><p class="home"></p>首页</a></li>
		<li><a href="<%=path%>/common/m-projectList-stock.html?isProjectPay=1"><p class="pro"></p>项目融资</a></li>
		<li><a href="<%=path%>/common/m-projectList.html?type=entity"><p class="cp"></p>产品众筹</a></li>
	    <li><a class="col1" href="javascript:void(0);"><p class="myl"></p>我的</a></li>
	</ul>
	<div id="bgpop" class="bgpop"></div>
	<script type="text/javascript" src="<%=path%>/js/m/createBankCardAdmin.js"></script>
</body>
</html>