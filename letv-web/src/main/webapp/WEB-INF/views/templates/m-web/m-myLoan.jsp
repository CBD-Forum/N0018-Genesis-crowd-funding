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
<title>个人中心</title>
<script type="text/javascript">
	var path = "<%=path%>";
	var userId = "<%=userId%>";
</script>
</head>

<body>
<div class="head_top back1">
    <p>我的项目</p>
    <div class="back"></div>
</div>
<div class="center_pro mt50" style="padding-bottom:60px;">
	<div class="clearfix">
   	<ul class="rz_tab clearfix" id="centerLoanTab">
       <li><a href="javascript:void(0);" class="a_home" name="invest">投资项目</a></li>
       <li><a href="javascript:void(0);" name="enter">发起项目</a></li>
     </ul>
       <div id="centerLoanTabBody">
       	<div id="investBody">
        	<dl class="scr_sx clearfix" style="background:#eee!important" id="centerLoanType">
<!--              <dd style="width:25%;"><a href="javascript:void(0);" code="public_service" class="cur">公益众筹</a></dd> -->
             <dd style="width:25%;"><a class="cur" href="javascript:void(0);" code="entity">产品众筹</a></dd>
             <dd style="width:25%;"><a href="javascript:void(0);" code="stock">权益众筹</a></dd>
<!--              <dd style="width:25%;"><a href="javascript:void(0);" code="house">房产众筹</a></dd> -->
         	</dl>
         <dl class="scr_sx clearfix" id="centerLoanSonTab" style="display:none;">
             <dd><a href="javascript:void(0);"  name="order" class="cur" furl="/crowdfundUserCenter/getMyInterviewList.html">我的约谈</a></dd>
             <dd><a style="margin-left:10px;" href="javascript:void(0);"  name="yg" furl="/crowdfundUserCenter/getMyPreSupportList.html">我的预购</a></dd>
             <dd><a style="margin-left:10px;" href="javascript:void(0);"  name="rg"  furl="/crowdfundUserCenter/getMySupportList.html">我的认购</a></dd>
             <dd><a style="margin-left:10px;" href="javascript:void(0);"  name="attent" furl="/crowdfundUserCenter/getMyAttentionList.html">我的关注</a></dd>
         </dl>
         <dl class="scr_sx clearfix" id="centerLoanSonTab2">
             <dd><a href="javascript:void(0);"  name="rgg" class="cur" furl="/crowdfundUserCenter/getMySupportList.html">我的认购</a></dd>
             <dd><a href="javascript:void(0);" style="margin-left:10px;"  name="attent" furl="/crowdfundUserCenter/getMyAttentionList.html">我的关注</a></dd>
         </dl>
         <div id="centerLoanTitleDiv">
         	<!-- <dl class="sx_list bb_border clearfix" style="font-weight:bold;" id="orderTitle">
              <dd style="width:23%;">名称</dd>
              <dd style="width:23%;">筹资目标</dd>
              <dd style="width:23%;">发起人</dd>
              <dd style="width:25%;">时间</dd>
          </dl> -->
          <!-- <dl class="sx_list bb_border clearfix" style="display:none;font-weight:bold;"  id="ygTitle">
              <dd style="width:180px;">项目编号</dd>
              <dd style="width:230px;">项目名称</dd>
              <dd style="width:130px;">预购金额</dd>
              <dd style="width:130px;">项目融资金额</dd>
              <dd style="width:180px;">预购时间</dd>
          </dl> -->
          <!-- <dl class="sx_list bb_border clearfix" style="display:none;font-weight:bold;" id="rgTitle">
              <dd style="width:120px;">名称</dd>
              <dd style="width:120px;">认购金额</dd>
              <dd style="width:120px;">筹资目标</dd>
              <dd style="width:120px;">状态</dd>
              <dd style="width:120px;">发起人</dd>
              <dd style="width:120px;">领投人</dd>
              <dd style="width:120px;">操作</dd>
          </dl> -->
          <!-- <dl class="sx_list bb_border clearfix" style="display:none;font-weight:bold;" id="rggTitle">
              <dd style="width:190px;">名称</dd>
              <dd style="width:130px;">认购金额</dd>
              <dd style="width:130px;">筹资目标</dd>
              <dd style="width:100px;">状态</dd>
              <dd style="width:160px;">认购时间</dd>
              <dd style="width:130px;">操作</dd>
          </dl> -->
          <!-- <dl class="sx_list bb_border clearfix" style="display:none;font-weight:bold;" id="attentTitle">
              <dd style="width:21%;">名称</dd>
              <dd style="width:15%;">筹资目标</dd>
              <dd style="width:15%;">状态</dd>
              <dd style="width:15%;">发起人</dd>
              <dd style="width:15%;">关注时间</dd>
              <dd style="width:11%;">操作</dd>
          </dl> -->
         </div>
         <div id="centerLoanSonBody">
         	<div id="orderBody"><div></div></div>
         	<div class="page" id="orderPage"></div>
         </div>
        </div>
        <div id="enterBody" style="display:none;">
        	<dl class="scr_sx clearfix" style="background:#eee!important" id="enterLoanType">
         	<!-- <dt style="font-weight: bold;">类别</dt> -->
<!-- 		     <dd><a href="javascript:void(0);" class="cur" code="">全部</a></dd> -->
<!--              <dd style="width:25%"><a href="javascript:void(0);" code="public_service" class="cur">公益众筹</a></dd> -->
             <dd style="width:25%"><a class="cur" href="javascript:void(0);" code="entity">产品众筹</a></dd>
             <dd style="width:25%"><a href="javascript:void(0);" code="stock">权益众筹</a></dd>
<!--               <dd style="width:25%"><a href="javascript:void(0);" code="house">房产众筹</a></dd>  -->
         </dl>
         <dl class="scr_sx clearfix" id="enterLoanSonTab1">
         	<!-- <dt style="font-weight: bold;">状态</dt> -->
             <dd><a style="margin-right:10px;" href="javascript:void(0);" name="add" class="cur">草稿</a></dd>
             <dd><a style="margin-right:10px;" href="javascript:void(0);" name="submit">已提交</a></dd>
             <dd><a style="margin-right:10px;" href="javascript:void(0);" name="funding">筹款中</a></dd>
             <dd><a style="margin-right:10px;" href="javascript:void(0);" name="lended">待发货</a></dd>
             <dd><a style="margin-right:10px;" href="javascript:void(0);" name="end">完成</a></dd>
         </dl>
         <dl class="scr_sx clearfix bgCol" id="enterLoanSonTab2" style="display:none;">
         	<!-- <dt style="font-weight: bold;">状态</dt> -->
           	<dd><a style="margin-right:10px;" href="javascript:void(0);" name="add" class="cur">草稿</a></dd>
			<dd><a style="margin-right:10px;" href="javascript:void(0);" name="re_passed">预热申请</a></dd>
           	<dd><a style="margin-right:10px;" href="javascript:void(0);" name="preheat">预热中</a></dd>
            <dd><a style="margin-right:10px;" href="javascript:void(0);" name="funding" >筹款中</a></dd>
            <dd><a style="margin-right:10px;" href="javascript:void(0);" name="lended">待分红</a></dd>
            <dd><a style="margin-right:10px;" href="javascript:void(0);" name="end">完成</a></dd>
           </dl>
           
           <dl class="scr_sx clearfix bgCol" id="enterLoanSonTab3" style="display:none;">
         	<!-- <dt style="font-weight: bold;">状态</dt> -->
         	<dd><a style="margin-right:10px;" href="javascript:void(0);" name="add" class="cur">草稿</a></dd>
             <dd><a style="margin-right:10px;" href="javascript:void(0);" name="funding" >筹款中</a></dd>
             <dd><a style="margin-right:10px;" href="javascript:void(0);" name="lended">待分红</a></dd>
             <dd><a style="margin-right:10px;" href="javascript:void(0);" name="end">完成</a></dd>
         </dl>
         
         <!-- <dl class="sx_list bb_border clearfix" id="enterTitle" style="font-weight:bold;">
         	<dt style="width:12%;">项目名称</dt>
             <dd style="width:12%;">目标金额</dd>
             <dd style="width:12%;">发布日期</dd>
             <dd style="width:12%;">截止日期</dd>
             <dd style="width:12%;">已投资金额</dd>
             <dd style="width:12%;">项目状态</dd>
             <dd style="width:12%;">操作</dd>
         </dl> -->
         <!-- <dl class="sx_list bb_border clearfix" style="display: none;font-weight:bold;" id="enter1Title">
         	<dt style="width:16%;">项目名称</dt>
         	<dt style="width:14%;">待付金额</dt>
             <dd style="width:12%;">目标金额</dd>
             <dd style="width:14%;">发布日期</dd>
             <dd style="width:14%;">项目状态</dd>
             <dd style="width:14%;">操作</dd>
         </dl> -->
         <!-- <dl class="sx_list bb_border clearfix" id="enter2Title" style="display: none;font-weight:bold;">
         	<dt style="width:12%;">项目名称</dt>
             <dd style="width:12%;">目标金额</dd>
             <dd style="width:12%;">发布日期</dd>
             <dd style="width:12%;">截止日期</dd>
             <dd style="width:12%;">尾款金额</dd>
             <dd style="width:7%;">项目状态</dd>
             <dd style="width:7%;">领投人数</dd>
             <dd style="width:12%;">操作</dd>
         </dl> -->
         <div id="enterListBody"><div></div></div>
         <div class="page" id="enterListPage"></div>
        </div>
       </div>
   </div>
	
</div>
<!-- 点击支付订金弹出框 -->
<div class="tk_box" id="zfdjDiv">
    <ul class="tk_margin">
    	<li class="mb20">* 请您仔细核对打款金额，本次款项为预热申请订金。</li>
        <li class="rmb clearfix"><span>金额：</span><span>(¥)<i id="zfdjAmt"></i>&nbsp;&nbsp;</span></li>
<!--         <li>为项目发起人投资金额的<i id="zfdjPercent"></i>%</li> -->
        <li class="tk_but_qr_qx clearfix">
        	<a href="javascript:void(0);" id="payZfdjBtn">确认支付</a>
            <a href="javascript:void(0);" id="canelPayZfdjBtn">取消</a>
        </li>
    </ul>
</div>
<form id="zfdjForm" action="<%=path%>/fundpool/yeepay/pay/directBindPay.html" method="post" target="_blank">
	<input type="hidden" name="supportAmt"/>
	<input type="hidden" name="loanNo"/>
	<input type="hidden" name="cardTop"/>
	<input type="hidden" name="cardLast"/>
	<input type="hidden" name="payNode" value="pre_preheat"/>
	<input type="hidden" name="payType" value="loanpay" />
	<input type="submit" id="zfdjFormBtn" style="display:none;" />
</form>
<!-- 点击支付尾款弹出框 -->
<div class="tk_box" id="zfwkDiv">
    <ul class="tk_margin">
    	<li class="mb20">* 恭喜您，已有 <i id="t_leaderNum"></i> 位领投人确认认购，在正式融资开始时请将剩余发起人投资金额的 <i id="zfwkPercent"></i>%打入项目托管账户。</li>
        <li class="rmb clearfix"><span>金额：</span><span>(¥)<i id="zfwkAmt"></i>&nbsp;&nbsp;</span></li>
        <li>为项目发起人投资金额的<i id="zfwkPercent1"></i>%</li>
        <li class="tk_but_qr_qx clearfix" style="width:300px;margin-left:105px;">
        	<a href="javascript:void(0);" id="payZfwkBtn" style="padding:1px 10px;">确认支付</a>
            <a href="javascript:void(0);" id="canelPayZfwkBtn" style="padding:1px 10px;">取消</a>
        </li>
    </ul>
</div>
<div id="bgpop" class="bgpop"></div>
<form id="zfwkForm" action="<%=path%>/fundpool/yeepay/pay/directBindPay.html" method="post" target="_blank">
	<input type="hidden" name="supportAmt"/>
	<input type="hidden" name="loanNo"/>
	<input type="hidden" name="cardTop"/>
	<input type="hidden" name="cardLast"/>
	<input type="hidden" name="payNode" value="preheat"/>
	<input type="hidden" name="payType" value="loanpay" />
	<input type="submit" id="zfwkFormBtn" style="display:none;" />
</form>
<ul class="foot_pos">
	<li><a href="<%=path %>/common/m-index.html"><p class="home"></p>首页</a></li>
	<li><a href="<%=path%>/common/m-projectList-stock.html?isProjectPay=1"><p class="pro"></p>项目融资</a></li>
	<li><a href="<%=path%>/common/m-projectList.html?type=entity"><p class="cp"></p>产品众筹</a></li>
	<%if(userId == null){ %>
    <li><a href="<%=path%>/common/m-login.html"><p class="my"></p>我的</a></li>
    <%}else{ %>
    <li><a class="col1" href="<%=path%>/common/m-myCenter.html"><p class="myl"></p>我的</a></li>
    <%} %>
</ul>
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
<script type="text/javascript" src="<%=path %>/js/m/myLoan.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/jPages.js"></script>
</body>
</html>