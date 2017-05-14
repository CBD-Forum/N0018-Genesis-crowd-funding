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
<script type="text/javascript" src="<%=path %>/js/common/m-validate.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/common.js"></script>
<title>我要跟投</title>
<script type="text/javascript">
	var path = "<%=path %>" ;
	var userId = "<%=userId%>";
</script>
</head>

<body>
<div class="head_top back1">
    <p>我要投资</p>
    <div class="back"></div>
</div>
<div class="center_s investsjs">
	<div id="ltrTitle" class="ltrTitle">请选择领投人：</div>
	<div id="ltrList" class="ltrsjs"></div>
	<ul class="sjsinvest">
       	<li class="rgNum clearfix" style="height:auto; padding-bottom:5px; ">
           	认购份数：¥<span class="orl" id="miniInvestAmt1"></span>(单份投资额)
           	<div class="clearfix" style="margin-left:30px;">
              <a href="javascript:void(0);" class="jia" id="iJian">-</a>
               <input type="text" class="duo" style="text-align:center;" id="fenshuNum" value="1" nullMessage="请输入大于0且小于剩余份数的值"/>
           		<a href="javascript:void(0);" class="jia" id="iJIa">+</a>
               <p class="fl" style="font-size:16px;">&nbsp;&nbsp;份</p>
           	</div>
        </li>
        <li>
         	当前剩余份数：<span class="orl" id="remainsFenshu"></span>份
        </li>
        <li class="clearfix">
        	<span class="fl">验证码：</span><input type="text" class="yzm fl" id="investValiInput"  nullMessage="请输入验证码"/>
                <p class="yzm_a fl">
                    <img alt="点击刷新" id="imageInvest" style="cursor:pointer;"
									src="<%=path%>/security/securityCodeImage.html"
									onclick="this.src='<%=path%>/security/securityCodeImage.html?'+new Date().getTime()" /><br />
                </p>
        </li>
        <li id="payWay" class="paywayli clearfix" style="height:auto; line-height:40px;">
         	<span class="fl">支付方式：</span>
         	<br />
         	<label style="margin-left:15px; color:#333;"><input id="depositPay" type="radio" name="pay" checked="checked" />&nbsp;保证金支付（保证金比例：<em id="depositPayEm"></em>%）</label>
         	<br />
         	<label style="margin-left:15px; color:#333;"><input id="onePay" type="radio" name="pay" />&nbsp;一次性支付</label>
        </li>
        <li>
        	投资总额：<span class="orl"><i id="fundAmt4">0.00</i>元</span>
        </li>
        <li>
        	支付金额：<span class="orl"><i id="paywayAmt">0.00</i>元</span>
        </li>
        <li id="bonusesDiv">
        	分红比例：<span class="orl"><i id="couInvestZgbl">0.00</i>%</span>
        </li>
	</ul>
	<input id="financeNum" type="hidden" />
	<input id="buyNum" type="hidden" />
	<input id="fundAmt" type="hidden" />
	<input id="fundAmt1" type="hidden" />
	<input id="projectFinanceAmt" type="hidden" />
	<input id="qtyxhhr_zgbl" type="hidden" />
	<a id="investLBtn" class="gt_bottom_btn sjs_bottom_btn">确认订单</a>
</div>
<form id="supFomr" action="<%=path%>/fundpool/yeepay/pay/directBindPay.html" method="post" target="_blank">
	<input type="hidden" name="buyNum" id="formBuyNum" />
	<input type="hidden" name="loanNo" id="formLoanNo" />
	<input type="hidden" name="actualPayAmt" id="formActualPayAmt" />
	<input type="hidden" name="supportAmt" id="formSupportAmt" />
	<input type="hidden" name="investType" value="firstInves"/>
	<input type="hidden" name="payWay"  id="formPayType"/>
	<input type="hidden" name="cardTop" id="cardTop"/>
	<input type="hidden" name="cardLast" id="cardLast"/>
	<input type="hidden" name="payType" id="payType" value="invest" />
	
	<input type="submit" id="supFormBtn" style="display:none;"/>
</form>
<!-- 我要领投form -->
<form id="lForm" action="<%=path%>/fundpool/yeepay/pay/directBindPay.html" method="post" target="_blank">
	<input type="hidden" name="buyNum" />
	<input type="hidden" name="loanNo" />
	<input type="hidden" name="applyLeadDes" />
	
	<input type="hidden" name="cardTop"/>
	<input type="hidden" name="cardLast" />
	<input type="hidden" name="payType" value="leaderpay" />
	
	<input type="submit" id="lFormBtn" style="display:none;"/>
</form>
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
<script type="text/javascript" src="<%=path %>/js/m/withVote.js"></script>
</body>
</html>