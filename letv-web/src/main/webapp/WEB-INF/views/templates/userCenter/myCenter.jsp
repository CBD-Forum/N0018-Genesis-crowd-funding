<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>

<script type="text/javascript" src="<%=path%>/js/userCenter/myCenter.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/ueditor/third-party/webuploader/webuploader.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/demo.css"/>
<style>
.header2{ border-bottom: 3px solid #1c2024;}
.bai_box{ background-color: #f4f3f3;overflow:hidden;padding-bottom:30px;}
</style>
<div class="bai_box">
<div class="box pt30" style="    overflow: initial;">
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
		   <li class="bor_top cur"><a href="javascript:void(0)">账户总览</a></li>
		   <li class="rel hg225"><a id="rightProjectList">项目管理</a>
		    <div class="abs_div">
			 <p class="bor_top"><a href="<%=path %>/common/administrationProduct.html">产品众筹</a></p>
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
	<div class="fr rg_side clearfix" style="    overflow: inherit;">
	   <div class="bg_pink" id="notAccountRecord" style="display:none;">您还有一笔未到帐，<a class="ck_btn" href="<%=path %>/common/myTrade.html">点击查看</a><a><img src="<%=path %>/images/letv/close.png" class="close" id="billClose"></a></div>
	   <h4 class="tit_xt clearfix">我的账户<a class="ck_btn fr normal_fw" href="<%=path %>/common/centerRZguide.html">投资人认证</a></h4>
	   <ul class="my_count clearfix">
	    <li class="myCenter-Address" style="padding-top: 0;">区块链查询地址：<a href="https://charts.ripple.com/#/graph/"  target="_blank" id="blockChainAddress"></a><span id="blockChainAddress-Btn"><img src="<%=path %>/images/letv/flashCopy_btn.png"></span></li>
	    <li class="bor_right" style="width:380px;">
		 <p>总资产</p>
		 <p class="ft16"><em id="totalAssets">0.00</em> <b class="companyCode"></b>
		 <i class="wenhao" style="left: 0;position: relative;" id="counterFee-hover">
	 		<span class="yxj_myCenter" id="counterFee-popup" style="width:225px;display: none;left: -30px; text-align: left; line-height: 28px;z-index: 9999;">
			 	<b class="companyCode"></b>是您在众筹平台上进行一切交易活动所使用的虚拟数字资产。
			 </span>
	 	</i></p>
		 <p class="zc_det col8a">
		  <span style="width:50%">可用资产</span>
		 <!--  <span>预计待收资产</span> -->
		  <span style="width:120px">冻结资产</span>
		 </p>
		 <p class="zc_det ft16">
		  <span class="ft14" style="width:50%"><i id="availableAssets">0.00</i> <b class="companyCode"></b></span>
		  <!-- <span class="ft14"><i id="collectAssets">0.00</i> <b class="companyCode"></b></span> -->
		  <span style="width:180px" class="ft14"><i id="frozenAssets">0.00</i> <b class="companyCode"></b></span>
		 </p>
		</li>
		<li class="pdl_72">
		 <p>账户余额</p>
		 <p class="ft16"><em id="balance">0.00</em> <b class="companyCode"></b></p>
		 <p class="tx_p">
			 <a class="chz_btn mr40 cur" id="cz_btn" >充 入</a>
			 <a class="chz_btn" id="tx_btn" >提 出</a>
		 </p>
		</li>
	   </ul>
	   <ul class="my_count clearfix">
	    <li style="width:33.33%">
	   	<p class="col8b mb10">可转让资产</p>
		  <span class="ft14"><i id="canTransferAssets">0.00</i> <b class="companyCode"></b></span>
	   </li>
	   <li style="width:33.33%">
	   	<p class="col8b mb10">转让中资产</p>
		  <span class="ft14"><i id="transferingAssets">0.00</i> <b class="companyCode"></b></span>
	   </li>
	  
	   <li style="width:33.33%">
	   	<p class="col8b mb10">已转让资产</p>
		  <span class="ft14"><i id="alreadyTransferAssets">0.00</i> <b class="companyCode"></b></span>
	   </li>
	    <!-- <li class="bor_right clearfix pd0">
		 <div class="fl mt50"  style=" width: 145px;">
		  <p> 昨日收益</p>
		  <p class="ft16"><em class="colr" id="yesterdayProfit">0.00</em> <b class="companyCode"></b></p>
		 </div>
		 <div class="fl wid206" style="width: 405px;    height: 200px;">
		  <p class="col8b mb10">历史投资</p>
		  <span class="ft14"><i id="historyInvest">0.00</i> <b class="companyCode"></b></span>
		  <p class="mt40 col8b mb10">累计收益</p>
		  <span class="ft14"><i id="cumulativeProfit">0.00</i> <b class="companyCode"></b></span>
		 </div>
		</li> -->
		<!-- <li class="clearfix pd0" style="width:370px;">
		 <div class="fl mt50 ml75">
		  <p> 转让中资产</p>
		  <p class="ft16"><em id="transferingAssets">0.00</em> <b class="companyCode"></b></p>
		 </div>
		 <div class="fr" style="min-width: 125px;">
		  <p class="col8b mb10">可转让资产</p>
		  <span class="ft14"><i id="canTransferAssets">0.00</i> <b class="companyCode"></b></span>
		  <p class="mt40 col8b mb10">已转让资产</p>
		  <span class="ft14"><i id="alreadyTransferAssets">0.00</i> <b class="companyCode"></b></span>
		 </div>
		</li> -->
	   </ul>
	   <h4 class="tit_xt clearfix">我的项目</h4>
	   <ul class="clearfix my_nav" id="projectTit">
	    <li><a class="cur" vname="cpzc">产品众筹</a></li>
		<li><a vname="fgy">非公开融资</a></li>
		<li><a vname="gyzc">公益众筹</a></li>
	   </ul>
	   <div id="projectBody">
	   <ul class="zhc_xq clearfix mb10" id="cpzc_body">
	    <li>
		 <p>我支持的</p>
		 <a href="<%=path %>/common/administrationProduct.html?type=0"><span class="col_blue"><em id="productSupportCount">0</em>个</span></a>
		</li>
		<li>
		 <p>我申请的</p>
		 <a href="<%=path %>/common/administrationProduct.html?type=1"><span class="col_blue"><em id="productApplyCount">0</em>个</span></a>
		</li>
		<li>
		 <p>我转让的</p>
		 <a href="<%=path %>/common/administrationProduct.html?type=2"><span class="col_blue"><em id="productTransferCount">0</em>个</span></a>
		</li>
		<li style="margin-right:0">
		 <p>我收藏的</p>
		 <a href="<%=path %>/common/administrationProduct.html?type=3"><span class="col_blue"><em id="productKeepCount">0</em>个</span></a>
		</li>
	   </ul>
	   <ul class="zhc_xq clearfix mb10" id="fgy_body">
	    <li>
		 <p>我支持的</p>
		 <a href="<%=path %>/common/administrationStock.html?type=0"><span class="col_blue"><em id="stockSupportCount">0</em>个</span></a>
		</li>
		<li>
		 <p>我申请的</p>
		 <a href="<%=path %>/common/administrationStock.html?type=1"><span class="col_blue"><em id="stockApplyCount">0</em>个</span></a>
		</li>
		<li style="margin-right:0">
		 <p>我收藏的</p>
		 <a href="<%=path %>/common/administrationStock.html?type=2"><span class="col_blue"><em id="stockKeepCount">0</em>个</span></a>
		</li>
	   </ul>
	   <ul class="zhc_xq clearfix mb10" id="gyzc_body">
	    <li>
		 <p>我支持的</p>
		 <a href="<%=path %>/common/administrationPublic.html?type=0"><span class="col_blue"><em id="publicSupportCount">0</em>个</span></a>
		</li>
		<li>
		 <p>我申请的</p>
		 <a href="<%=path %>/common/administrationPublic.html?type=1"><span class="col_blue"><em id="publicApplyCount">0</em>个</span></a>
		</li>
		<li style="margin-right:0">
		 <p>我收藏的</p>
		 <a href="<%=path %>/common/administrationPublic.html?type=2"><span class="col_blue"><em id="publicKeepCount">0</em>个</span></a>
		</li>
	   </ul>
	   </div>
	</div>
</div>
</div>
<div class="bgpop" id="bgpop"></div>
<script type="text/javascript" src="<%=path%>/js/common/jPages.js"></script>
<script type="text/javascript" src="<%=path%>/js/userCenter/modifyData.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/copy/swfobject.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>