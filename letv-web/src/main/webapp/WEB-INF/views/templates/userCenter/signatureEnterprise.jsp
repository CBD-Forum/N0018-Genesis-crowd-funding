<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>
<style>
.header2{ border-bottom: 3px solid #1c2024;}
.bai_box{ background-color: #f4f3f3;overflow:hidden;padding-bottom:30px;}
</style>
<div class="back_colh">
  <div class="box gy_info gy_info2">
   <h3 class="chz-tit">企业印章开户</h3>
   <p class="card_line">开户信息</p>
   <div class="recharge-info">
    
	   <div class="chz_input kh_info">
		<p class="mt20 clearfix">
		 <span class="fl"><em>* </em>机构名称</span>
		 <input type="text"  placeholder="请输入正确的机构名称" class="fl" id="orgName" nullmessage="请输入机构名称"  onchange="this.value=this.value.substring(0, 30)" onkeydown="this.value=this.value.substring(0, 30)" onkeyup="this.value=this.value.substring(0, 30)" />
		</p>
		<p class="mt20 clearfix">
		 <span class="fl"> 统一社会信用代码</span>
		 <input type="text"  placeholder="请输入统一社会信用代码" class="fl" id="usci" nullmessage="请输入统一社会信用代码" onchange="this.value=this.value.substring(0, 30)" onkeydown="this.value=this.value.substring(0, 30)" onkeyup="this.value=this.value.substring(0, 30)"/>
		</p>
		<p class="mt20 clearfix">
		 <span class="fl"><em>* </em>组织机构代码</span>
		 <input type="text"  placeholder="请输入组织机构代码" class="fl" id="orgCode" nullmessage="请输入组织机构代码" onchange="this.value=this.value.substring(0, 30)" onkeydown="this.value=this.value.substring(0, 30)" onkeyup="this.value=this.value.substring(0, 30)"/>
		</p>
		<p class="mt20 clearfix">
		 <span class="fl"> 工商营业执照号</span>
		 <input type="text"  placeholder="请输入工商营业执照号" class="fl" id="businessNum" nullmessage="请输入工商营业执照号" onchange="this.value=this.value.substring(0, 30)" onkeydown="this.value=this.value.substring(0, 30)" onkeyup="this.value=this.value.substring(0, 30)"/>
		</p>
		<p class="mt20 clearfix">
		 <span class="fl"><em>* </em>法定代表人</span>
		 <input type="text"  placeholder="请输入法定代表人真实姓名" class="fl" id="legalPersonName" nullmessage="请输入法定代表人真实姓名"/>
		</p>
		<p class="mt20 clearfix">
		 <span class="fl"><em>* </em> 经办人姓名</span>
		 <input type="text"  placeholder="请输入您的真实姓名" class="fl" id="transactorName" style="background: #eeeeee;" readonly="readonly"/>
		</p>
		<p class="mt20 clearfix">
		 <span class="fl"><em>* </em> 经办人居民身份证号码</span>
		 <input type="text"  placeholder="请输入有效的证件号码" class="fl" id="transactoridCardNum" style="background: #eeeeee;" readonly="readonly"/>
		</p>
		<p class="mt20 clearfix">
		 <span class="fl"><em>* </em> 经办人手机号</span>
		 <input type="text"  placeholder="请输入您的手机号码" class="fl" id="transactorMobile"/>
		</p>
		<p class="mt20 clearfix" id="yinzhang" style="display:none;">
		 <span class="fl"><em></em> 印章</span>
		 <img src="" id="img" />
		</p>
		<p class="mt20 clearfix">
		 <span class="fl"><em>* </em> 图片验证码</span>
		 <input type="text"  placeholder="请输入图片验证码" class="fl mr8 wid136" id="valiCode" nullmessage="请输入图片验证码"/>
		 <a class="fl"><img src="<%=path%>/security/securityCodeImage.html?+new Date().getTime()" id="imageStream" onclick="this.src='<%=path%>/security/securityCodeImage.html?'+new Date().getTime()" /></a>
		</p>
		<p class="mt20 clearfix">
		 <span class="fl"><em>* </em> 手机验证码</span>
		 <input type="text"  placeholder="点击获取验证码" class="fl mr8 wid136" id="verifycode" nullmessage="请输入手机验证码"/>
		 <a class="log_btn fl" id="sendViliimgBtn" >获取</a>
		</p>
		<div class="clearfix operat_btn" style="margin:0 0 0 210px;">
		 <a class="cur wid225" style="margin-bottom:0" id="enterpriseBtn">提交</a>
		 <a class="cur wid225" href="javascript:history.go(-1);" id="return" style="display:none;">返回</a>
		</div>
	   </div>
	   
	</div>
	<div class="tishi_div">
	 <p>温馨提示：</p>
	 <p>1.请正确填写您的印章开户信息，众筹平台会根据您的企业信息创建您的专属电子印章；</p>
	 <p>2.电子印章具有唯一性，且一旦创建，将无法对其进行修改，请您知悉；</p>
	 <p>3.电子印章是平台上电子合同签署的有效证明，发起项目或者进行电子合同签署时必须具备有效的电子印章；</p>
	 <p>4.您专属的电子印章将会在与您相关的电子合同上进行签署，您可通过下载的方式进行保存。</p>
	</div>
   </div>
  </div>

<div id="bgpop" class="bgpop" name="bigp"></div>
<script type="text/javascript" src="<%=path%>/js/userCenter/modifyData.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>

<script type="text/javascript" src="<%=path%>/js/userCenter/signatureEnterprise.js"></script>