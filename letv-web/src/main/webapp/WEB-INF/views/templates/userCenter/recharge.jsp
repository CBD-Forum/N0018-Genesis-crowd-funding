<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>
<style>
.header2{ border-bottom: 3px solid #1c2024;}
.bai_box{ background-color: #f4f3f3;overflow:hidden;padding-bottom:30px;}
</style>
<div class="bai_box">
    <div class="box pt30" style="background-color: #fff;">
    	<div class="realName">
			<h4 class="tit_xt">充入</h4>
			<div>
				
				<ul class="rechargeUl">
					<li>
						<p class="p1">账户余额</p>
						<p class="p2"><span id="Rbalance"></span> <b class="companyCode"></b></p>
					</li>
					<li style="position:relative;">
						<p class="p1">充入金额</p>
						<input class="shuMon" type="text" id="rechargeAmount">
						<i class="tian yuan" style="top:0;right:0;width:30px;">元</i>
						<span style="padding-left: 10px;">充入限额<a style="color:#44b4ff;" target="_blank" href="<%=path %>/common/quotaSee.html">查看</a></span>
					</li>
					<li>
						<p class="p1">实际到账<b class="companyCode"></b></p>
						<p><em id="actualAmount">0</em> <b class="companyCode"></b></p>
					</li>
				</ul>
				<a class="rechargeBtu" id="recharge">确认充入</a>
			</div>
			<div class="realTipDiv">
				<p>温馨提示：</p>
				<p>1.用户只有实名认证通过并开通会员后才能进行充入操作；</p>
				<p>2.充入成功后，您的充入金额将以众筹<b class="companyCode"></b>的形式展现，充入金额和众筹<b class="companyCode"></b>的兑换比例为1:1 ；</p>
				<p>3.您的众筹<b class="companyCode"></b>预计会在充入成功后的30分钟之内到账，请您耐心等待。充入结果我们会以短信和站内信的形式发送给您，期间您也可通过<b><a  target="_blank" href="<%=path %>/common/myTrade.html">个人中心-交易记录</a></b>查看充入到账情况。</p>
			</div>
		</div>
    </div>
</div>
<div class="bgpop"></div>
<form method="post" action="<%=path %>/sxyPay/recharge/createRechargeRequest.html" target="_blank">
    <input type="hidden" id="form_rechargeAmount" name="rechargeAmt"/>
    <input id="submitBtn"  type="submit" style="display:none" />
</form>
<script type="text/javascript" src="<%=path%>/js/common/validate.js"></script>
<script type="text/javascript" src="<%=path%>/js/userCenter/modifyData.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>

<script type="text/javascript" src="<%=path%>/js/userCenter/recharge.js"></script>