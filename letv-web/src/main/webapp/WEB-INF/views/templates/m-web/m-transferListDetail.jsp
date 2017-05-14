<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%
	String path = request.getContextPath();
	String userId = session.getAttribute("userId") == null ? null
			: session.getAttribute("userId").toString();
	String photoUrl = session.getAttribute("userphoto") == null ? null
			: session.getAttribute("userphoto").toString(); 
	String level = session.getAttribute("investorLevel")==null?"0":session.getAttribute("investorLevel").toString();
	String userLevel = session.getAttribute("userLevel")==null?null:session.getAttribute("userLevel").toString();
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
<link rel="stylesheet" href="<%=path %>/style/m-ruoshui.css" />
<script type="text/javascript" src="<%=path %>/js/common/jquery.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/common.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/m-validate.js"></script>
<script type="text/javascript" src="<%=path %>/js/common/TouchSlide.1.1.js"></script>
<title>挂牌</title>
<script type="text/javascript">
	var path = "<%=path%>";
	var userId = "<%=userId%>";
	var userPhoto = "<%=photoUrl%>";
	var investorLevel = "<%=level%>";
	var userLevel = "<%=userLevel%>";
</script>
</head>

<body>
<div class="head_top back1">
    <p>挂牌</p>
    <a href="javascript:void(0);" class="back"></a>
    <div id="indexHw" class="right"></div>
</div>

<div class="equity-info mt50">
	<div class="equity-img">
		<img id="loanPhoto" src="" style="width:100%; height:100%;" />
    	<div class="pop"></div>
        <a class="pop-f" id="loanName"></a>
    </div>
    <ul class="equity-jd">
    	<li>
    		已认购￥<span class="col1" id="approveAmt"></span>万
    		<span class="fr" id="supportRatio1"></span>
    	</li>
        <li class="jd">
        	<div class="jdBar"><span id="pBar" class="back1"></span></div>
            <p class="clearfix">
            	<span class="fl">挂牌金额：<em id="fundAmt1"></em>万</span>
                <span class="fr">剩余<em id="remainDay"></em>天</span>
            </p>
        </li>
    </ul>
</div>
<div class="equity-info" style="background:#fff; line-height:40px;  text-align:center;">
	<span style="margin-right:30px;">挂牌总份数：<em id="financeNum" style="color:#248af9"></em>份</span>
	<span style="margin-right:30px;">已完成份数：<em id="buyNum1" style="color:#248af9"></em>份</span>
	<span style="white-space: nowrap;">挂牌分红比例：<em id="qtyxhhr_zgbl" style="color:#248af9"></em></span>
</div>
<div class="equity-tab pb50">
	<ul id="detailTab" class="tab">
    	<li name="proInfo"><a href="javascript:void(0);">项目简介</a></li>
        <li name="proBudget"><a href="javascript:void(0);">项目预算</a></li>
        <li name="proProgress"><a href="javascript:void(0);">项目进展</a></li>
        <li name="proComment"><a href="javascript:void(0);">认购记录</a></li>
    </ul>
    <div class="detailbody" id="detailBody">
    	<div id="proInfoBody">
    		<div id="xs_video" style="text-align: center;"></div>
    		<div class="proinfo" id="proInfoContent"></div>
    	</div>
    	<div id="proBudgetBody">
    		<div class="proinfo" id="proBudgetContent"></div>
    	</div>
    	<div id="proProgressBody">
    		<div class="projectNav">
		        <ul class="clearfix" id="loanProgress"></ul>
		    </div>
    	</div>
    	<div id="proCommentBody">
    		<table cellspacing="0" cellpadding="0" style="width:100%;padding-bottom:0;">
	        	<tbody>
	        		<tr style="background:#ddd;">
                        <td style="width:33%;font-size:14px;text-align: center;height: 35px;">参与人</td>
                        <td style="width:33%;font-size:14px;text-align: center;height: 35px;">众筹金额(元)</td>
                        <td style="width:33%;font-size:14px;text-align: center;height: 35px;">众筹时间</td>
                    </tr>
	        	</tbody>
	        </table>
	        <table id="suportTable" cellspacing="0" cellpadding="0" style="width:100%;"></table>
	        <div class="list_more" id="backMoreList" style="display: none;">点击查看更多</div>
    		<!-- <ul class="talk-list"></ul>
		    <div class="gq-talk clearfix">
		        <input type="text" id="comVal" class="talk-box" placeholder="评论"/>
		        <a id="subComment" href="javascript:void(0);">发表</a>
		        <a class="exp"></a>
		        <a class="add"></a>
		    </div> -->
    	</div>
    </div>
    <div class="gt-but" id="buttonDiv" style="display:none;">
<!-- 		<div id="buttonDiv"> -->
			<a class="xs_bg2" href="javascript:void(0);" id="investLastBtn" style="display:none;margin-left:25%;">我要购买</a>
<!-- 		</div> -->
    </div>
</div>

<!-- 点击领投弹出框 -->
<div class="gqzc" id="investFirstDiv">
	<div class="gqzc_tit">我要领投</div>
    <div class="tzr_dd" style="">
        <ul class="qrdd" style="border:0;">
        	<li class="rengou">
            	<p>认购份数：</p>
                <a href="javascript:void(0);" class="jia" id="iJian1">-</a>
                <input type="text" class="duo" style="text-align:center;" id="fenshuNum1" value="1" readonly="readonly"/>
                <a href="javascript:void(0);" class="jia" id="iJIa1">+</a>
                <p>&nbsp;&nbsp;份</p>
                <p class="yuan">¥<span class="orl" id="miniInvestAmt2"></span>(单份投资额)</p>
            </li>
            <li style="height:30px; line-height:30px;">
            	<p>当前剩余份数：<span class="orl" id="remainsFenshu1"></span>份</p>
            </li>
            <li class="tk_textarea clearfix" style="margin-left:0; height:auto;"><span>申请说明：</span>
	        	<textarea id="applyExplain" nullMessage="请输入申请说明"></textarea>
	        </li>
            <li class="zf" style="margin-left:0; height:atuo; line-height:30px;">
            	<p class="clear clearfix">投资总额：<span class="orl"><i id="fundAmt5">0</i>元</span></p><br />
                <p class="clear clearfix" id="bonusesDiv2">分红比例：<span class="orl"><i id="couInvestZgbl1">0.00</i>%</span></p>
            </li>
        </ul>
        <a class="orderoksjs clear" href="javascript:void(0);" id="investFBtn" style="margin-top:10px;">确认订单</a>
    </div>
</div>

<!-- 点击我要预约弹出框 -->
<div class="tk_box" id="preSupportDiv">
	<div class="gqzc_tit">预约认购</div>
    <ul class="tk_margin">
    	<li class="mb20">*预约认购金额不能低于项目方要求的最低投资金额(￥<i id="preMinAmt"></i>)，最高可填两倍的目标融资金额：(￥<i id="preMaxAmt"></i>).</li>
        <li class="rmb clearfix"><span>预约认购金额：</span><input type="text" class="rmb" id="preSupportInput" nullMessage="预约金额应该为数字" logicMessage="预约金额不能低于最低投资金额" logicMessage1="预约金额不能高于目标投资金额2倍"/><span>&nbsp;&nbsp;(¥)</span></li>
        <li class="tk_but_qr_qx clearfix">
        	<a href="javascript:void(0);" id="preSupportBtn">确定</a>
            <a href="javascript:void(0);" id="canelPreSupportBtn">取消</a>
        </li>
    </ul>
</div>
<!-- 点击我要认购/我要投资弹出框 -->
<div class="gqzc" id="investLastDiv">
	<div class="gqzc_tit">投资订单
    	<a href="javascript:void(0);" class="close" title="关闭"></a>
    </div>
    <div class="tzr_dd" style="">
        <ul class="fl qrdd" style="border-left:0px;">
        	<li class="rengou">
            	<p>认购份数：</p>
                <a href="javascript:void(0);" class="jia" id="iJIa">+</a>
                <input type="text" class="duo" style="text-align:center;" id="fenshuNum" value="1" readonly="readonly"/>
                <a href="javascript:void(0);" class="jia" id="iJian">-</a>
                <p>&nbsp;&nbsp;份</p>
                <p class="yuan">¥<span class="orl" id="miniInvestAmt1"></span></p>
            </li>
            <li>
            	<p>当前剩余份数：<span class="orl" id="remainsFenshu"></span>份</p>
            </li>
            <li>
            	<p>验证码：</p>
                <input type="text" class="yzm" id="investValiInput"/>
                <p class="yzm_a">
                    <img alt="点击刷新" id="imageInvest" style="cursor:pointer;"
									src="<%=path%>/security/securityCodeImage.html"
									onclick="this.src='<%=path%>/security/securityCodeImage.html?'+new Date().getTime()" /><br />
                    <a href="javascript:void(0);" id="changeValiBtn">看不清,换一张</a>
                </p>
            </li>
            <li class="zf" style="height:auto;">
            	<div class="clearfix" style="line-height:30px;">投资总额：<span class="orl"><i id="fundAmt4">0.00</i>元</span></div>
                <div class="clearfix" id="fhbl" style="line-height:30px;">挂牌分红比例：<span class="orl"><i id="couInvestZgbl">0.00</i>%</span></div>
                <a class="qrzf" href="javascript:void(0);" id="investLBtn" style="margin:10px auto;">确认订单</a>
            </li>
            <li></li>
        </ul>
    </div>
</div>
<!-- <form id="subumitTransfer" method="post" target="_blank" action="<%=path %>/fundpool/transfer/submitBuyTransfer.html"> -->
<!-- 	<input type="hidden" name="transferNo" id="hideTransferNo"/> -->
<!-- 	<input type="hidden" name="buyParts" id="hideBuyParts"/> -->
<!-- 	<input id="submitTransferBtn" type="submit" style="display:none;" /> -->
<!-- </form> -->
<form id="supFomr" action="<%=path%>/fundpool/yeepay/pay/directBindPay.html" method="post">
   		<input type="hidden" name="supportAmt" id="subSupportAmt" />
   		<input type="hidden" name="loanNo" id="subLoanNo" />
   		<input type="hidden" name="backNo" id="subBackNo" />
		<input type="hidden" name="cardTop" id="cardTop"/>
		<input type="hidden" name="cardLast" id="cardLast"/>
		<input type="hidden" name="payType" id="payType" value="invest" />   		
   		<input type="hidden" name="invstType" id="invstType" value="commonInvest"/>
<!--    		<input type="hidden" name="postAddressNo" id="subPostAddressNo" /> -->
   		<input type="submit" id="supFormBtn" style="display:none;"/>
   </form>
<input id="yLoanuser" type="hidden"/>
<span id="miniInvestAmt" style="display:none;"></span>
<div id="bgpop" class="bgpop"></div>
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
<script type="text/javascript" src="<%=path %>/js/m/transferListDetail.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/validate.js"></script>
</body>
</html>