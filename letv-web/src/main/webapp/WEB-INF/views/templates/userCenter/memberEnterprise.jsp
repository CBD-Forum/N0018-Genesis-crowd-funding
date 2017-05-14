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
   <h3 class="chz-tit">企业创建会员</h3>
   <p class="card_line">会员信息</p>
   <div class="recharge-info">
    
	   <div class="chz_input kh_info">
		<p class="mt20 clearfix" style="display:none;">
		 <span class="fl"><em>* </em>登录名</span>
		 <input type="text"  placeholder="" class="fl" id="loginName" style="background: #eeeeee;color:#bbb;" nullmessage="请输入登录名" readonly="readonly"/>
		</p>
		<p class="mt20 clearfix">
		 <span class="fl"> 会员名称</span>
		 <input type="text"  placeholder="" class="fl" id="memberName" style="background: #eeeeee;color:#bbb;" readonly="readonly"/>
		</p>
		<p class="mt20 clearfix">
		 <span class="fl"> 联系电话</span>
		 <input type="text"  placeholder="" class="fl" id="telephone" style="background: #eeeeee;color:#bbb;" readonly="readonly"/>
		</p>
		<p class="mt20 clearfix">
		 <span class="fl"><em>* </em>企业名称</span>
		 <input type="text"  placeholder="请输入企业名称" class="fl" id="enterpriseName" nullmessage="请输入企业名称"/>
		</p>
		<p class="mt20 clearfix">
		 <span class="fl">企业法人</span>
		 <input type="text"  placeholder="请输入企业法人" class="fl" id="legalPerson"/>
		</p>
		<p class="mt20 clearfix">
		 <span class="fl">法人手机号</span>
		 <input type="text"  placeholder="请输入法人手机号" class="fl" id="legalPersonPhone"/>
		</p>
		<p class="mt20 clearfix">
		 <span class="fl"><em>* </em> 组织机构代码</span>
		 <input type="text"  placeholder="请输入组织机构代码" class="fl" id="organizationNo" nullmessage="组织机构代码"/>
		</p>
		<p class="mt20 clearfix">
		 <span class="fl">营业执照号</span>
		 <input type="text"  placeholder="请输入营业执照号" class="fl" id="licenseNo"/>
		</p>
		<p class="mt20 clearfix">
		 <span class="fl">营业执照所在地</span>
		 <input type="text"  placeholder="请输入营业执照所在地" class="fl" id="licenseAddress"/>
		</p>
		<p class="mt20 clearfix">
		 <span class="fl">执照过期日期（营业期限）</span>
		 <input type="text"  placeholder="请输入执照过期日期（营业期限）" class="fl" id="licenseExpireDate"/>
		</p>
		<p class="mt20 clearfix">
		 <span class="fl">营业范围</span>
		 <input type="text"  placeholder="请输入营业范围" class="fl" id="businessScope"/>
		</p>
		<p class="mt20 clearfix">
		 <span class="fl">营业网址</span>
		 <input type="text"  placeholder="请输入营业网址" class="fl" id="website"/>
		</p>
		<p class="mt20 clearfix">
		 <span class="fl">企业地址</span>
		 <input type="text"  placeholder="请输入企业地址" class="fl" id="address"/>
		</p>
		<p class="mt20 clearfix">
		 <span class="fl">企业介绍</span>
		 <textarea class="fl" id="summary" maxlength="300" style="width:500px; height:100px;padding:10px;    border: 1px solid #cccccc;" placeholder="最大长度不可超过300"></textarea>
		</p>
		<div class="clearfix operat_btn" style="margin:0 0 0 210px;">
		 <a class="cur wid225" style="margin-bottom:0" id="enterpriseBtn">提交</a>
		 <a class="cur wid225" href="javascript:history.go(-1);" id="return" style="display:none;">返回</a>
		</div>
	   </div>
	   
	</div>
	<div class="tishi_div">
	 <p>温馨提示：</p>
	 <p>1.会员是进行个人和企业用户区分的依据，请您认真填写；</p>
	 <p>2.一旦选择创建企业会员，将无法对其进行修改，请您知悉；</p>
	 <p>3.开通会员后，您可以在平台进行充入、投资和提出等操作；</p>
	 <p>4.我们承诺不会将您的企业信息外泄或用于其它用途。</p>
	</div>
   </div>
  </div>

<div id="bgpop" class="bgpop" name="bigp"></div>

<script type="text/javascript" src="<%=path%>/js/userCenter/memberEnterprise.js"></script>