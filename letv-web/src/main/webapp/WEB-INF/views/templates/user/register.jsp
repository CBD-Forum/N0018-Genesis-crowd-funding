<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%
String path = request.getContextPath();
%>

<div class="login_box">	
 <div class="box">
  <div class="login_pop login_pop2">
  	<h3 id="registerTit"></h3>
   <%-- <h3>已有账号，<a href="<%=path%>/common/login.html">立即登录</a></h3> --%>
   <div class="pd_num">
    <input type="text"  placeholder="昵称" class="ipt_text" id="regiterUserId" maxlength="20" nullmessage="昵称 不能为空"/>
    <input type="text"  placeholder="手机号" class="ipt_text" id="phoneNum"/>
	<div class="clearfix">
	 <input type="text"  placeholder="请输入图形验证码" class="ipt_text fl wid138" id="valiCode" nullmessage="图片验证码不能为空"/>
	 <a class="fr"><img alt="点击刷新" width="132px" height="40px" style="cursor:pointer;" id="imageStream" src="<%=path%>/security/securityCodeImage.html?+new Date().getTime()"
				 onclick="this.src='<%=path%>/security/securityCodeImage.html?'+new Date().getTime()" /></a>
	</div>
	<div class="clearfix">
	 <input type="text"  placeholder="手机验证码" class="ipt_text fl wid138" id="verifycode"  nullmessage="手机验证码不能为空"/>
	 <a class="log_btn wid134 fr" id="sendViliimgBtn">获取</a>
	</div>
	<input type="password"  placeholder="新密码6-16位的字母、数字或下划线" class="ipt_text" id="password" nullMessage="密码不能为空"/>
    <input type="password"  placeholder="请再次输入，防止输入错误" class="ipt_text" id="password2" nullMessage="确认密码不能为空"/>
	<input type="text" placeholder="邀请人手机号" class="ipt_text" id="inviteCode" style="margin-bottom: 10px;" />
	<p style="margin-bottom: 10px;color:#FFF;"><input type="checkbox" style="margin-right: 5px;top: 1px; position: relative;" id="read" nullmessage="请阅读并同意乐视金融众筹网站服务协议">阅读并同意《<a  target="_blank" href="<%=path%>/common/FDFagreement.html?pdf=register_Agreement" style="color:#FFF;"  id="readTit"></a>》</p>
	<a class="log_btn" id="regeditBtn">立即注册</a>
	<p class="login_ts" style="line-height: 25px;">已有账号，<a href="<%=path%>/common/login.html">立即登录</a></p>
   </div>
  </div>
 </div>
</div>


<div class="bgpop"></div>
<script type="text/javascript" src="<%=path%>/js/user/register.js"></script>

