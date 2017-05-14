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
		<p class="rzh_cz"><a class="rzh1 cur" href="<%=path %>/common/accountSecurity.html"></a><a class="rzh2" id="realNameRZ"></a><a class="rzh3"></a></p>
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
	   <h4 class="tit_xt clearfix mb20" id="deliveryTitle"></h4>
	   <ul class="screen clearfix mb20" id="tabTit">
	       <li><a vname="wcj" href="javascript:void(0);"></a></li>
	       <li><a vname="zj" href="javascript:void(0);"></a></li>
       </ul>
       <div id="tabBody">
       		<div id="wcjBody"><div></div></div>
       		<div id="zjBody"><div></div></div>
       </div>
	   
        <div class="letvPage" id="wcjPage" style="display:none;margin-right:10px;"></div>
        <div class="letvPage" id="zjPage" style="display:none;margin-right:10px;"></div>
	  </div>

    </div>
</div>
<div id="enterDiv" class="enter_div">
	<div class="enter_title">录入物流信息</div>
	<div class="enter_content">
		<p><label style="margin-left:50px;">物流单号：</label><input type="text" id="logicoNo"/></p>
		<p><label style="margin-left:50px;">物流公司：</label><input type="text" id="logicoComp"/></p>
		<p style="text-align:center;margin-top:20px;"><span id="addLogicoBtn" class="button">确定</span><span id="canelLogicoBtn" class="button" style="margin-left:15px;">取消</span></p>
	</div>
</div>
<div id="bgpop" class="bgpop" name="bigp"></div>
<div class="sbgpop"></div>
 <!--  整体弹框 -->
<div id="agree_box2" class="agree_box2" style="z-index:106;">
	<div class="agree_close2"></div>
	<div id="leaveMessage" class="agree_con2"></div>
</div>
<div class="prompt_box dividendDetail" id="failRefund" style="width:420px;height:200px;"><!-- top:68px; left: 324.5px; display: block; -->
	<a class="prompt_close failRefund_close">关闭</a>
	<h4>录入邮箱信息</h4>
	<div class="overDiv">
		<div class="refundListDiv" style="width:300px;line-height:30px;padding:10px 0 30px 0;">
			<p style="margin-right:10px;" class="fl">邮箱信息：</p>
			<input style="width:200px;padding:0 5px;height:30px;line-height:30px;" id="emailText" class="fl" placeholder="请输入正确邮箱" readonly="readonly" />
		</div>
		<div class="refundListDiv" style="width:300px;padding-top:10px">
			<a class="fl" id="FoperatBtu" style="width:130px;height:30px;line-height:30px;">确定</a>
			<a class="fr" id="FqxBtu" style="width:130px;height:30px;line-height:30px;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript" src="<%=path%>/js/common/jPages.js"></script>
<script type="text/javascript" src="<%=path%>/js/userCenter/modifyData.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/userCenter/enterLogico.js"></script>