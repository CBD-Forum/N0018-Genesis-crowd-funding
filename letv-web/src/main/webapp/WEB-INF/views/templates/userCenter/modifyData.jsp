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
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/ueditor/third-party/webuploader/webuploader.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/demo.css"/>
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
	<div class="fr rg_side">
		<h3 class="zhaq_h3">个人信息</h3>
		<ul class="screen clearfix" id="tradeTab">
            <li><a class="a_home" href="javascript:void(0);">基本信息</a></li>
            <li><a href="<%=path %>/common/modifyAddress.html">收货地址管理</a></li>
        </ul>
        <div>
        	<ul class="modifyUl">
        		<li class="rupdate">
        			<p style="line-height:113px;">头像</p>
        			<div class="tx fl" id="image_file">
	            		<input hidefocus="true" style="font-size:50px;display:none;" name="file" type="file" />
	                	<a href="javascript:void(0);" class="photo"><img id="imghead" src="" width="100%" height="100%" style="border-radius:50%;"/></a>
	                    <div class="cover" nullMessage="请上传项目封面" style="height:3px"></div>
	                    <input type="hidden" id="loan_logo_url"/>
	                </div>
	                <div class="tx fl" style="color: #aaa;padding-top: 20px;line-height: 25px;"> 图片尺寸：113*113px<br/>图片格式：png、jpg、gjf、jpeg<br/>图片大小：5M以内</div>
        		</li>
        		<li>
        			<p>昵称</p>
        			<input type="text" id="nickName" readonly="readonly" class="bor" />
        		</li>
        		<li>
        			<p>所在公司</p>
        			<input type="text" id="company" readonly="readonly" class="bor" onchange="this.value=this.value.substring(0, 30)" onkeydown="this.value=this.value.substring(0, 30)" onkeyup="this.value=this.value.substring(0, 30)" />
        		</li>
        		<li>
        			<p>当前职位</p>
        			<input type="text" id="position" readonly="readonly" class="bor"onchange="this.value=this.value.substring(0, 20)" onkeydown="this.value=this.value.substring(0, 20)" onkeyup="this.value=this.value.substring(0, 20)" />
        		</li>
        		<li>
        			<p>个人简介</p>
        			<div style="width:600px;display:block;line-height: 25px;color: #e1e1e1;" id="selfDetailDiv">一句话简介，文字不超过20个</div>
        			<textarea id="selfDetail" id="selfDetail" style="display:none" rows="4" placeholder="一句话简介，文字不超过20个"></textarea>
        		</li>
        	</ul>
        	<a class="modifyBtu" id="submitInfo">修改</a>
        </div>
	</div>
</div>
</div>
<div class="bgpop" id="bgpop"></div>
<script type="text/javascript" src="<%=path%>/js/userCenter/modifyData.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>