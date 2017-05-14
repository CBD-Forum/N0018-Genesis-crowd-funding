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
<script type="text/javascript" src="<%=path %>/js/common/m-validate.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/common.js"></script>
<title>资金管理</title>
<script type="text/javascript">
	var path = "<%=path %>" ;
	var userId = "<%=userId%>";
</script>
</head>

<body>
<div class="head_top back1">
    <p>提现</p>
    <div class="back"></div>
</div>
<div style="padding:60px 0px; font-size:14px;">
	<div class="derw_add_bank"><a href="<%=path%>/common/m-addBank.html">添加提现银行卡</a></div>
	<ul class="withdraw_div">
		<li class="clearfix">
			<i class="fl">提现银行卡</i>
			<select id="bankAccount" nullMessage="请添加提现银行卡" class="fl" style="width:66%"><option >请选择提现银行卡</option></select>
		</li>
		<li class="clearfix">
			<i class="fl">可提现金额</i>
			<span id="userBalance"></span>
		</li>
		<li class="clearfix">
			<i class="fl">提现金额</i>
			<input type="text" placeholder="请输入要提现的金额" id="withdrawInput" nullMessage="请输入提现金额" style="width:62%;" />
		</li>
		<li class="clearfix">
			<i class="fl">手续费</i>
			<label style="margin-right:10px;"><input type="radio" id="db" name="pd" checked="checked" />&nbsp;按单笔</label>
<!-- 			<label><input type="radio" id="bl" name="pd" />&nbsp;按比例</label> -->
<!-- 			<span id="poundage" style="margin-left:10px;"></span> -->
		</li>
		<li class="clearfix">
			<i class="fl">手机验证码</i>
			<input type="text" placeholder="请输入手机验证码" id="phoneCode" maxlength="6" nullMessage="请输入手机验证码" class="fl" style="width:31%;" />
			<a href="javascript:void(0);" class="getvertify fr" id="getVertify">获取验证码</a>
		</li>
		<li class="btn" style="margin-top:20px;">
			<a id="withdrawBtn" href="javascript:void(0);">提&nbsp;现</a>
		</li>
	</ul>
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
<div class="bgpop"></div>
<script type="text/javascript" src="<%=path %>/js/m/withdraw.js"></script>
</body>
</html>