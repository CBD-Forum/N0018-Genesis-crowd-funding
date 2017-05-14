<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>
<div class="bai_box" style="min-height:300px;">
	<div class="box">
		<!-- 确认收货地址 -->
		<div class="sup_tit">确认收货地址</div>
		<div id="address"></div>
		<div id="addAddressBox" class="add_address_div">
			<p><label>收件人：</label><input type="text" id="postUser" nullMessage="收件人不能为空"/><span class="add_hou_tip" style="margin-left:20px;">*（收件人信息，请确认无误！）</span></p>
			<p><label>手机：</label><input type="text" id="postPhone"/><span class="add_hou_tip" style="margin-left:20px;">*（手机号码用于接收回报信息，请确认无误！）</span></p>
			<p>
				<label>所在地：</label>
				<select id="postProvince" nullMessage="省份不能为空"><option value="">请选择</option></select>
				<select id="postCity" nullMessage="城市不能为空"><option value="">请选择</option></select>
				<span class="add_hou_tip">*（所在地信息，请确认无误！）</span>
			</p>
			<p><label>详细地址：</label><input type="text" id="postDetail" nullMessage="详细地址不能为空"/><span class="add_hou_tip" style="margin-left:20px;">*（详细地址信息，请确认无误！）</span></p>
			<p><label>邮编：</label><input type="text" id="postCode"/></p>
			<p id="isDefaultP"><label>设置为默认：</label><input type="radio" value="1" name="pDefault" checked="true"/>是<input type="radio" value="0" name="pDefault" style="margin-left:10px;"/>否</p>
			<p style="margin-top:20px;padding-left:100px;">
				<span class="btn s" id="saveBtn">保存</span><span class="btn q" id="canelSaveBtn">取消</span>
			</p>
		</div>
		<!-- 确认支持详情 -->
		<div class="sup_tit">确认支持详情</div>
		<div class="supdtl">
	       <div class="supdtl-head">
	           <span class="pos1">项目名称</span>
	           <span class="pos2">回报内容</span>
	           <span class="pos3" style="width:240px;">支持金额</span>
<!-- 	           <span class="pos4">配送费用</span> -->
	           <span class="pos5">预计回报发送时间</span>
	       </div>
	       <div class="supdtl-cont">
	           <div class="supdtl-cont-top">
	               <span class="pos1"><a id="supCover" href="" target="_blank"><img src="" alt="" /></a><span><a href="" id="supLoanName" target="_blank"></a></span></span>
	               <span class="pos2"><span class="morecon" id="supContent"></span></span>
	               <span class="pos3 js-pos3" id="supAmt" style="width:240px;"></span>
<!-- 	               <span class="pos4 js-pos4" id="supTransFee">免费</span> -->
	               <span class="pos5" id="supBackTime"></span>
	           </div>
	       </div>
	   </div>
	   <div>
	   		留言：<input type="text" id="saleInput" placeholder="请输入想跟项目发起人说的话" style="width:300px;height:30px;padding-left: 5px;"/>
	   </div>
	   <!-- 底部按钮 -->
	   <div id="suphAmt" class="sup_amt"></div>
	   <div style="text-align:right;margin-top:20px;padding:0 50px 50px;">
	   <span id="supportBtn" class="sup_btn">确认付款</span>
	   </div>
	   <form id="supFomr" action="<%=path%>/fundpool/invest/submitInvest.html" method="post">
	   		<input type="hidden" name="supportAmt" id="subSupportAmt" />
	   		<input type="hidden" name="loanNo" id="subLoanNo" />
	   		<input type="hidden" name="backNo" id="subBackNo" />
	   		<input type="hidden" name="invstType" value="commonInvest"/>
	   		<input type="hidden" name="postAddressNo" id="subPostAddressNo" />
	   		<input type="submit" id="supFormBtn" style="display:none;"/>
	   </form>
	</div>
</div>

<input id="indexFor" type="hidden" namefor="loan"/>
<script type="text/javascript" src="<%=path%>/js/project/supportPage-entity.js"></script>
