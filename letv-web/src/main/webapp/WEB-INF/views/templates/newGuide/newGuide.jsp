<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%
	String path = request.getContextPath();
%>

<div class="bgColorWh">
<div class="box">
	<div class="navDiv">
    	<a href="<%=path%>/common/index.html">首页</a> > 新手指南
    </div>
</div>
</div>

<div class="bgColorWh">
<div class="xszn1">
</div>
</div>


<div class="xszn_reg">
	<div class="box" id="registerBtn"><a onclick="go2Regiter();" href="javascript:void(0);">立即注册</a></div>
</div>
<script type="text/javascript">
	if(siteUserId != "null"){
		$("#registerBtn").hide();
	}
</script>
<input id="slideFor" type="hidden" snamefor="guide"/>
