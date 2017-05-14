<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>
<style>
.header2{ border-bottom: 3px solid #1c2024;}
.bai_box{ background-color: #f4f3f3;overflow:hidden;padding-bottom:30px;}
</style>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/ueditor/third-party/webuploader/webuploader.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/demo.css"/>
<div class="bai_box">
    <div class="box" style="margin-top:27px;margin-bottom:20px;">
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
			 <p><a class="col_blue" href="<%=path %>/common/administrationPublic.html">公益众筹</a></p>
			</div>
		   </li>
		   <li><a href="<%=path %>/common/myTrade.html">交易记录</a></li>
		   <li><a href="<%=path %>/common/modifyData.html">个人信息</a></li>
		   <li><a href="<%=path %>/common/accountSecurity.html">安全中心</a></li>
		   <li><a href="<%=path %>/common/messages.html">消息中心</a></li>
		   <li class="cur"><a href="<%=path %>/common/sealList.html">合同管理</a></li>
		   <li><a href="<%=path %>/common/invitefriend.html">邀请管理</a></li>
	   </ul>
	</div>
	<div class="fr rg_side clearfix">
	   <h4 class="tit_xt clearfix">合同管理</h4>
	   <ul class="screen clearfix" id="tradeTab">
            <li><a class="a_home" href="javascript:void(0);" type="investor">我支持的</a></li>
            <li><a href="javascript:void(0);" type="project">我申请的</a></li>
        </ul>
        <div id="contractBody">
	   		<ul class="hb-table" style="width:100%;"></ul>
	   	</div>
	   <div class="letvPage" id="contractPage"></div>
	</div>

    </div>
</div>
<div class="prompt_box prompt_phone" id="phoneDiv" style="top:100px; left:50%;margin-left: -252px;z-index: 9999;">
	<a class="prompt_close">关闭</a>
	<h4>签署合同验证</h4>
	<ul>
		<li>
			<p class="p1">验证码</p>
			<input type="text" id="verifycode" nullmessage="请输入签署合同验证" placeholder="请输入手机验证码" />
		</li>
	</ul>
	<a class="modify_phone_btu" id="updateMobile" href="javascript:void(0);">确认</a>
</div>
<img src="<%=path%>/images/letv/Load.gif" class="Load" style="">
<div id="bgpop" class="bgpop" name="bigp"></div>

<script type="text/javascript" src="<%=path%>/js/common/jPages.js"></script>
<script type="text/javascript" src="<%=path%>/js/userCenter/modifyData.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/userCenter/sealList.js"></script>