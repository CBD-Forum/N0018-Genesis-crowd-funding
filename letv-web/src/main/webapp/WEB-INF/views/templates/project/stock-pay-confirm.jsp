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
		 <div class="step_1 ">
		  <i>1</i>
		 </div>
		 <p>订单信息填写</p>
		</li>
		<li>
		 <div class="step_1 step_2">
		  <i>2</i>
		 </div>
		 <p class="col_blue">支付</p>
		</li>
		<li style="margin-right:0">
		 <div class="step_1">
		  <i>3</i>
		 </div>
		 <p>完成</p>
		</li>
	   </ul>
	   <div class="clearfix plr30" style="margin-bottom:55px;">
	    <div class="fl bg-blue ft18 bg-blue3">
		 <p class="mb30">每份金额：<em class="col3" id="miniInvestAmt">0</em> <b class="companyCode"></b>/份</p>
		 <p class="mb30">投资份数：<em id="buyNum">0</em> 份</p>
		 <p class="mb30">投资金额：<em id="supportAmt">0</em> <b class="companyCode"></b></p>
		</div>
		<div class="fr bg-blue" style='padding: 45px 50px 55px 50px;width:350px;'>
		 <!-- <p class="ft22">预计每日收益： <em id="dailyProfit">0</em> <b class="companyCode"></b></p> -->
		  <p class="clearfix " style="font-size: 18px;">可用余额：<em class="col3" id="balance">0</em> <b class="companyCode"></b><a class="fr chz_btn2" style="    text-align: center;margin:0;" href="<%=path%>/common/recharge.html">充入</a></p>
		 <a class="next_button fr pay_btn" style="    margin: 80px 65px 0 0;" id="investFBtn">确认支付</a><!--  href="<%=path%>/common/stock-complete.html" -->
		</div>
	   </div>
	   </div>
 </div>
 </div>

<input id="indexFor" type="hidden" namefor="enter"/>
<div id="bgpop" class="bgpop" name="bigp"></div>
	
<input id="indexFor" type="hidden" namefor="entry"/>
<script type="text/javascript" src="<%=path%>/js/project/stock-pay-confirm.js"></script>