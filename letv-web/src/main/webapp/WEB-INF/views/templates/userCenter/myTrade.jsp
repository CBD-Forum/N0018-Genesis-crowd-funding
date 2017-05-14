<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>
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
		   <li class="rel hg225"><a id="rightProjectList">项目管理</a>
		    <div class="abs_div">
			 <p class="bor_top"><a href="<%=path %>/common/administrationProduct.html">产品众筹</a></p>
			 <p><a href="<%=path %>/common/administrationStock.html">非公开融资</a></p>
			 <p><a href="<%=path %>/common/administrationPublic.html">公益众筹</a></p>
			</div>
		   </li>
		   <li class="cur"><a href="javascript:void(0)">交易记录</a></li>
		   <li><a href="<%=path %>/common/modifyData.html">个人信息</a></li>
		   <li><a href="<%=path %>/common/accountSecurity.html">安全中心</a></li>
		   <li><a href="<%=path %>/common/messages.html">消息中心</a></li>
		   <li><a href="<%=path %>/common/sealList.html">合同管理</a></li>
		   <li><a href="<%=path %>/common/invitefriend.html">邀请管理</a></li>
	   </ul>
	</div>
	<div class="fr rg_side">
    	<ul class="screen clearfix" id="tradeTab">
            <li><a href="javascript:void(0);" name="success" vtype="trade_type_person">交易成功</a></li>
            <li><a href="javascript:void(0);" name="process" vtype="trade_type_person">交易中</a></li>
            <li><a href="javascript:void(0);" name="fail" vtype="trade_type_person">交易失败</a></li>
        </ul>
        <div id="tradeLoanTabBody">
			<div id="tradeSearchDiv" class="trade_serach_div">
				<select id="success_tradeType" value="" style="display:none;"></select>
				<select id="process_tradeType" value="" style="display:none;"></select>
				<select id="fail_tradeType" value="" style="display:none;"></select>
				<input id="dateStart" class="datetime" type="text" style="margin-left:20px;margin-right:10px;" onfocus="WdatePicker({isShowOK:false,isShowClear:false,isShowToday:false,readOnly:true})">-
				<input id="dateEnd" class="datetime" type="text" style="margin-left:10px;" onfocus="WdatePicker({isShowOK:false,isShowClear:false,isShowToday:false,readOnly:true})">
				<span id="searchBtn" class="button tradeBut">查询</span>
			</div>
			<div class="address_head">
				<p style="width:25%">交易类型</p>
				<p style="width:25%">交易众筹<b class="companyCode"></b></p>
				<p style="width:25%">时间</p>
				<p style="width:25%">操作</p>
			</div>
			<div id="tradeBody">
				<div id="successBody" style="display:none;">
					<div id="successListBody">
						<div></div>
						<ul class="address_cont"></ul>
					</div>
					<div class="letvPage tr" id="successListPage"></div>
				</div>
				<div id="processBody" style="display:none;">
					<div id="processListBody">
						<div></div>
						<ul class="address_cont"></ul>
					</div>
					<div class="letvPage tr" id="processListPage"></div>
				</div>
				<div id="failBody" style="display:none;">
					<div id="failListBody">
						<div></div>
						<ul class="address_cont"></ul>
					</div>
					<div class="letvPage tr" id="failListPage"></div>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
<div class="sbgpop"></div>
<!-- 查看详情 -->
<div class="prompt_box dividendDetail tradePop" id="bonusPop">
	<a class="prompt_close" id="qxBtu">关闭</a>
	<h4>详情</h4>
	<ul class="tradeDiv">
		<li>
			<p class="w50">交易类型：<span id="tradeTypeName"></span></p>
			<p class="w50">交易金额：<span id="tradeAmt">0</span><span><b class="companyCode"></b></span></p>
		</li>
		<li>
			<p>交易时间：<span id="tradeTime"></span></p>
		</li>
		<li>
			<p>备注：<span id="tradeDetail"></span></p>
		</li>
	</ul>
</div>
<div class="bgpop" id="bgpop"></div>
<script type="text/javascript" src="<%=path%>/js/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/jPages.js"></script>
<script type="text/javascript" src="<%=path%>/js/userCenter/myTrade.js"></script>
<script type="text/javascript" src="<%=path%>/js/userCenter/modifyData.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>