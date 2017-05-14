<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>
<div class="userCenter_nav">
	<ul id="secondMenu">
	</ul>
</div>
<div class="box_2 nav">
	<a href="/longtoudai-web/common/index.html">首页</a>&gt;<a href="<%=path%>/common/dynamic.html">最新动态</a>&gt;<span id="title"></span>
</div>
<div class="aboutbox">
</div>

<input id="indexFor" type="hidden" namefor="help"/>
<script type="text/javascript" src="<%=path%>/js/aboutUs/dynamicAndNotice_info.js"></script>