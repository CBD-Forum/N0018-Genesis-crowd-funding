<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>
<style>
.header2{ border-bottom: 3px solid #1c2024;}
</style>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/ueditor/third-party/webuploader/webuploader.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/demo.css"/>
<div class="back_colh">
	 <div class="box clearfix">
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
	  <div class="fr rg_side clearfix">
	   
	   <h4 class="tit_xt clearfix">绑定银行卡</h4>
	   <div class="yhk_info">
	    <div class="mb30">
		 <span>真实姓名</span>
		 <em class="col9" id="BrealName"></em>
		</div>
		<div class="mb30">
		 <span>身份证号码</span>
		 <em class="col9" id="BcertificateNo"></em>
		</div>
		<div class="mb30">
			<span><em style="color:#ff1616;">*</em> 银行名称</span>
			<select id="bankName" nullmessage="请选择开户银行"></select>
		</div>
		<div class="mb30">
		 <span><em style="color:#ff1616;">*</em> 开户行省市</span>
		 <select id="province" nullmessage="请选择省份" style="width:125px"><option value="">请选择省</option></select>
		 <select id="city" nullmessage="请选择城市" style="width:125px"><option>请选择市</option></select>
		</div>
		<div class="mb30 clearfix">
		 <span class="fl">开户行</span>
		 <input type="text" placeholder="请输入开户行支行" id="bankAddress" nullMessage="请输入开户行支行"/>
		</div>
		<div class="mb30 clearfix">
		 <span class="fl"><em style="color:#ff1616;">*</em> 开户行银行卡号</span>
		 <input type="text" placeholder="请输入银行卡号" id="bankNo" nullMessage="请输入正确的银行卡号" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" />
		</div>
		<div class="mb30 clearfix">
		 <span class="fl"><em style="color:#ff1616;">*</em> 手机验证码</span>
		 <input type="text wd148" placeholder="请输入手机验证码" class="wd148" id="phoneCode1" maxlength="6" nullMessage="请输入手机验证码" />
		 <a class="get_yzm" id="getVertifyb" href="javascript:void(0);">获取验证码</a>
		</div>
		<div class="clearfix operat_btn wid255" style="margin-bottom:38px;">
	     <a class="cur" style="margin-left:103px;" id="bindCardBtn">绑定银行卡</a>
	    </div>
	   </div>
	   <div class="tishi_div tishi_div2 mb50">
		 <p>温馨提示：</p>
		 <p>1.提现之前必须完成绑卡操作，请务必确认绑定您本人银行卡；</p>
		 <p>2.接收验证码手机号为您注册时填写的手机号码；</p>
		 <p>3.平台支持绑卡上限为五张。</p>
	    </div>
	  </div>
	 </div>
 </div>
 <img src="<%=path%>/images/letv/Load.gif" class="Load" style="">
 <div class="bgpop"></div>
<script type="text/javascript" src="<%=path%>/js/common/validate.js"></script>
<script type="text/javascript" src="<%=path%>/js/userCenter/modifyData.js"></script>
<script type="text/javascript" src="<%=path%>/js/userCenter/bankAdd.js"></script>