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
<script type="text/javascript" src="<%=path %>/js/common/jquery.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/common.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/TouchSlide.1.1.js"></script>
<title>支付详情</title>
<script type="text/javascript">
	var path = "<%=path%>";
	var userId = "<%=userId%>";
</script>
</head>

<body>
<div class="head_top back1">
    <p>支付详情</p>
    <a class="back" href="javascript:void(0);"></a>
</div>
<div class="center_s">
	<p class="gt_tit">收货人信息</p>
	<ul id="addressList"></ul>
	<p class="gt_tit">支持回报</p>
	<ul>
		<li style="min-height:70px;background:none;">
			<p style="line-height:30px;" id="supContent"></p>
			<p style="line-height:30px;color:#a9a9a9;" id="supBackTime"></p>
		</li>
	</ul>
	<p class="gt_tit">付款信息</p>
	<ul>
		<li style="height:70px;line-height:70px;background:none;">
			<p style="line-height:30px;">
				支持金额
				<span style="float:right;color:" id="supAmt"></span>
			</p>
<!-- 			<p style="line-height:30px;"> -->
<!-- 				运费 -->
<!-- 				<span style="float:right;color:" id="supTransFee"></span> -->
<!-- 			</p> -->
		</li>
		<li style="color:#ff500b;background:none; height:50px; line-height:50px;">
			总计<span style="float:right;color:" id="suphAmt"></span>
		</li>
	</ul>
	<div style="width:80%;text-align:center;margin:0 auto; padding-bottom:50px;">
		<a class="registerbtn" style="color:#fff;" id="supportBtn" href="javascript:void(0);">确定支持</a>
	</div>
	<form id="supFomr" action="<%=path%>/fundpool/yeepay/pay/directBindPay.html" method="post">
   		<input type="hidden" name="supportAmt" id="subSupportAmt" />
   		<input type="hidden" name="loanNo" id="subLoanNo" />
   		<input type="hidden" name="backNo" id="subBackNo" />
		<input type="hidden" name="cardTop" id="cardTop"/>
		<input type="hidden" name="cardLast" id="cardLast"/>
		<input type="hidden" name="payType" id="payType" value="invest" />   		
   		<input type="hidden" name="invstType" id="invstType" value="commonInvest"/>
   		<input type="hidden" name="postAddressNo" id="subPostAddressNo" />
   		<input type="submit" id="supFormBtn" style="display:none;"/>
   </form>
</div>
<!-- 支付 -->
<div id="selCard" class="selCard">
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
<div class="bgpop"></div>
<script type="text/javascript" src="<%=path %>/js/m/support-entity.js"></script>
</body>
</html>