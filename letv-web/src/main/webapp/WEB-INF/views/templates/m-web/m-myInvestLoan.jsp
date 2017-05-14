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
<title>我投资的项目</title>
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
    <p>我的投资</p>
    <a class="back"></a>
</div>
<div class="equity-info mt50" style="border-bottom:0px;">
<!-- 	<ul id="loanType" class="invest_title_ul clearfix"> -->
<!-- 		<li class="cur" code="public_service">筹爱心</li> -->
<!-- 		<li code="entity">筹好货</li> -->
<!-- 		<li code="house">筹好房</li> -->
<!-- 		<li code="stock	">筹好业</li> -->
<!-- 	</ul> -->
	<div class="startloantype clearfix">
		<div>
			<a id="loanTypeA" href="javascript:void(0);" type="0"><i>项目类型</i></a>
			<ul class="pop_div" id="loanType">
				<li code="">全部</li>
				<li code="public_service">筹爱心</li>
				<li code="entity">筹好货</li>
				<li code="house">筹好房</li>
				<li code="stock">筹好业</li>
			</ul>
		</div>
		<div>
			<a id="loanProcessA" href="javascript:void(0);" type="0"><i>项目状态</i></a>
			<ul id="loanProcess" class="pop_div"></ul>
		</div>
	</div>
	<div id="loanBody" class="loanbody_div" style="padding:10px;">
		<div id="Body"></div> 
		<div id="public_serviceBody"></div>
		<div id="entityBody"></div>
		<div id="houseBody"></div>
		<div id="stockBody"></div>
	</div>
	<div class="list_more" id="loanPage">点击查看更多</div>
</div>
<div class="bgpop" id="bgpop"></div>
<script type="text/javascript" src="<%=path %>/js/m/myInvestLoan.js"></script>
</body>
</html>