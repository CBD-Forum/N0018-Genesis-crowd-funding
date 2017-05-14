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
<div class="box">
	<h4 class="orderH4">转让下单</h4>
	<div class="orderBox">
		<div class="orderTit" id="loanName"></div>
		<ul class="orderTab">
			<li class="fl">
				<p class="p1">1</p>
				<p class="p2">转让订单信息填写</p>
			</li>
			<li class="cur fl" style="margin-left:13px;">
				<p class="p1">2</p>
				<p class="p2">支付</p>
			</li>
			<li class="fr">
				<p class="p1">3</p>
				<p class="p2">转让完成</p>
			</li>
		</ul>
		<div class="orderInfo" style="background: #f6f7f9;border:0;">
			<div class="orderAsc clearfix" style="    padding-bottom: 15px;">
				<p style="margin-right: 100px;">出让人：<em id="transferUser"></em></p>
				<p>转让金额：<em id="transferAmt"></em><b class="companyCode"></b></p>
			</div>
			<div class="orderPs clearfix"style="padding-top:20px;">
				<p style="font-size: 16px;color: #333;">回报详情：</p>
				<p id="backContent" style="width:950px;"></p>
			</div>
			<div class="orderPs clearfix" style="padding:30px 0;">
				<p style=" text-align: right; width: 80px;font-size: 16px;color: #333;">备注：</p>
				<p id="saleInput"></p>
			</div>
			<div class="orderPs clearfix">
				<p style="font-size: 16px;color: #333;">发货时间：</p>
				<p id="givTime"></p>
			</div>
		</div>
		<ul class="orderAdd">
			<li>
				<p class="p1" id="addN"></p>
				<p class="fl" id="address"></p>
			</li>
			<li>
				<p class="p1">运费：</p>
				<p class="fl" id="transFee">0.00</p>
			</li>
			<li>
				<p class="p1">实付款：</p>
				<p class="fl ling"><em id="supportAmt"></em><b class="companyCode"></b></p>
			</li>
			<li>
				<p class="p1">可用余额：</p>
				<p class="fl"><i id="balance"></i> <b class="companyCode"></b><a class="chz_btnn"  id="cz_btn">充 入</a></p>
			</p>
			
			<li style="padding:50px 0;">
				<a class="tjdd" id="transBtu">提交订单 </a>
			</li>
		</ul>
		<!-- <div class="orderTip">
			<p class="fl">风险提示：</p>
			<p class="fl p1">请您务必审慎阅读、充分理解协议中相关条款内容，其中包括：<br/>
				1、风险提示条款和特别提示条款；<br/>
				2、与您约定法律适用和管辖的条款；<br/>
				3、其他以粗体标识的重要条款。<br/>
				如您不同意相关协议、公告、规则、操作流程和项目页面承诺，您有权选择不支持；一旦选择支持，即视为您已确知并完全同意相关协议。</p>
		</div> -->
	</div>
</div>
</div>
<div class="sbgpop"></div>
<div class="bgpop" id="bgpop"></div>

<input id="indexFor" type="hidden" namefor="gua"/>
<script type="text/javascript" src="<%=path%>/js/transfer/transferPay-confirm.js"></script>
