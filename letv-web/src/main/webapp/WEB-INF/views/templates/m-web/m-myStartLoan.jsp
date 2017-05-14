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
<title>我发起的项目</title>
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
    <p>我的发起</p>
    <a class="back"></a>
</div>
<div class="equity-info mt50" style="border-bottom:0px;">
	<div class="startloantype clearfix">
		<div>
			<a id="loanTypeA" href="javascript:void(0);" type="0"><i>项目类型</i></a>
			<ul class="pop_div" id="loanType">
			    <li code="" class="cur">全部</li>
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
		<div id="Body">
		</div>
		<div id="public_serviceBody">
		</div>
		<div id="entityBody"></div>
		<div id="houseBody"></div>
		<div id="stockBody"></div>
	</div>
	<div class="list_more" id="loanPage">点击查看更多</div>
</div>
<!-- 点击支付订金弹出框 -->
<div class="tk_box" id="zfdjDiv">
    <ul class="tk_margin">
    	<li class="mb20">* 请您仔细核对打款金额，本次款项为预热申请订金。</li>
        <li class="rmb clearfix"><span>金额：</span><span>(¥)<i id="zfdjAmt"></i>&nbsp;&nbsp;</span></li>
<!--         <li>为项目发起人投资金额的<i id="zfdjPercent"></i>%</li> -->
        <li class="tk_but_qr_qx clearfix">
        	<a href="javascript:void(0);" id="payZfdjBtn">确认支付</a>
            <a href="javascript:void(0);" id="canelPayZfdjBtn">取消</a>
        </li>
    </ul>
</div>
<form id="zfdjForm" action="<%=path%>/fundpool/yeepay/pay/directBindPay.html" method="post" target="_blank">
	<input type="hidden" name="supportAmt"/>
	<input type="hidden" name="loanNo"/>
	<input type="hidden" name="cardTop"/>
	<input type="hidden" name="cardLast"/>
	<input type="hidden" name="payNode" value="pre_preheat"/>
	<input type="hidden" name="payType" value="loanpay" />
	<input type="submit" id="zfdjFormBtn" style="display:none;" />
</form>
<!-- 点击支付尾款弹出框 -->
<div class="tk_box" id="zfwkDiv">
    <ul class="tk_margin">
    	<li class="mb20">* 恭喜您，已有 <i id="t_leaderNum"></i> 位领投人确认认购，在正式融资开始时请将剩余发起人投资金额的 <i id="zfwkPercent"></i>%打入项目托管账户。</li>
        <li class="rmb clearfix"><span>金额：</span><span>(¥)<i id="zfwkAmt"></i>&nbsp;&nbsp;</span></li>
        <li>为项目发起人投资金额的<i id="zfwkPercent1"></i>%</li>
        <li class="tk_but_qr_qx clearfix" style="width:300px;margin-left:105px;">
        	<a href="javascript:void(0);" id="payZfwkBtn" style="padding:1px 10px;">确认支付</a>
            <a href="javascript:void(0);" id="canelPayZfwkBtn" style="padding:1px 10px;">取消</a>
        </li>
    </ul>
</div>
<div id="bgpop" class="bgpop"></div>
<form id="zfwkForm" action="<%=path%>/fundpool/yeepay/pay/directBindPay.html" method="post" target="_blank">
	<input type="hidden" name="supportAmt"/>
	<input type="hidden" name="loanNo"/>
	<input type="hidden" name="cardTop"/>
	<input type="hidden" name="cardLast"/>
	<input type="hidden" name="payNode" value="preheat"/>
	<input type="hidden" name="payType" value="loanpay" />
	<input type="submit" id="zfwkFormBtn" style="display:none;" />
</form>
<!-- 支付 -->
<div id="selCard" class="selCard" style="font-size:14px;">
	<a class="close">关闭</a>
	<div class="title">请选择支付银行卡</div>
	<select id="selBank" class="select">
		<option>请选择</option>
	</select>
	<div class="clearfix" style="width:90%;margin:0 auto;">
		<input type="text" class="select" id="phoneCode" placeholder="手机验证码" maxlength="6" style="width:40%;float:left;padding:0 5px;margin-top:0px;"/>
		<a id="sendBtn" class="btn btn_sendSMS">获取验证码</a>
	</div>
	<a class="btn" id="okPay">确认支付</a>
</div>
<script type="text/javascript" src="<%=path %>/js/m/myStartLoan.js"></script>
</body>
</html>