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
		   <li><a href="<%=path %>/common/modifyData.html">个人信息</a></li>
		   <li class="cur"><a href="javascript:void(0)">安全中心</a></li>
		   <li><a href="<%=path %>/common/messages.html">消息中心</a></li>
		   <li><a href="<%=path %>/common/sealList.html">合同管理</a></li>
		   <li><a href="<%=path %>/common/invitefriend.html">邀请管理</a></li>
	   </ul>
	</div>
	<div class="fr rg_side">
		<h3 class="zhaq_h3">安全中心</h3>
		<ul class="zhaq_ul">
			<li class="fl">
				<div class="modify_div">
					<p class="p3">实名认证</p>
					<p class="p1 c1" id="realName"></p>
					<p id="iconRZ"></p>
				</div>
				<a class="modify_btu" id="nameRZ"></a>
			</li>
			<li class="fr">
				<div class="modify_div">
					<p class="p3">开通会员</p>
					<p class="p1 c1" id="member" style="height:20px;"></p>
					<p id="memberRZ"></p>
				</div>
				<a class="modify_btu" id="memberBtn"></a>
			</li>
			<li class="fl">
				<div class="modify_div">
					<p class="p3">印章开户</p>
					<p class="p1 c1" style="height:25px;">签署合同时需要印章开户</p>
					<p id="sealRz"></p>
				</div>
				<a class="modify_btu" id="modifySeal"></a>
			</li>
			<li class="fr">
				<div class="modify_div">
					<p class="p3">手机号</p>
					<p class="p1 c1" id="mobile"></p>
					<p><i class="i2"></i>已设置</p>
				</div>
				<a class="modify_btu" onclick="modifyPhone()">修改</a>
			</li>
			<li class="fl">
				<div class="modify_div">
					<p class="p2 p3">登录密码</p>
					<p class="p1 c1">登录账户时需要登录密码</p>
					<p class="p2"><i class="i2"></i>已设置</p>
				</div>
				<a class="modify_btu" onclick="modifyPwd()">修改</a>
			</li>
			<li class="fr">
				<div class="modify_div">
					<p class="p3">邮箱验证</p>
					<p class="p1 c1" id="email" style="height:25px;"></p>
					<p class="p3" id="emailRZ"></p>
				</div>
				<a class="modify_btu" id="modifyEmail"></a>
			</li>
			<li class="fl">
				<div class="modify_div">
					<p class="p3">银行卡绑定</p>
					<p class="p1 c1" id="bankCard"></p>
					<p id="CiconRZ"></p>
				</div>
				<a class="modify_btu" id="bindBankCard"></a>
			</li>
		</ul>
	</div>
	<div class="prompt_box prompt_phone" id="phoneDiv">
		<a class="prompt_close">关闭</a>
		<h4>修改手机号</h4>
		<ul>
			<li>
				<p class="p1">原手机号</p>
				<p class="p2" id="phone"></p>
			</li>
			<li id="VerifycodeDiv">
<!-- 				<p class="p1">验证码</p> -->
<!-- 				<input type="text" id="verifycode" nullmessage="请输入手机验证码" placeholder="请输入验证码" /> -->
<!-- 				<a class="verifica_btu" id="sendBtn">获取验证码</a> -->
			</li>
			<li>
				<p class="p1">新手机号</p>
				<input class="long" type="text" id="mobile1" placeholder="请输入新的手机号" />
			</li>
			<li id="VerifycodeDiv1">
<!-- 				<p class="p1">验证码</p> -->
<!-- 				<input type="text" id="verifycode1" nullmessage="请输入手机验证码" placeholder="请输入验证码" /> -->
<!-- 				<a class="verifica_btu" id="sendBtn1">获取验证码</a> -->
			</li>
		</ul>
		<a class="modify_phone_btu" id="updateMobile" href="javascript:void(0);">确认</a>
	</div>
	<div class="prompt_box prompt_phone" id="passwordDiv">
		<a class="prompt_close">关闭</a>
		<h4>修改登录密码</h4>
		<ul>
			<li>
				<p class="p1">原密码</p>
				<input class="long" id="pass" type="password" nullMessage="原始密码不能为空"placeholder="请输入原登录密码" />
			</li>
			<li>
				<p class="p1">新密码</p>
				<input class="long" id="newPass" type="password"placeholder="新密码6-16位数字、字母和符号的组合" />
			</li>
			<li>
				<p class="p1">确认新密码</p>
				<input class="long" id="okPass" type="password"placeholder="请确认新的登录密码" />
			</li>
		</ul>
		<a class="modify_phone_btu" id="savePass" href="javascript:void(0);">确认</a>
	</div>
	<div class="prompt_box prompt_phone" id="emailDiv">
		<a class="prompt_close">关闭</a>
		<h4>绑定邮箱</h4>
		<ul>
			
		</ul>
		<a class="modify_phone_btu" id="checkEmail" href="javascript:void(0);">确认</a>
	</div>
</div>
</div>
<div style="display:none;" id="emailDivHtml">
	<li>
		<p class="p1">邮箱</p>
		<input class="long" id="newEmail" type="text" nullMessage="请输入邮箱" placeholder="请输入您的邮箱" />
	</li>
	<li>
		<p class="p1">验证码</p>
		<input type="text" id="emailCode" nullmessage="请输入邮箱验证码" placeholder="请输入验证码" />
		<a class="verifica_btu" id="emailSendBtn">获取验证码</a>
	</li>
</div>
<input type="hidden" id="tel"/>
<div class="kh_alert">
  <h3 class="clearfix tc">
   <span>开通会员</span>
   <a class="fr" id="alert-none">关闭</a>
  </h3>
  <ul class="kh_det clearfix">
   <li class="fl">
	   <span class="pic1"><img src="<%=path%>/images/letv/kh1.png"><br/><br/>个人会员</span>
	   <span class="pic2"><img src="<%=path%>/images/letv/kh1.png"><br/><br/>个人会员</span>
	   <a href="<%=path%>/common/memberPersonal.html"></a>
   </li>

   <li class="fr">
	   <span class="pic1"><img src="<%=path%>/images/letv/kh2.png"><br/><br/>企业会员</span>
	   <span class="pic2"><img src="<%=path%>/images/letv/kh2.png"><br/><br/>企业会员</span>
	   <a href="<%=path%>/common/memberEnterprise.html"></a>
   </li>
  </ul>
 </div>
<div class="sbgpop"></div>
<div class="gbpop"></div>
<div class="bgpop" id="bgpop"></div>
<div style="display:none;" id="VerifycodeHtml">
	<p class="p1">验证码</p>
	<input type="text" id="verifycode" nullmessage="请输入手机验证码" placeholder="请输入验证码" />
	<a class="verifica_btu" id="sendBtn">获取验证码</a>
</div>
<div style="display:none;" id="VerifycodeHtml1">
	<p class="p1">验证码</p>
	<input type="text" id="verifycode1" nullmessage="请输入手机验证码" placeholder="请输入验证码" />
	<a class="verifica_btu" id="sendBtn1">获取验证码</a>
</div>
<script type="text/javascript" src="<%=path%>/js/userCenter/modifyData.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/userCenter/accountSecurity.js"></script>
