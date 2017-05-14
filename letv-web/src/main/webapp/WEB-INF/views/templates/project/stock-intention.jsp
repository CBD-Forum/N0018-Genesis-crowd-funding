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
	     <p class="mb30" style="margin-left:97px;"><i>共 <em id="allFenshu1">0</em> 份</i><i>已募集 <em class="col_blue" id="buyFenshu1">0</em> 份</i><i>剩余 <em id="remainsFenshu1">0</em> 份</i></p>
		 <div class="mb30 clearfix">
		  <span class="fl">
		   投资份数：
		  </span>
		  <div class="clearfix fl opt_num" style="width:175px;">
		   <a class="fl" href="javascript:void(0);" id="iJian1">-</a>
		   <input type="text" class="duo" style="text-align:center;" id="fenshuNum1" value="0" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
		   <a class="fr" href="javascript:void(0);" id="iJIa1">+</a>
		  </div>
		  <em class="col_blue"id="miniInvestAmt1">0</em> <b class="companyCode"></b>/份
		 </div>
		 <div class="mb30 clearfix">
		  <span class="fl">
		   投资总额：
		  </span>
		  <em class="col_blue" id="fundAmt5"> 0</em> <b class="companyCode"></b>
		 </div>
		 <div class="mb30 clearfix">
		  <span class="fl">
		   意向金支付比例： 
		  </span>
		  <em class="col_blue"> <t id="depositRatio">0</t>%</em>
		 </div>
		 <div class="mb30 clearfix rel">
		  <p class="fl">
		  <span>
		   支付定金：
		  </span>
		  <em id="depositPay"></em> <b class="companyCode"></b>
		  </p>
		  <!-- <p class="fl col9 ft14 ft_bold ml78">
		      &nbsp;&nbsp;订金支付阶段预计每日收益：<em class="col_blue" id="income1">0.00</em> <b class="companyCode"></b>
		  </p> -->
		 </div>
		 <div class="mb20 clearfix">
		  <p class="fl">
		  <span>
		   需支付尾款：
		  </span>
		  <em class="col_blue" id="retainage"></em> <b class="companyCode"></b>
		  </p>
		  <!-- <p class="fl col9 ft14 ft_bold ml78">尾款补齐后预计每日收益：<em class="col_blue" id="income2">0.00</em> <b class="companyCode"></b></p> -->
		 </div>
		 <div class="clearfix" style="margin-bottom: 35px;">
		  <span class="fl">当前账号余额：</span>
		  <em class="col_blue" id="balance">0</em> <b class="companyCode"></b>
		  <a class="chz_btn2" style=" text-align: center; margin: 0 0 0 20px; display: inline-block;" href="<%=path%>/common/recharge.html">充入</a>
		 </div>
		 <div class="mb30 clearfix">
		  <span class="fl col9 ft14 ft_bold">
		   温馨提示：
		  </span>
		  <p class="fl ft14 col7">剩余尾款需在 <em class="col3" id="completeTime"></em> 前补齐，尾款支付可在 <em class="col3">个人中心-项目管理 </em>中进行操作。如在规定时间<br>内未完成尾款支付，将被视为违约行为，需支付定金的 <em class="col3"><e id="yxjPlatformRatio"></e>%</em> 作为违约罚款。</p>
		 </div>
		 <div class="yxj_btn bottom_btn clearfix rel bgb_btn">
		  <a class="mr20" id="chyBtu">全额付款</a>
		  <a class="cur">意向金订购</a>
		  <i class="wenhao" style="left:410px" id="wen1"></i> 
		  <div class="yxj_jsh" id="wen1jsh" style="display:none;">
		   <p>意向金订购仅需支付全部投资金额的<em id="yxjPayScaleA"></em>作为订金，剩余尾款需在规定时间内完成支付，否则需要支付订金的<em id="yxjPlatformRatioA"></em>作为违约罚款。</p>
		  </div>
		  <a class="next_button" id="confirmBtu">下一步</a>
		 </div>
		</div>
		
	   </div>
	   <!--  href="<%=path%>/common/stock-intention-confirm.html" -->
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

<input id="indexFor" type="hidden" namefor="enter"/>
<div id="bgpop" class="bgpop" name="bigp"></div>
	
<input id="indexFor" type="hidden" namefor="entry"/>
<script type="text/javascript" src="<%=path%>/js/project/stock-intention.js"></script>