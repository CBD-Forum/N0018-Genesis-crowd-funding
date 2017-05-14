<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>
<div class="userCenter_nav">
	<ul id="secondMenu">
	</ul>
</div>
<div class="aboutbox">
	<div class="tit">网站公告</div>
	<ul id="dynUl" class="dynUl">
	</ul>
</div>

<input id="indexFor" type="hidden" namefor="help"/>
<script type="text/javascript" src="<%=path%>/js/aboutUs/notice.js"></script>