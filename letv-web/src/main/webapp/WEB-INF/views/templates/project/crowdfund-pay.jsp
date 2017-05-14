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
<style>
.header2{ border-bottom: 3px solid #1c2024;}
</style>
<div class="back_colf9">
  <div class="box">
   <h3 class="pay_title">众筹下单</h3>
   <div class="pay_box mb70">
	   <h4><a id="supLoanName"></a></h4>
	   <ul class="clearfix step_pay">
		<li>
		 <div class="step_1">
		  <i>1</i>
		 </div>
		 <p>订单信息填写</p>
		</li>
		<li>
		 <div class="step_1  step_2">
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
	   <div class="zhc_box">
	    <p class="clearfix shy_xq " style="width:645px;">
		 <span class="fl ml45">支持 <em id="supAmt"></em> <b class="companyCode"></b></span>
		 <!-- <span class="fr">预计每日收益：<em id="dailyProfit"></em> <b class="companyCode"></b></span> -->
		</p>
		<p class="mb30 qr_info"><span>运费：</span><i id="suphAmt"></i></p>
		<p class="qr_info mb23"><span>实付款：</span><i class="col_blue"  id='paymentAmt'></i> <b class="companyCode"></b></p>
		<p class="qr_info shy_xq" style="width:360px;"><span>当前可用余额：</span><i id="balance"></i> <b class="companyCode"></b><!-- <a class="chz_btnn"  id="cz_btn">充 入</a> --></p>
		
	   </div>
	   <div class="zhc_box2" style="    padding: 55px 80px 180px 80px;">
	    <span style="display: inline-block;text-align: right; width: 112px;">回报内容：</span>
		<p class="mt7" style="padding-left: 30px;" id="supContent"></p>
		<p class="mt30" style="display:none;"><span style="display: inline-block;text-align: right; width: 112px;">收货地址： </span><i id="address"></i></p>
		<p class="mt30" style="display:none;"><span style="display: inline-block;text-align: right; width: 112px;">邮箱：</span><i id="mailbox"></i></p>
		<p class="mt30"><span style="display: inline-block;text-align: right; width: 112px;">备注：</span><i id="remark"></i></p>
		 <div class="clearfix operat_btn" style="margin:94px 0 0 105px;">
		 <a style="margin-right:30px;" href="javascript:history.go(-1);">返回上一级</a>
		 <a class="cur" id="supportBtn">确认支持</a>
		</div>
	   </div>
	   
	   </div>
	  </div>
  </div>

<div id="bgpop" class="bgpop" name="bigp"></div>
<input id="indexFor" type="hidden" namefor="loan"/>
<script type="text/javascript" src="<%=path%>/js/project/crowdfund-pay.js"></script>