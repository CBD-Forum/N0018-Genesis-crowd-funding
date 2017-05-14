<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>
<style>
.header2{ border-bottom: 3px solid #1c2024;}
.bai_box{ background-color: #f4f3f3;overflow:hidden;padding-bottom:30px;}
</style>
<div class="bai_box">
<div class="box">
	<h4 class="orderH4">转让下单</h4>
	<div class="orderBox">
		<div class="orderTit" id="loanName"></div>
		<ul class="orderTab">
			<li class="cur fl">
				<p class="p1">1</p>
				<p class="p2">转让订单信息填写</p>
			</li>
			<li class="fl" style="margin-left:13px;">
				<p class="p1">2</p>
				<p class="p2">支付</p>
			</li>
			<li class="fr">
				<p class="p1">3</p>
				<p class="p2">转让完成</p>
			</li>
		</ul>
		<div class="orderInfo" style="background: #f6f7f9;border:0;">
			<div class="orderAsc clearfix">
				<p style="margin-right: 100px;">出让人：<em id="transferUser"></em></p>
				<p>转让金额：<em id="transferAmt"></em><b class="companyCode"></b></p>
			</div>
			<div class="orderPs clearfix" style="padding:35px 0;">
				<p style="font-size: 16px;color: #333;">回报详情：</p>
				<p id="backContent" style="width:950px;"></p>
			</div>
			<div class="orderPs clearfix">
				<p style=" text-align: right; width: 80px;font-size: 16px;color: #333;">备注：</p>
				<input type="text" placeholder="请填写您的备注信息，20字以内" id="saleInput"style="background: #f6f7f9;"/>
			</div>
		</div>
		<ul class="orderAdd">
			<li style="display:none;">
				<p class="p1">收货地址：</p>
				<p class="fl" id="address"></p>
			</li>
			<li style="display:none;">
				<p class="p1">邮箱：</p>
				<p class="fl" id="mailbox"></p>
			</li>
			<li>
				<p class="p1">运费：</p>
				<p class="fl" id="transFee">0.00</p>
			</li>
			<li>
				<p class="p1">实付款：</p>
				<p class="fl ling"><em id="supportAmt"></em><b class="companyCode"></b></p>
			</li>
			<%-- <li>
				<label class="tsl"><input id="read" checked style="width:auto;" type="checkbox">我已认真细阅读并同意 《<a class="col_blue" target="_blank" href="<%=path %>/common/FDFagreement.html?pdf=investment_service_agreement">乐视投资服务协议</a>》及其附件《<a class="col_blue" target="_blank" href="<%=path %>/common/FDFagreement.html?pdf=fxjss">风险揭示书</a>》</label>
			</li> --%>
			<li style="padding-bottom:70px;">
				<a class="tjdd" id="transBtu">提交订单 </a>
			</li>
		</ul>
		<!-- <div class="orderTip">
			<p class="fl">风险提示：</p>
			<p class="fl p1">请您务必审慎阅读、充分理解协议中相关条款内容，其中包括：<br/>
				1、风险提示条款和特别提示条款；<br/>
				2、与您约定法律适用和管辖的条款；<br/>
				3、其他以粗体标识的重要条款。<br/>
				如您不同意相关协议、公告、规则、操作流程和项目页面承诺，您有权选择不支持；一旦选择支持，即视为您已确知并完全同意相关协议。</p>
		</div> -->
	</div>
</div>
</div>
<div class="bgpop" id="bgpop"></div>
<div class="sbgpop"></div>
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
		<p class="p3">邮编</p>
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
        <li class="item-set-default" style=" margin-bottom:0px;">
          	 <label class="tsl"><input id="aDefault" type="checkbox" default="0">设置为默认收货地址</label>
        </li>
		<li class="btuPut"><a id="saveAddress" href="javascript:void(0);">保存</a></li>
	</ul>
</div>

<div class="prompt_box prompt_phone" id="addBgpop" style="width:900px;">
	<a class="prompt_close" id="close_abs">关闭</a>
	<h4>收货地址</h4>
	<a class="add_dz" onclick="addNewAddress();">新增收货地址</a>
	<ul class="newAddredd" id="newAddredd" style="width:800px;display:none;">
       	<li style="margin-top:0px;">
            <span class="title">所在地区<i>*</i></span>
            <select id="province" class="citySelect" nullmessage="省份不能为空"><option value="">请选择省份</option><option value="110100">北京</option><option value="120100">天津</option><option value="130000">河北</option><option value="140000">山西</option><option value="150000">内蒙古</option><option value="210000">辽宁</option><option value="220000">吉林</option><option value="230000">黑龙江</option><option value="310100">上海</option><option value="320000">江苏</option><option value="330000">浙江</option><option value="340000">安徽</option><option value="350000">福建</option><option value="360000">江西</option><option value="370000">山东</option><option value="410000">河南</option><option value="420000">湖北</option><option value="430000">湖南</option><option value="440000">广东</option><option value="450000">广西</option><option value="460000">海南</option><option value="500100">重庆</option><option value="510000">四川</option><option value="520000">贵州</option><option value="530000">云南</option><option value="540000">西藏</option><option value="610000">陕西</option><option value="620000">甘肃</option><option value="630000">青海</option><option value="640000">宁夏</option><option value="650000">新疆</option><option value="710000">台湾</option><option value="810000">香港</option><option value="820000">澳门</option></select>
            <select id="city" class="cityStreet" nullmessage="省市区不能为空"><option value="">请选择</option><option value="130100">石家庄市</option><option value="130200">唐山市</option><option value="130300">秦皇岛市</option><option value="130400">邯郸市</option><option value="130500">邢台市</option><option value="130600">保定市</option><option value="130700">张家口市</option><option value="130800">承德市</option><option value="130900">沧州市</option><option value="131000">廊坊市</option><option value="131100">衡水市</option></select>
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
        <li class="item-set-default" style=" margin-bottom:0px;">
          	 <label class="tsl"><input style="width:auto;" id="aDefault" type="checkbox" default="0">设置为默认收货地址</label>
        </li>
		<li class="btuPut"><a id="saveAddress" href="javascript:void(0);">保存</a></li>
	</ul>
	<div id="address_xq" style="padding:10px 20px;"></div>
	<a class="saveAddress1" id="saveAddress1" href="javascript:void(0);">确定</a>
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
<input id="indexFor" type="hidden" namefor="gua"/>
<script type="text/javascript" src="<%=path%>/js/common/jPages.js"></script>
<script type="text/javascript" src="<%=path%>/js/transfer/transferPay.js"></script>
