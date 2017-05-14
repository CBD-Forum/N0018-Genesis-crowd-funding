<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
String thirdNo = (String)session.getAttribute("thirdNo");
%>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/ueditor/third-party/webuploader/webuploader.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/demo.css"/>
<script type="text/javascript">
	var thirdNo = "<%=thirdNo%>"; //是否需要绑定用户
</script>
<div class="back_colf9">
  <div class="box">
   <h3 class="pay_title">非公开融资下单</h3>
   <div class="pay_box mb70">
	   <h4 id="loanName"></h4>
	   <ul class="clearfix step_pay">
		<li>
		 <div class="step_1 step_2">
		  <i>1</i>
		 </div>
		 <p class="col_blue">订单信息</p>
		</li>
		<li>
		 <div class="step_1">
		  <i>2</i>
		 </div>
		 <p>支付</p>
		</li>
		<li style="margin-right:0">
		 <div class="step_1">
		  <i>3</i>
		 </div>
		 <p>完成</p>
		</li>
	   </ul>
	   <div class="gq_info clearfix" id="ltrInfo">
	   	<dl class="tzr fl" id="tzr" style="display:none;">
        	<dt>请选择领投人</dt>
        	<div id="ltrList" style="margin-top:10px;"></div>
        </dl>
	    <div class="det_inf fl">
		 <p class="mb30"><i>共 <em id="allFenshu1">0</em> 份</i><i>已募集 <em class="col_blue" id="buyFenshu1">0</em> 份</i><i>剩余 <em id="remainsFenshu1">0</em> 份</i></p>
		 <div class="mb30 clearfix">
		  <span class="fl">
		   投资份数：
		  </span>
		  <div class="clearfix fl opt_num" style="width:175px;">
		   <a class="fl" href="javascript:void(0);" id="iJian1">-</a>
		   <input type="text" class="duo" style="text-align:center;" id="fenshuNum1" value="0" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
		   <a class="fr" href="javascript:void(0);" id="iJIa1">+</a>
		  </div>
		  <em class="col_blue" id="miniInvestAmt1"></em> <b class="companyCode"></b>/份
		 </div>
		 <div class="clearfix" style="margin-bottom: 20px;">
		  <span class="fl">
		   投资总额：
		  </span>
		  <em class="col_blue" id="fundAmt5"> 0</em> <b class="companyCode"></b>
		 </div>
		 <div class="clearfix" style="margin-bottom: 35px;">
		  <span class="fl">当前账号余额：</span>
		  <em class="col_blue" id="balance">0</em> <b class="companyCode"></b>
		  <a class="chz_btn2" style=" text-align: center; margin: 0 0 0 20px; display: inline-block;" href="<%=path%>/common/recharge.html">充入</a>
		 </div>
		 <!-- <div class="mb30 clearfix rel">
		  <span class="fl">
		   预计每日收益：
		  </span>
		  <em id=""> 0</em> <b class="companyCode"></b><i class="wenhao"></i>
		  <span class="fl" style="width: auto;"><em id="couInvestZgbl1"> 0</em> <b class="companyCode"></b></span><i class="wenhao" id="wen2" style="position:inherit;left:0;margin:0;float:left;"></i>
		  <div class="yxj_jsh" id="wen2jsh" style="display:none;left: 260px;height: 140px;padding-top:5px;">
		   <p>投资成功后平台会于次日00:30后根据预计每日收益率结算昨日收益。<br/>收益计算公式：预计每日收益=投资本金*预计每日收益率</p>
		  </div>
		 </div> -->
		 <div class="yxj_btn clearfix rel bgb_btn" id="yxjNone" style="display:none;">
		  <a class="cur mr20">全额付款</a>
		  <a id="yxjBtu">意向金订购</a>
		  <i class="wenhao" style="left:410px" id="wen1"></i>
		  <div class="yxj_jsh" id="wen1jsh" style="display:none;">
		   <p>意向金订购仅需支付全部投资金额的<em id="yxjPayScale"></em>作为订金，剩余尾款需在规定时间内完成支付，否则需要支付订金的<em id="yxjPlatformRatio"></em>作为违约罚款。</p>
		  </div>
		 </div>
		 
		</div>
		
	   </div>
	   <a class="next_button" id="confirmBtu">下一步</a><!--  href="<%=path%>/common/stock-pay-confirm.html" -->
	   <div class="wx_tishi clearfix bord_top">
	   <div class="fl">
	    风险提示：
	   </div>
	   <div class="fl col9">
	    <p>请您务必审慎阅读、充分理解协议中相关条款内容，其中包括：</p>
		<p>1、风险提示条款和特别提示条款；</p>
		<p>2、与您约定法律适用和管辖的条款；</p>
		<p>3、其他以粗体标识的重要条款。</p>
		<p>如您不同意相关协议、公告、规则、操作流程和项目页面承诺，您有权选择不支持；一旦选择支持，即视为您已确知并完全同意相关协议。</p>
	   </div>
	  </div>
  </div>
 </div>
</div>
<input type="hidden" id="dailyEarningsForecast"/>

<input id="indexFor" type="hidden" namefor="enter"/>
<div id="bgpop" class="bgpop" name="bigp"></div>
	
<input id="indexFor" type="hidden" namefor="entry"/>
<script type="text/javascript" src="<%=path%>/js/project/stock-pay.js"></script>