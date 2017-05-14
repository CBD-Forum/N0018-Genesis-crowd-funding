<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
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
        	<div id="title" class="nodeList" style="line-height:45px;font-size:16px;font-weight: bold;border-bottom:1px solid #e3e3e3;">
        	</div>
        	<ul id="nodelist" class="nodeList">
        	</ul>
        </div>
    </div>
</div>
<input id="indexFor" type="hidden" namefor="help"/>
<script type="text/javascript" src="<%=path%>/js/aboutus/aboutus.js"></script>