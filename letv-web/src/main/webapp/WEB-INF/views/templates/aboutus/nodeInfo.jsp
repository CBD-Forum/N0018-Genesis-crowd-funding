<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
String realName = (String)session.getAttribute("realName");
String userId = session.getAttribute("userId")==null?null:session.getAttribute("userId").toString();
String lastLoginTime = (String)session.getAttribute("lastLoginTime");
String isBorrower = (String)session.getAttribute("isBorrower");
String isAuth = (String)session.getAttribute("isAuth");
%>
<div class="box">
    <p class="aboutUs">关于我们</p>
    <div class="aboutUs_box">
        <div class="left aboutUs_list">
            <ul id="secondMenu">
            </ul>
            <p>
                <a href="<%=path%>/common/myCenter.html">返回个人中心</a>
            </p>
        </div>
        <div class="left aboutUs_art">
        	<div class="nodeList">
	        	<div id="title" class="title">
	        	</div>
	        	<div id="content" class="content">
	        	</div>
        	</div>
        </div>
    </div>
</div>
<input id="indexFor" type="hidden" namefor="help"/>
<script type="text/javascript" src="<%=path%>/js/aboutus/nodeInfo.js"></script>