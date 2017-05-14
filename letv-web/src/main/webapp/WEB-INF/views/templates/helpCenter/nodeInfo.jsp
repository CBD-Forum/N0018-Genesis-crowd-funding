<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>
<div class="box help_center">
	<ul class="help_left" id="secondMenu">
    </ul>
    <ul class="help_right" id="cotent">
    </ul>
</div>
<input id="indexFor" type="hidden" namefor="help"/>
<script type="text/javascript" src="<%=path%>/js/helpCenter/nodeInfo.js"></script>