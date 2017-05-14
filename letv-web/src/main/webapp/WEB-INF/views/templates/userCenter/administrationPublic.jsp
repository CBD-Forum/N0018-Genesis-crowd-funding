<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>

<script type="text/javascript" src="<%=path%>/js/userCenter/administrationPublic.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/ueditor/third-party/webuploader/webuploader.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/demo.css"/>
<style>
.header2{ border-bottom: 3px solid #1c2024;}
.bai_box{ background-color: #f4f3f3;overflow:hidden;padding-bottom:30px;}
</style>
<div class="bai_box">
<div class="box pt30">
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
			 <p class="bor_top"><a href="<%=path %>/common/administrationProduct.html">产品众筹</a></p>
			 <p><a href="<%=path %>/common/administrationStock.html">非公开融资</a></p>
			 <p><a class="col_blue" href="<%=path %>/common/administrationPublic.html">公益众筹</a></p>
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
	<div class="fr rg_side clearfix">
	   <h4 class="tit_xt clearfix">项目管理</h4>
	   <ul class="pro-nav clearfix" id="centerLoanTab">
	    <li name="support"><a class="cur">我支持的</a></li>
		<li name="apply"><a>我申请的</a></li>
		<li name="collection"><a>我收藏的</a></li>
	   </ul>
	   <div id="centerLoanType">
		   <div class="sq_nav clearfix" id="supportType">
			    <a class="fl cur" code="">全部</a>
				<div class="fl">
				 <a code="noPay">待支付</a>
				 <a code="payed">已支付</a>
				 <a code="waitSend">待发货</a>
				 <a code="sended">已发货</a>
				</div>
			</div>
			<div class="sq_nav clearfix" id="applyType" style="display:none;">
			    <a class="fl cur">全部</a>
				<div class="fl">
				 <a code="add">草稿</a>
				 <a code="submit">已提交</a>
				 <!-- <a code="preheat">预热中</a> -->
				 <a code="funding">募集中</a>
				 <a code="funded">募集结束</a>
				 <a code="lended">待发货</a>
				 <a code="end">已发货</a>
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
	   	<div id="collectionBody" style="display:none;">
	   		<div>
	   		</div>
	   	</div>
	   	
	   </div>
	   <div class="letvPage" id="supportPage"></div>
	   <div class="letvPage" id="applyPage" style="display:none;"></div>
	   <div class="letvPage" id="collectionPage" style="display:none;"></div>
	</div>
</div>
</div>
<div class="prompt_box dividendDetail" id="projectDebriefing">
	<a class="prompt_close">关闭</a>
	<h4>录入项目进展</h4>
	<div class="overDiv">
		<ul class="bonus_ul progressUl" id="progressUl">
			
		</ul>
		<a class="operatBtu" id="operatBtu">确定</a>
	</div>
</div>
<div style="display:none;" id="progressHtml">
<li>
				<p class="p1 fl border0">录入时间</p>
				<input id="timeNode" class="datetime" type="text" onfocus="WdatePicker({minDate:'%y-%M-{%d+1}'})" nullMessage="请选择录入时间"/>
			</li>
			<li>
				<p class="p1 fl border0">内容说明</p>
				<textarea rows="3" id="enterContent" nullMessage="请输入项目进展相关文字描述" style="width: 455px;height:90px;" placeholder="内容说明，文本框需要添加提示信息：请输入项目进展相关文字描述" ></textarea>
			</li>
			<li>
				<p class="p1 fl border0">上传图片</p>
				<div class="tjtp" style="width:490px;">
					<a class="add_img" id="changeUpload" nullMessage="请上传图片">添加图片</a>
		   			<input id="fileToUpload" type="file" style="display:none" name="file">
		  			<div id="uploadInFo"></div>
	  			</div>
			</li>
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
<input type="hidden" id="orgLoanReceiveProve">
<input type="hidden" id="blockChainAddress">
<div class="sbgpop"></div>
<div class="bgpop"></div>
<script type="text/javascript" src="<%=path%>/js/common/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/jPages.js"></script>
<script type="text/javascript" src="<%=path%>/js/userCenter/modifyData.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>