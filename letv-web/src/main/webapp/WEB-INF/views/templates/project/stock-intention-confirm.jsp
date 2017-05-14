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
		 <p>订单信息</p>
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
	   <div class="gq_info" style="margin-bottom:55px;">
	    <div class="det_inf" style="margin-left:220px;">
		 <div class="mb30 clearfix">
		  <span class="fl">
		    每份金额： 
		  </span>
		  <em id="miniInvestAmt">  0 </em><b class="companyCode"></b>/份
		 </div>
		 <div class="mb30 clearfix">
		  <span class="fl">
		    投资份数：
		  </span>
		  <em class="col_blue" id="buyNum"> 0 </em>份
		 </div>
		 <div class="mb30 clearfix">
		  <span class="fl">
		    投资金额：
		  </span>
		  <em class="col_blue" id="supportAmt"> 0 </em><b class="companyCode"></b>
		 </div>
		 <div class="mb30 clearfix">
		  <span class="fl">
		    意向金订购支付比例：
		  </span>
		  <em> <t id="depositRatio">0</t>%</em>
		 </div>
		 <div class="mb30 clearfix">
		  <p class="fl">
		  <span>
		   支付定金：
		  </span>
		  <em id="depositPay"> 0</em> <b class="companyCode"></b>
		  </p>
		  <!-- <p class="fl col9 ft14 ft_bold ml78">
		      &nbsp;&nbsp;定金支付预计每日收益：<em class="col_blue" id="income1">0.00</em> <b class="companyCode"></b>
		  </p> -->
		 </div>
		 <div class="mb20 clearfix">
		  <p class="fl">
		  <span>
		    需支付尾款金额：  
		  </span>
		  <em id="retainage">0  </em><b class="companyCode"></b>
		  </p>
		  <!-- <p class="fl col9 ft14 ft_bold ml78">尾款补齐后预计每日收益： <em class="col_blue" id="income2">0.00</em> <b class="companyCode"></b></p> -->
		 </div>
		 <p class="clearfix mb20"><span>可用余额：</span><em class="col3" id="balance">0</em> <b class="companyCode"></b><a class="next_button fr chz_btn2" style="margin:0 150px 0 0" href="<%=path%>/common/recharge.html">充入</a></p>
		 <div class="mb30 clearfix">
		  <span class="fl col9 ft14 ft_bold">
		   温馨提示：
		  </span>
		  <p class="fl ft14 col7" style="width: 420px;">剩余尾款需在 <em class="col3" id="completeTime" ></em> 前补齐，尾款支付可在 <em class="col3">个人中心-项目管理 </em>中进行操作。如在规定时间内未完成尾款支付，将被视为违约行为，需支付定金的 <em class="col3"><e id="yxjPlatformRatio">0</e>%</em> 作为违约罚款。</p>
		 </div>
		 <a class="next_button" id="investFBtn">确认支付</a><!--  href="<%=path%>/common/stock-complete.html" -->
		</div>
	   </div>
	   
  </div>
 </div>
</div>
<input id="indexFor" type="hidden" namefor="enter"/>
<div id="bgpop" class="bgpop" name="bigp"></div>
	
<input id="indexFor" type="hidden" namefor="entry"/>
<script type="text/javascript" src="<%=path%>/js/project/stock-intention-confirm.js"></script>