<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%
String path = request.getContextPath();
%>
<div class="login_box">	
 <div class="box">
  <div class="login_pop login_pop2">
   <h3>已有账号，<a href="<%=path%>/common/login.html">立即登录</a></h3>
   <div class="pd_num">
    <input type="text"  placeholder="手机号" class="ipt_text" id="username"/>
	<div class="clearfix">
	 <input type="text"  placeholder="请输入图形验证码" class="ipt_text fl wid138" id="regiterValiCode" nullmessage="图片验证码不能为空"/>
	 <a class="fr"><img alt="点击刷新" width="132px" height="40px" style="cursor:pointer;" id="imageStream2" src="<%=path%>/security/securityCodeImage.html?+new Date().getTime()"
				 onclick="this.src='<%=path%>/security/securityCodeImage.html?'+new Date().getTime()" /></a>
	</div>
	<div class="clearfix">
	 <input type="text"  placeholder="手机验证码" class="ipt_text fl wid138" id="phoneCode"  nullmessage="手机验证码不能为空"/>
	 <a class="log_btn wid134 fr" id="sendViliimgBtn">获取</a>
	</div>
	<input type="password"  placeholder="新密码6-16位的字母、数字和符号" class="ipt_text" id="password" nullMessage="密码不能为空"/>
    <input type="password"  placeholder="请再次输入，防止输入错误" class="ipt_text" id="password2" nullMessage="确认密码不能为空"/>
	<input type="text" placeholder="邀请人手机号" class="ipt_text" id="inviteCode"/>
	<a class="log_btn" id="findPwdSubmit">立即注册</a>
   </div>
  </div>
 </div>
</div>

<input type="hidden" id="findUserId"/>
<!-- 整体遮盖曾 -->
<div id="bgpop" class="bgpop"></div>

<script type="text/javascript" src="<%=path%>/js/common/validate.js"></script>
<script type="text/javascript" src="<%=path%>/js/user/findPass.js"></script>
