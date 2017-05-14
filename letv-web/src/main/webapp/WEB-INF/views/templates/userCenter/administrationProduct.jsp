<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>

<script type="text/javascript" src="<%=path%>/js/userCenter/administrationProduct.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/ueditor/third-party/webuploader/webuploader.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/demo.css"/>
<style>
.header2{ border-bottom: 3px solid #1c2024;}
.bai_box{ background-color: #f4f3f3;overflow:hidden;padding-bottom:30px;}
</style>
<div class="bai_box">
<div class="box pt30" style="overflow: initial">
	<div class="fl xt_news">
	   <div class="cont_xq">
	    <%-- <a href="<%=path %>/common/modifyData.html"><img id="centerUserPhoto" class="rightImg"></a> --%>
	    <div class="tx" id="image_file">
    		<input hidefocus="true" style="font-size:50px;display:none;" name="file" type="file" />
        	<a href="javascript:void(0);" class="photo"><img id="imgheadPhoto" src="" width="113px" height="113px" style="border-radius:50%;"/></a>
            <div class="cover" nullMessage="请上传项目封面" style="height:3px"></div>
            <input type="hidden" id="loan_logo_url"/>
        </div>
		<p class="mt15 ft16" id="userRealName"></p>
		<p class="rzh_cz"><a class="rzh1 cur" href="<%=path %>/common/accountSecurity.html"></a><a class="rzh2" id="realNameRZ"></a><a class="rzh3" id="bankCardRZ"></a></p>
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
	<div class="fr rg_side clearfix" style="overflow: initial">
	   <h4 class="tit_xt clearfix">项目管理</h4>
	   <ul class="pro-nav clearfix" id="centerLoanTab">
	    <li name="support"><a class="cur">我支持的</a></li>
		<li name="apply"><a>我申请的</a></li>
		<li name="possession"><a>我转让的</a></li>
		<li name="collection"><a>我收藏的</a></li>
	   </ul>
	   <div id="centerLoanType">
		   <div class="sq_nav clearfix" id="supportType">
			    <a class="fl cur" code="banTransfer">全部</a>
				<div class="fl">
				 <a code="noPay">待支付</a>
				 <a code="payed">已支付</a>
				 <a code="waitSend">待发货</a>
				 <a code="sended">已发货</a>
				 <!-- <a code="canRefund">可退款</a>
				 <a code="refunding">退款中</a><br/>
				 <a code="refundSuccess">退款成功</a>
				 <a code="refundFail">退款失败</a> -->
				 <a code="canTransfer">可转让 </a>
				</div>
			</div>
			<div class="sq_nav clearfix" id="applyType" style="display:none;">
			    <a class="fl cur">全部</a>
				<div class="fl">
				 <a code="add">草稿</a>
				 <a code="submit">已提交</a>
				 <a code="preheat">预热中</a>
				 <a code="funding">募集中</a>
				 <a code="funded">募集结束</a>
				 <a code="lended">待发货</a><br/>
				 <a code="end">已发货</a>
<!-- 				 <a code="canRefund">待退款 </a> -->
<!-- 				 <a code="refunding">退款中</a> -->
<!-- 				 <a code="passed">退款成功</a> -->
<!-- 				 <a code="refuse">退款失败</a> -->
				</div>
		   </div>
		   <div class="sq_nav clearfix" id="possessionType" style="display:none;">
		    <a class="fl cur">全部</a>
			<div class="fl">
<!-- 			 <a code="transfering">转让中</a>
			 <a code="transfered">转让成功</a>
			 <a code="transferend">转让失败</a> -->
			 <a code="wait">转让中</a>
			 <a code="success">转让成功</a>
			 <a code="fail">转让失败</a>
			</div>
		   </div>
	   </div>
	   <div id="centerLoanTabBody">
	   	<div id="supportBody">
	   		<div>
	   		</div>
	   	</div>
	   	<div id="applyBody" style="display:none;">
	   		<div>
	   		</div>
	   	</div>
	   	<div id="possessionBody" style="display:none;">
	   		<div>
	   		</div>
	   	</div>
	   	<div id="collectionBody" style="display:none;">
	   		<div>
	   		</div>
	   	</div>
	   </div>
	   <div class="letvPage" id="supportPage"></div>
	   <div class="letvPage" id="applyPage" style="display:none;"></div>
	   <div class="letvPage" id="possessionPage" style="display:none;"></div>
	   <div class="letvPage" id="collectionPage" style="display:none;"></div>
	</div>
</div>
</div>

<!-- 申请退款出框 -->
<div class="prompt_box prompt_phone" id="talkDiv" style="top:70px;left:50%;margin-left:-350px;width:700px;">
	<a class="prompt_close">关闭</a>
	<h4>申请退款</h4>
	<div class="zhr_info">
		<div class="clearfix"> <span class="fl">项目编号</span>
          <p class="fl" id="approveloanno"></p>
        </div>
        <div class="clearfix"> <span class="fl">项目名称</span>
          <p class="fl" id="approveloanname"></p>
        </div>
		<div class="clearfix"> <span class="fl">退款人</span>
          <p class="fl" id="approveloanusername"></p>
        </div>
        <div class="clearfix"> <span class="fl">支持金额</span>
          <p class="fl" id="approveAmtA"></p>
        </div>
        <div class="clearfix"> <span class="fl">运费</span>
          <p class="fl" id="transFee"></p>
        </div>
        <div class="clearfix"> <span class="fl">退款金额</span>
          <p class="fl" id="approveAmtB"></p>
        </div>
        
        <div class="clearfix"> <span class="fl">退款说明 </span>
          <textarea style="width:350px;max-width:350px;" class="fl tk_area" id="applicationContent" nullmessage="退款说明不能为空" onchange="this.value=this.value.substring(0, 100)" onkeydown="this.value=this.value.substring(0, 100)" onkeyup="this.value=this.value.substring(0, 100)"  ></textarea>
          <em class="fl">文字限100字</em> </div>
        <div class="clearfix rel">
        <span class="fl hg35">手机验证码</span>
		<input type="text"  class="fl" placeholder="" id="verifycode"  nullmessage="手机验证码不能为空"/>
          <a class="get-num get-num2 fl" style="width:auto;padding:0 5px;" id="sendViliimgBtn" >获取验证码</a> 
          </div>
        <a class="submit_btn" style="width:256px;margin:40px auto" id="applicationContentBtn">提交</a> </div>
</div>

<!-- 查看退款明细 -->
<div class="fr rg_side clearfix" id="seeRefundDiv" style="display:none; position: fixed;top: 90px;left: 50%;margin-left: -460px;    z-index: 999;"> 
   <a class="prompt_close" style="color:#1ea5ff">关闭</a>
   <h4 class="tit_xt clearfix mb20" id="refundName"></h4>
   <div class="proj_list">
    <p class="clearfix mb20">
	 <span><i>投资金额：</i><em id="refundAmt1"></em><b class="companyCode"></b></span>
	 <span><i>退款金额：</i><em id="refundAmt2"></em><b class="companyCode"></b></span>
	 <span class="wid3"><i>退款申请提交时间：</i><em id="refundTime1"></em></span>
	</p>
	<p class="clearfix mb20">
	 <span><i>投资时间：</i><em id="refundTime2"></em></span>
	 <span class="wid3"><i>项目方同意退款时间：</i><em id="refundTime3"></em></span>
	 <span><i>退款成功时间：</i><em id="refundTime4"></em></span>
	</p>
	<p class="clearfix">
	<i>退款说明：</i><em id="refundExplain"></em>
	</p>
   </div>
</div>
<!-- 申请转让 -->
<div class="alert_div alert_div3" id="applyApplicationDiv" style="display:none; position: fixed; z-index: 999;">
  <h3 class="clearfix">
   <span>申请转让</span>
   <a class="fr" id="applyApplicationDiv-none">关闭</a>
  </h3>
  <div class="zhr_info">
   <div class="clearfix">
    <span class="fl palf">项目编号</span>
	<p class="fl" id="applicationLoan"></p>
   </div>
   <div class="clearfix">
    <span class="fl palf">项目名称</span>
	<p class="fl" id="applicationEntryName"></p>
   </div>
   <div class="clearfix">
    <span class="fl palf">回报详情</span>
	<p class="fl overflow"  id="applicationDetails" ></p>
   </div>
   <div class="clearfix">
    <span class="fl palf">出让人</span>
	<p class="fl" id="applicationName"></p>
   </div>
   <div class="clearfix">
    <span class="fl palf">支持金额</span>
	<p class="fl" id="applicationSupportAmt"></p>
   </div>
   <div class="clearfix">
    <span class="fl hg35 palf">转让金额</span>
    <div class="fl" style="position: relative;margin-top:0;">
    	<input type="text" placeholder="请输入转让金额" id="Transfer_AMT" nullmessage="转让金额不能为空" style="width:160px">
		<i style="left:130px;"><b class="companyCode"></b></i>
    </div>
	<p class="fl sxxx"><em id="Transfer_min"></em> ≤ 转让金额 ≤ <em id="Transfer_max"></em></p>
   </div>
   <div>
	<p class="zhr_info_ts">转让金额输入范围：支持金额*转让最低下限（<em id="Transfer_min_AMT">0%</em>）≦ 转让金额 ≦ 支持金额*转让最高上限（<em id="Transfer_max_AMT">0%</em>）</p>
   </div>
   <div class="clearfix">
    <span class="fl palf">转让手续费</span>
	<p class="fl" id="Transfer_fee1">0.00<b class="companyCode"></b></p>
   </div>
   <div>
	<p class="zhr_info_ts">转让成功后，平台会扣除转让金额（不包含运费）的<em id="Transfer_fee">0%</em>作为转让手续费</p>
   </div>
<!--    <div class="clearfix"> -->
<!--     <span class="fl palf">投资金额</span> -->
<!-- 	<p class="fl"  id="applicationAmt1" ></p> -->
<!--    </div> -->
   <div class="clearfix">
    <span class="fl palf">运费</span>
	<p class="fl" id="transFee1">0.00<b class="companyCode"></b></p>
   </div>
   <div class="clearfix">
    <span class="fl palf">转让总金额</span>
	<p class="fl" id="applicationAmt2">0.00<b class="companyCode"></b></p>
   </div>
   
   <div class="clearfix">
    <span class="fl palf">转让时长</span>
	<p class="fl" id="transferDay"></p>
   </div>
   <div class="clearfix rel" id="VerifycodeDiv">
    
   </div>
   <a class="submit_btn" style=" margin: 20px auto;" id="applicationBtn">提交</a>
  </div>
 </div>
 
 
<div style="display:none;" id="VerifycodeHtml">
	<span class="fl hg35 palf">验证码</span>
	<input type="text" placeholder="" id="applicationVerifycode" style="width:160px;float: left;" nullmessage="手机验证码不能为空" >
	<a class="get-num get-num2" style="width:auto;padding:0 5px;" id="applicationSendViliimg" >获取验证码</a>
</div>
 
 
<!-- 录入项目进展 -->
<div class="prompt_box dividendDetail" id="projectDebriefing" style="">
	<a class="prompt_close" id="projectDebriefing-none">关闭</a>
	<h4>录入项目进展</h4>
	<div class="overDiv">
		<ul class="bonus_ul progressUl" id="progressUl">
			
		</ul>
		<a class="operatBtu" id="operatBtu">确定</a>
	</div>
</div>
<div class="sbgpop"></div>
<!-- 查看退款失败原因 -->
<div class="prompt_box dividendDetail" id="refundFailed" style="width:505px;height:auto;">
	<a class="prompt_close">关闭</a>
	<h4>退款失败</h4>
	<div class="refundDiv">
		<p><span>退款拒绝时间：</span><i id="auditTime"></i></p>
		<p><span>说明：</span><i id="auditOpinion"></i></p>
	</div>
</div>
<!-- 查看退款成功明细 -->
<div class="prompt_box dividendDetail" id="refundSuccess" style="width:520px;height:auto;">
	<a class="prompt_close">关闭</a>
	<h4>退款成功明细</h4>
	<ul class="infoSuccessUl">
		<li>
			<p class="p1 p2" style="width: 135px;">退款成功时间:</p>
			<p class="p2" id="refuseSuccessTime"></p>
		</li>
		<li>
			<p class="p1" style="width: 135px;">投资金额:</p>
			<p id="refuseSupportAmt"></p>
		</li>
		<li>
			<p class="p1" style="width: 135px;">投资时间:</p>
			<p id="refuseSupportTime"></p>
		</li>
		<li>
			<p class="p1" style="width: 135px;">退款金额:</p>
			<p id="refuseAmt"></p>
		</li>
		<li>
			<p class="p1" style="width: 135px;">退款申请提交时间:</p>
			<p id="refuseApplyTime"></p>
		</li>
		<li>
			<p class="p1" style="width: 135px;">项目方同意退款时间:</p>
			<p id="refuseLoanAuditTime"></p>
		</li>
		<li style="border:0;">
			<p class="p1" style="width: 135px;">退款说明:</p>
			<p id="refuseationContent"></p>
		</li>
	</ul>
</div>
<!-- 查看发货明细 -->
<div class="prompt_box dividendDetail" id="deliveryInfo" style="width:520px;height:auto;">
	<a class="prompt_close">关闭</a>
	<h4>查看发货明细</h4>
	<ul class="infoSuccessUl">
		<li>
			<p class="p1 p2">发货时间</p>
			<p class="p2" id="FcompleteTime"></p>
		</li>
		<li>
			<p class="p1 p5">投资金额</p>
			<p class="p6" id="FsupportAmt"></p>
			<p class="p1 p4">投资时间</p>
			<p class="p3" id="FsupportTime"></p>
		</li>
		<li>
			<p class="p1">回报详情</p>
			<p id="FbackContent"></p>
		</li>
		<li>
			<p class="p1 p5">收货人</p>
			<p class="p6" id="FpostUser"></p>
			<p class="p1 p4">联系方式</p>
			<p class="p3" id="FpostMobile"></p>
		</li>
		<li>
			<p class="p1">收货地址</p>
			<p id="FaddressPost"></p>
		</li>
		<li>
			<p class="p1">备注</p>
			<p id="Fremark"></p>
		</li>
		<li style="border:0;">
			<p class="p1 p5">物流公司</p>
			<p class="p6" id="FsendDelivery"></p>
			<p class="p1 p4">物流单号</p>
			<p class="p3" id="FsendOrderId"></p>
		</li>
	</ul>
</div>
<div style="display:none;" id="progressHtml">
<li>
				<p class="p1 fl border0"><em>*</em>录入时间</p>
				<input id="timeNode" class="calend_bg" style="width:180px;" type="text" onfocus="WdatePicker({minDate:'%y-%M-{%d+1}'})" nullMessage="请选择录入时间"/>
			</li>
			<li>
				<p class="p1 fl border0"><em>*</em>内容说明</p>
				<textarea rows="3" id="enterContent" nullMessage="请输入项目进展相关文字描述" style="width: 455px;height:90px;" placeholder="请输入项目进展相关文字描述" ></textarea>
			</li>
			<li>
				<p class="p1 fl border0">上传图片</p>
				<div class="tjtp"style="width:490px;">
					<a class="addImgX" id="changeUpload" nullMessage="请上传图片"></a>
					<span class="addSpan">图片尺寸：200*400px，图片格式：png、jpg，图片大小：5M以内</span>
		   			<input id="fileToUpload" type="file" style="display:none" name="file">
		  			<div id="uploadInFo" class="clearfix" style="widht:400px;"></div>
	  			</div>
			</li>
</div>

<!-- 查看转让成功明细 -->
<div class="prompt_box dividendDetail" id="possessionDetailed" style="width:520px;height:auto;">
	<a class="prompt_close">关闭</a>
	<h4>查看转让成功明细</h4>
	<ul class="infoSuccessUl">
		<li>
			<p class="p1">转让成功时间</p>
			<p class="p2" id="transferEndTime"></p>
		</li>
		<li>
			<p class="p1">项目编号</p>
			<p class="p2" id="possessionLoanNo"></p>
		</li>
		<li>
			<p class="p1">支持金额</p>
			<p id="investAmt"></p>
		</li>
		<li>
			<p class="p1">转让金额</p>
			<p id="transferAmt"></p>
		</li>
		<li>
			<p class="p1">运费</p>
			<p id="possessionTransFee"></p>
		</li>
		<li>
			<p class="p1">转让总金额</p>
			<p id="transferTotalAmt"></p>
		</li>
		<li>
			<p class="p1">转让手续费</p>
			<p id="transferAmt_sxf">0.00<b class="companyCode"></b></p>
		</li>
		<!-- <li>
			<p class="p1">转让人</p>
			<p class="p6" id="loanUserName"></p>
		</li> -->
		<li>
			<p class="p1">实际到账金额</p>
			<p id="actualArrivalAmount"></p>
		</li>
		<li>
			<p class="p1">转让发起时间</p>
			<p id="transferTime"></p>
		</li>
		<li>
			<p class="p1">承接人</p>
			<p id="buyUserRealName"></p>
		</li>
	</ul>
</div>

<!-- 股权详情单独遮盖曾 -->
<input type="hidden" id="blockChainAddress">
<input type="hidden" id="orgLoanReceiveProve">
<div id="stockBgpop" class="sbgpop" style="z-index:100;"></div>
<div id="bgpop" class="bgpop" style="z-index:100;"></div>
<script type="text/javascript" src="<%=path%>/js/common/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/jPages.js"></script>
<script type="text/javascript" src="<%=path%>/js/userCenter/modifyData.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>