<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>
<style>
.header2{ border-bottom: 3px solid #1c2024;}
</style>
<div class="back_colf9">
  <div class="box">
   <h3 class="pay_title">众筹下单</h3>
   <div class="pay_box mb70">
	   <h4><a id="supLoanName"></a></h4>
	   <ul class="clearfix step_pay">
		<li>
		 <div class="step_1 step_2">
		  <i>1</i>
		 </div>
		 <p class="col_blue">订单信息填写</p>
		</li>
		<li>
		 <div class="step_1">
		  <i>2</i>
		 </div>
		 <p>支付</p>
		</li>
		<li style="margin-right:0">
		 <div class="step_1">
		  <i>3</i>
		 </div>
		 <p>完成</p>
		</li>
	   </ul>
	   <div class="clearfix plr30">
	    <div class="fl bg-blue" style="width: 1050px;">
		 <p class="ft22">支持 <em id="supAmt"></em> <b class="companyCode"></b></p>
		 <p class="col6 jsh_xq" id="supContent" style="height:auto;    width: auto;"></p>
		 <p class="beizhu">备注:<input type="text" id="saleInput" placeholder="您还有什么需求可在此处填写，20字以内~" class="bzh_input"/></p>
		</div>
		<!-- <div class="fr bg-blue bg-blue2" style="    padding: 35px 47px 89px 42px;">
		 <p class="ft22">预计每日收益： <em id="dailyProfit"></em> <b class="companyCode"></b></p>
		 <p class="ft_bold mt43">温馨提示：</p>
		 <p class="col6 jsh_xq2">投资成功后平台会于次日 00:30 后根据预计每日收益率结算昨日收益</p>
		 <p class="ft_bold mt10">收益计算公式：预计每日收益=投资本金*预计每日收益率</p>
		</div> -->
	   </div>
	   <div class="dd_info">
	    <p style="display:none;"><span style="width:100px;">收货地址：</span><i id="address"></i></p>
	    <p style="display:none;"><span style="width:100px;">邮箱：</span><i id="mailbox"></i></p>
		<p><span style="width:100px;">运费：</span><i id="suphAmt"></i></p>
		<p><span style="width:100px;">实付款：</span><em id="paymentAmt"></em></p>
		<div class="mb30 clearfix">
		  <span style="width:100px;">当前账号余额：</span>
		  <em class="col_blue" id="balance">0</em> <b class="companyCode"></b>
		  <a class="chz_btn2" style=" text-align: center; margin: 0 0 0 20px; display: inline-block;" href="<%=path%>/common/recharge.html">充入</a>
		 </div>
		
		<div class="agree_div">
		 <div class="clearfix">
		  <input type="checkbox" class="fl" id="read"/>
		  <p class="fl" style="margin-bottom:25px;display:none" id="product">我已认真仔细阅读并同意 《<a class="col_blue" target="_blank" href="<%=path %>/common/FDFagreement.html?pdf=cp_investment_service_agreement">乐视金融众筹投资服务协议</a>》及其附件《<a class="col_blue" target="_blank" href="<%=path %>/common/FDFagreement.html?pdf=fxjss">风险揭示书</a>》</p>
		  <p class="fl" style="margin-bottom:25px;display:none" id="public">我已认真仔细阅读并同意 《<a class="col_blue" target="_blank" href="<%=path %>/common/FDFagreement.html?pdf=gy_investment_service_agreement">乐视金融众筹投资服务协议</a>》及其附件《<a class="col_blue" target="_blank" href="<%=path %>/common/FDFagreement.html?pdf=fxjss">风险揭示书</a>》</p>
		 </div>
		 <a class="sub_button" id="supportBtn">提交订单</a>
	   </div>
      </div>
	  <div class="wx_tishi clearfix">
	   <div class="fl">
	    风险提示：
	   </div>
	   <div class="fl col9">
		<p>1.请您务必审慎阅读并充分理解上述协议中相关条款内容；</p>
		<p>2.如您不同意相关协议、公告、规则、操作流程和项目页面承诺，您有权选择不支持；一旦选择支持，即视为您已确知并完全同意相关协议。</p>
	   </div>
	  </div>
  </div>
 </div>
<form id="supFomr" action="<%=path%>/fundpool/invest/submitInvest.html" method="post">
	<input type="hidden" name="supportAmt" id="subSupportAmt" />
	<input type="hidden" name="loanNo" id="subLoanNo" />
	<input type="hidden" name="backNo" id="subBackNo" />
	<input type="hidden" name="invstType" value="commonInvest"/>
	<input type="hidden" name="postAddressNo" id="subPostAddressNo" />
	<input type="submit" id="supFormBtn" style="display:none;"/>
</form>
<div class="rg_side" style="min-height:700px;position: absolute;top: 50px;left: 50%; margin-left: -460px;display:none;z-index:999;">
		<a class="prompt_close" style="line-height: 25px;padding: 0 5px;color:#333;">关闭</a>
		<div style="overflow:hidden;position:relative;">
			<ul class="screen clearfix" id="tradeTab">
	            <li><a class="a_home" href="javascript:void(0);">收货地址管理</a></li>
	        </ul>
			<a class="address_add">
				<img src="<%=path %>/images/letv/zhaq04.png" style="position:relative;top:-3px;">
				新增收货地址
			</a>
		</div>
		<div class="address_head">
			<p class="p3"></p>
			<p class="p3">收货人</p>
			<p>所在地区</p>
			<p class="p2">详细地址</p>
			<p class="p4">邮编</p>
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
<div class="prompt_box prompt_phone" id="emailDiv">
	<a class="prompt_close">关闭</a>
	<h4>绑定邮箱</h4>
	<ul>
		
	</ul>
	<a class="modify_phone_btu" id="checkEmail" href="javascript:void(0);">确认</a>
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
<div class="sbgpop"></div>
<div class="bgpop" id="bgpop"></div>
<div class="paymentBgpop" id="paymentBgpop"></div>
<input id="indexFor" type="hidden" namefor="loan"/>
<script type="text/javascript" src="<%=path%>/js/project/supportPage-pubBenefit.js"></script>
