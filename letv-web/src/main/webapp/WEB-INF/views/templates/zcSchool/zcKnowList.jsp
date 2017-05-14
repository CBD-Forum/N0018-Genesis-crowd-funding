<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>
<div class="navDiv" style="width:1175px;margin:0 auto;">
    <a href="<%=path%>/common/zcSchool.html">众筹学院</a> &gt; <i id="typec"></i>
</div>
<div class="zcxy_box">
	<div class="zcxy_maLix zcxy_new">
		<ul id="zczsList"></ul>
	</div>
</div>
<input id="indexFor" type="hidden" namefor="zcSchool"/>
<script type="text/javascript" src="<%=path%>/js/zcSchool/zcKnowList.js"></script>