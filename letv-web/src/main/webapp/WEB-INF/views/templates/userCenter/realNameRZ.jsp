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
<div class="box pt30" style="background:#fff;">
	<div class="realName">
		<h4 class="tit_xt">实名认证</h4>
		<div>
			<div class="realTipDiv">
				<p class="p1">身份验证</p>
			</div>
			<ul class="realNameUl">
				<li>
					<p><i>*</i> 真实姓名  </p>
					<input type="text" id="realname" placeholder="请输入您的真实姓名"/>
				</li>
				<li>
					<p><i>*</i> 身份证号  </p>
					<input type="text" id="cardCode" placeholder="请输入您的身份证号"/>
				</li>
				<li>
					<p><i>*</i> 银行卡号  </p>
<!-- 					<input type="text" id="bankcardNo"/> -->
					<input type="text" id="bankcardNo" nullMessage="请输入正确的银行卡号" placeholder="请输入正确的银行卡号" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" />
				</li>
			</ul>
			<a class="realNameBtu" id="subInvestBtn">确定</a><a class="realNameBtu indexUrl" id="indexUrl" href="<%=path %>/common/index.html" >跳过</a>
		</div>
		<div class="realTipDiv">
			<p>温馨提示：</p>
			<p>1.为给您提供更好的金融服务，请您完善身份信息；</p>
			<p>2.实名认证需要验证四要素：姓名、身份证号、银行卡号和银行预留手机号，其中银行预留手机号默认验证您注册时填写的手机号码；</p>
			<p>3.实名认证成功可提升您的个人账户安全。</p>
		</div>
	</div>
</div>
</div>
<form method="post" action="<%=path %>/sxyPay/account/createUserAccount.html">
    <input type="hidden" id="form_realName" name="realName"/>
    <input type="hidden" id="form_certificateNo" name="certificateNo"/>
    <input type="hidden" id="form_bankCardNo" name="bankNo"/>
    <input id="submitBtn"  type="submit" style="display:none" />
</form>
<img src="<%=path%>/images/letv/Load.gif" class="LoadImg" style="">
<div class="bgpop" id="bgpop"></div>
<script type="text/javascript" src="<%=path%>/js/userCenter/modifyData.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/userCenter/realNameRZ.js"></script>
