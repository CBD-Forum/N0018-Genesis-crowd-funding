<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>

<script type="text/javascript" src="<%=path%>/js/userCenter/myCenter.js"></script>
<style>
.header2{ border-bottom: 3px solid #1c2024;}
.bai_box{ background-color: #f4f3f3;overflow:hidden;padding-bottom:30px;}
</style>
<div class="bai_box">
<div class="box pt30" style="background:#fff;">
	<div class="realName">
		<h4 class="tit_xt">实名认证</h4>
		<div class="rz_success">
			<p class="sp1">您已经实名认证成功，资料如下：</p>
			<ul style="margin:40px 0 0 80px;">
				<li><label>姓名</label><span id="realName"></span></li>
				<li><label>身份证号</label><span id="certificateNo"></span></li>
				<li><label>认证通过时间</label><span id="createTime"></span></li>
				<li><a class="save_btn" href="<%=path%>/common/accountSecurity.html" style="margin-left:38px;">返回</a></li>
			</ul>
		</div>
		<div class="realTipDiv">
			<p>温馨提示：</p>
			<p>1.实名认证主要用于核实用户身份信息和银行账户信息，由众多知名银行共同参与；</p>
			<p>2.实名认证成功后可提升您的个人账户安全。</p>
		</div>
	</div>
</div>
</div>
<script type="text/javascript" src="<%=path%>/js/userCenter/modifyData.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>

<script type="text/javascript" src="<%=path%>/js/userCenter/realNameRZTG.js"></script>
