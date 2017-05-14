<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
String realName = (String)session.getAttribute("realName");
String userId = session.getAttribute("userId")==null?null:session.getAttribute("userId").toString();
String lastLoginTime = (String)session.getAttribute("lastLoginTime");
String isBorrower = (String)session.getAttribute("isBorrower");
String isAuth = (String)session.getAttribute("isAuth");
%>
<script type="text/javascript">
	var realName = "<%=realName%>";
	var userId = "<%=userId%>";
	var lastLoginTime = "<%=lastLoginTime%>";
	var isBorrower = "<%=isBorrower%>";
	var isAuth = "<%=isAuth%>";
</script>
<style>
.header2{ border-bottom: 3px solid #1c2024;}
.bai_box{ background-color: #f4f3f3;overflow:hidden;padding-bottom:30px;}
</style>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/ueditor/third-party/webuploader/webuploader.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/demo.css"/>
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
			 <p><a href="<%=path %>/common/administrationPublic.html">非公开融资</a></p>
			 <p><a href="<%=path %>/common/administrationStock.html">公益众筹</a></p>
			</div>
		   </li>
		   <li><a href="<%=path %>/common/myTrade.html">交易记录</a></li>
		   <li class="cur"><a href="javascript:void(0)">个人信息</a></li>
		   <li><a href="<%=path %>/common/accountSecurity.html">安全中心</a></li>
		   <li><a href="<%=path %>/common/messages.html">消息中心</a></li>
		   <li><a href="<%=path %>/common/sealList.html">合同管理</a></li>
		   <li><a href="<%=path %>/common/invitefriend.html">邀请管理</a></li>
	   </ul>
	</div>
	<div class="fr rg_side" style="min-height:700px;">
		<h3 class="zhaq_h3">个人信息</h3>
		<div style="overflow:hidden;position:relative;">
			<ul class="screen clearfix" id="tradeTab">
	            <li><a href="<%=path %>/common/modifyData.html">基本信息</a></li>
	            <li><a class="a_home" href="javascript:void(0);">收货地址管理</a></li>
	        </ul>
			<a class="address_add">
				<img src="<%=path %>/images/letv/zhaq04.png" style="position:relative;top:-3px;">
				新增收货地址
			</a>
		</div>
		<div class="address_head">
			<p>收货人</p>
			<p>所在地区</p>
			<p class="p2">详细地址</p>
			<p>邮编</p>
			<p>手机号</p>
			<p>操作</p>
		</div>
		<ul class="address_cont" id="addressList"></ul>
		<ul class="newAddredd" id="newAddredd" style="display:none;">
           	<li style="margin-top:0px;">
                <span class="title">所在地区<i>*</i></span>
                <select id="province" class="citySelect" nullmessage="省份不能为空"></select>
                <select id="city" class="cityStreet" nullmessage="省市区不能为空"></select>
                <select id="county" class="cityCounty" nullmessage=""></select>
            </li>
            <li>
            	<span class="title">详细地址<i>*</i></span>
                <input id="adressDetail" class="combobox-input" style="width:400px;" type="text" placeholder="请填写详细的收获地址">
            </li>
            <li>
            	<span class="title">邮政编码<i>*</i></span>
                <input id="postCode" type="text" class="i-text" maxlength="16" style="width:255px;" placeholder="若不清楚邮政区号，请填写000000" value="">
            </li>
            <li>
            	<span class="title">收货人姓名<i>*</i></span>
                <input id="postUser" class="i-text" maxlength="25" style="width:255px;" placeholder="长度不超过25个字符" type="text" value="">
            </li>
            <li>
            	<span class="title">手机号码<i>*</i></span>
                <input id="mobile" class="i-text" placeholder="" style="width:255px;" type="text" value="">
            </li>
            <!-- <li class="item-set-default" style=" margin-bottom:0px;">
            	 <label class="tsl"><input id="aDefault" type="checkbox" default="0">设置为默认收货地址</label>
            </li> -->
			<li class="btuPut"><a id="saveAddress" href="javascript:void(0);">保存</a></li>
		</ul>
	</div>
</div>
</div>
<div class="bgpop"></div>
<script type="text/javascript" src="<%=path%>/js/userCenter/modifyData.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/userCenter/modifyAddress.js"></script>