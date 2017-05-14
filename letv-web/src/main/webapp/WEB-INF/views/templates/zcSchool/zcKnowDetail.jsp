<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>
<div class="navDiv" style="width:1175px;margin:0 auto;">
    <a href="<%=path%>/common/zcSchool.html">众筹学院</a> &gt; <a href="<%=path%>/common/zcKnowList.html?type=know">众筹知识</a> &gt; <span id="breadTop" class="zc_bread_top"></span>
</div>
<div class="zcxy_box" style="margin-top:10px;">
	<div class="zcxy_maLix">
		<p class="zc_detial_title" id="title"></p>
		<p class="zc_detial_time" id="time"><p>
		<div style="border-top:1px solid #000;margin-top:10px;margin-bottom:10px;"></div>
		<div id="content" class="zc_content"></div>
	</div>
</div>
<input id="indexFor" type="hidden" namefor="zcSchool"/>
<script type="text/javascript" src="<%=path%>/js/zcSchool/zcKnowDetail.js"></script>