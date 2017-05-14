<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>
<style>
.header2{ border-bottom: 3px solid #1c2024;}
</style>
<div class="back_colh">
  <div class="box gy_info gy_info2">
   <h3 class="chz-tit">申请提出</h3>
   <p class="card_line">绑定的银行卡</p>
   <div class="recharge-info">
	   <div class="clearfix bankCard_tab" id="bankCard_tab" nullmessage="请输入选择提出银行卡"></div>
	   <div class="chz_input">
	    <p class="mt35">
		 <span>可用<b class="companyCode"></b></span><em id="userBalance" num="">0.00</em><b class="companyCode"></b>
		</p>
		<p class="rel mt20">
		 <span>提出金额</span>
		 <input type="text" id="withdrawInput" nullmessage="请输入要提出的金额"><i><b class="companyCode"></b></i>
		 <span style="width:170px;">(单笔提现金额低于100万)</span>
		</p>
		<p class="rel mt20">
		 <span>提出费用</span>
		 <em id="withdrawFee" num="">0.00</em><b class="companyCode"></b> (提出费用将从您账户余额扣除)
		</p>
		<p class="mt20">
		 <span>实际到账金额</span><em id="withdrawAmt" num="">0.00</em>元
		</p>
		<div class="mt20 clearfix">
		 <span class="fl">手机验证码</span>
		 <input type="text" placeholder="" class="fl wid140" id="phoneCode" nullMessage="请输入手机验证码">
		 <a class="get-num fl" id="getVertify">获取验证码</a>
		</div>
	   </div>
		<div class="clearfix operat_btn" style="margin:0 0 110px 0">
		 <a class="cur wid225" id="withdrawBtn">提出</a>
		</div>
	</div>
	<div class="tishi_div">
	 <p>温馨提示：</p>
	 <p>1.只有实名认证通过、开通会员并且绑卡成功后才能进行提出操作；</p>
	 <p>2.账户余额大于等于12<b class="companyCode"></b>才能申请提出；</p>
	 <p>3.提出需要扣除手续费，从账户余额中扣除，提出手续费2<b class="companyCode"></b>/笔；</p>
	 <p>4.提出成功后，您的提出金额将以人民币的形式转账到您的提出银行卡，提出金额和众筹<b class="companyCode"></b>的兑换比例为1:1；</p>
	 <p>5.您的提出金额会在提出申请受理成功后的1-3个工作日到账，请您耐心等待。提出结果我们会以短信和站内信的形式发送给您，期间您也可通过<b><a  target="_blank" href="<%=path %>/common/myTrade.html">个人中心-交易记录</a></b>查看提出到账情况。</p>
	</div>
   </div>
</div>
<div id="bgpop" class="bgpop"></div>
<div id="bgpop_wait" class="bgpop_wait"></div>
<script type="text/javascript" src="<%=path%>/js/userCenter/modifyData.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>

<script type="text/javascript" src="<%=path%>/js/common/validate.js"></script>
<script type="text/javascript" src="<%=path%>/js/userCenter/withdraw.js"></script>