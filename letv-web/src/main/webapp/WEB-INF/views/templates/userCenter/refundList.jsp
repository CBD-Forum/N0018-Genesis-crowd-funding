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
    <div class="box" style="margin-top:27px;margin-bottom:20px;overflow: visible;">
        <div class="fl xt_news">
	   <div class="cont_xq">
	    <a href="<%=path %>/common/modifyData.html"><img id="centerUserPhoto" class="rightImg"></a>
		<p class="mt15 ft16" id="userRealName"></p>
		<p class="rzh_cz"><a class="rzh1 cur" href="<%=path %>/common/accountSecurity.html"></a><a class="rzh2" id="realNameRZ"></a><a class="rzh3"></a></p>
	   </div>
	   <ul class="lf_nav">
		   <li class="bor_top"><a href="<%=path %>/common/myCenter.html">账户总览</a></li>
		   <li class="rel hg225 cur"><a id="rightProjectList">项目管理</a>
		    <div class="abs_div">
			 <p class="bor_top"><a class="col_blue" href="<%=path %>/common/administrationProduct.html">产品众筹</a></p>
			 <p><a href="<%=path %>/common/administrationStock.html">非公开融资</a></p>
			 <p><a href="<%=path %>/common/administrationPublic.html">公益众筹</a></p>
			</div>
		   </li>
		   <li><a href="<%=path %>/common/myTrade.html">交易记录</a></li>
		   <li><a href="<%=path %>/common/modifyData.html">个人信息</a></li>
		   <li><a href="<%=path %>/common/accountSecurity.html">安全中心</a></li>
		   <li><a href="<%=path %>/common/messages.html">消息中心</a></li>
		   <li><a href="<%=path %>/common/sealList.html">合同管理</a></li>
		   <li><a href="<%=path %>/common/invitefriend.html">邀请管理</a></li>
	   </ul>
	</div>
	<div class="fr rg_side clearfix" style="overflow:visible;"> 
	   <h4 class="tit_xt clearfix mb20">退款列表</h4>
	   <ul class="pro-nav clearfix" id="centerLoanTab" style="margin-bottom:20px;">
	    <li name="waitConfirm"><a class="cur">待处理</a></li>
		<li name="auditing"><a>退款中</a></li>
		<li name="success"><a>退款成功</a></li>
		<li name="fail"><a>退款失败</a></li>
	   </ul>
	   <div id="centerLoanTabBody">
	   	<div id="waitConfirmBody">
	   		<div>
	   		</div>
	   	</div>
	   	<div id="auditingBody" style="display:none;">
	   		<div>
	   		</div>
	   	</div>
	   	<div id="successBody" style="display:none;">
	   		<div>
	   		</div>
	   	</div>
	   	<div id="failBody" style="display:none;">
	   		<div>
	   		</div>
	   	</div>
	   </div>
	   <div class="letvPage" id="waitConfirmPage"></div>
	   <div class="letvPage" id="auditingPage" style="display:none;"></div>
	   <div class="letvPage" id="successPage" style="display:none;"></div>
	   <div class="letvPage" id="failPage" style="display:none;"></div>
       
	  </div>
    </div>
</div>
<div id="bgpop" class="bgpop" name="bigp"></div>
<div class="sbgpop"></div>
<div class="bgpop_wait" id="bgpop_wait"></div>
 <!--  整体弹框 -->
<div id="agree_box2" class="agree_box2" style="z-index:106;">
	<div class="agree_close2"></div>
	<div id="leaveMessage" class="agree_con2"></div>
</div>
<div class="prompt_box dividendDetail" id="successRefund" style="width:520px;height:370px;"><!-- top:68px; left: 324.5px; display: block; -->
	<a class="prompt_close">关闭</a>
	<h4>同意退款</h4>
	<div class="overDiv">
		<ul class="refundListUl">
			<li>
				<p class="p1">项目编号</p>
				<p id="TloanNo"></p>
			</li>
			<li>
				<p class="p1">项目名称</p>
				<p id="TloanName"></p>
			</li>
			<li>
				<p class="p1">退款人</p>
				<p id="TsupportUserName"></p>
			</li>
			<li>
				<p class="p1">退款金额</p>
				<p id="TsupportAmt">0.00<b class="companyCode"></b></p>
			</li>
			<li>
				<p class="p1 fl border0">当前可用余额</p>
				<p id="balance">0.00<b class="companyCode"></b></p>
				<a class="bonusCZBtu" href="/letv-web/common/recharge.html" id="bonusCZBtu">充入</a>
			</li>
		</ul>
		<div class="refundListDiv">
			<a class="fl" id="operatBtu">确定</a>
			<a class="fr" id="qxBtu">取消</a>
		</div>
	</div>
</div>
<div class="prompt_box dividendDetail" id="failRefund" style="width:520px;height:370px;"><!-- top:68px; left: 324.5px; display: block; -->
	<a class="prompt_close">关闭</a>
	<h4>拒绝退款</h4>
	<div class="overDiv">
		<p style="margin:35px 0 20px 40px;">拒绝原因：</p>
		<div class="failtTextarea">
			<textarea placeholder="请输入退款拒绝原因" id="refuseReason"></textarea>
			<span>字数限100</span>
		</div>
		<div class="refundListDiv" style="padding-top:10px">
			<a class="fl" id="FoperatBtu">确定</a>
			<a class="fr" id="FqxBtu">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript" src="<%=path%>/js/common/jPages.js"></script>
<script type="text/javascript" src="<%=path%>/js/userCenter/modifyData.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>

<script type="text/javascript" src="<%=path%>/js/userCenter/refundList.js"></script>