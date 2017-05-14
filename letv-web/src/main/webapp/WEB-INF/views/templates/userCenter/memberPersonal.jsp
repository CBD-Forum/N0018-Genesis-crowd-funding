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
   <h3 class="chz-tit">个人创建会员</h3>
   <p class="card_line">会员信息</p>
   <div class="recharge-info">
    
	   <div class="chz_input kh_info">
	    <p class="mt20 clearfix">
		 <span class="fl">会员名称</span>
		 <input type="text"  placeholder="" class="fl" id="memberName" style="background: #eeeeee;color:#bbb;" readonly="readonly"/>
		</p>
		<p class="mt20 clearfix">
		 <span class="fl"> 真实姓名</span>
		 <input type="text"  placeholder="" class="fl" id="realName" style="background: #eeeeee;color:#bbb;" readonly="readonly"/>
		</p>
		<p class="mt20 clearfix">
		 <span class="fl"> 身份证号</span>
		 <input type="text"  placeholder="" class="fl" id="idCardNo" style="background: #eeeeee;color:#bbb;" readonly="readonly"/>
		</p>
		<p class="mt20 clearfix">
		 <span class="fl"><em>* </em> 手机号</span>
		 <input type="text"  placeholder="" class="fl" id="mobile" style="background: #eeeeee;color:#bbb;" readonly="readonly"/>
		</p>
		<p class="mt20 clearfix">
		 <span class="fl"> 邮箱</span>
		 <input type="text"  placeholder="请输入邮箱" class="fl" id="email"/>
		</p>
		<p class="mt20 clearfix">
		 <span class="fl">职业</span>
		 <input type="text"  placeholder="请输入会员名称" class="fl" id="career"/>
		</p>
		<div class="clearfix operat_btn" style="margin:0 0 0 210px;">
		 <a class="cur wid225" style="margin-bottom:0" id="personalBtn">提交</a>
		 <a class="cur wid225" href="javascript:history.go(-1);" id="return" style="display:none;">返回</a>
		</div>
	   </div>
	   
	</div>
	<div class="tishi_div">
	 <p>温馨提示：</p>
	 <p>1.会员是进行个人和企业用户区分的依据，请您认真填写；</p>
	 <p>2.一旦选择创建个人会员，将无法对其进行修改，请您知悉；</p>
	 <p>3.开通会员后，您可以在平台进行充入、投资和提出等操作；</p>
	 <p>4.我们承诺不会将您的个人身份信息外泄或用于其它用途。</p>
	</div>
   </div>
  </div>

<div id="bgpop" class="bgpop" name="bigp"></div>

<script type="text/javascript" src="<%=path%>/js/userCenter/memberPersonal.js"></script>