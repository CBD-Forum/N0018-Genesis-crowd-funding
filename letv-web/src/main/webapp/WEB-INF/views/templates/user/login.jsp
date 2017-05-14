<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%
	String path = request.getContextPath();
%>

<div class="login_box">	
 <div class="box">
  <div class="login_pop">
   <h3 id="loginTit"></h3>
   <div class="pd_num">
    <input type="text"  placeholder="手机号" class="ipt_text" id="phoneNum" maxlength="11"/>
	<input type="password"  placeholder="密码" class="ipt_text" id="password" nullMessage="密码不能为空"/>
	<p class="tr mt10"><a class="col_ju" href="<%=path%>/common/password.html">找回密码</a></p>
<!-- 	<p class="tr mt10"><a class="colc">忘记密码？</a><span class="colc">/ </span><a class="col_ju" href="<%=path%>/common/password.html">找回密码</a></p> -->
	<a class="log_btn mt30" id="loginBtn">登 录</a>
	<a class="log_btn mt20 ft14 bgh" href="<%=path%>/common/register.html">没有账号，免费注册</a>
   </div>
  </div>
 </div>
</div>
<script type="text/javascript" src="<%=path%>/js/common/validate.js"></script>
<script type="text/javascript" src="<%=path%>/js/user/login.js"></script>

