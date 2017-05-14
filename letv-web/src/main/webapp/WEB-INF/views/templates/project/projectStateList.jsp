<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%
	String path = request.getContextPath();
%>

<div class="box">
	<div class="navDiv_gy clearfix"><a href="<%=path%>/common/index.html">首页</a> > <a href="<%=path%>/common/projectList.html">项目</a> > <span id="breadWord"></span></div>
	<div class="gyzc">
		<div id="breadTitle" class="bread_title"></div>
	</div>
</div>

<div class="box projectList rel_ov">
<div class="w1000_ri">
	<div class="projectInfo">
		<div id="stockLoanIndex" class="title clearfix"><span>筹好业</span><a href="<%=path%>/common/projectList.html">查看更多>></a></div>
        <div class="info clearfix">
        	<ul id="indexStockList"></ul>
        </div>
	</div>
	<div class="projectInfo">
		<div id="houseLoanIndex" class="title clearfix"><span>筹好房</span><a href="<%=path%>/common/projectList.html">查看更多>></a></div>
        <div class="info clearfix">
        	<ul id="indexHouseList"></ul>
        </div>
	</div>
    <div class="projectInfo">
		<div id="entityLoanIndex" class="title clearfix"><span>筹好货</span><a href="<%=path%>/common/projectList.html">查看更多>></a></div>
        <div class="info clearfix">
        	<ul id="indexEntityList"></ul>
        </div>
	</div>
    <div class="projectInfo">
		<div id="public_serviceLoanIndex" class="title clearfix"><span>筹爱心</span><a href="<%=path%>/common/projectList.html">查看更多>></a></div>
        <div class="info clearfix">
        	<ul id="indexPublic_serviceList"></ul>
        </div>
	</div>
</div>
</div>


<div class="box projectList">    
    <!-- <div class="fastNav">
    	<div class="title">第一次接触众筹吗？</div>
        <ul class="clearfix">
        	<li>
            	<a href="<%=path%>/common/enterProjectNav.html"><img src="<%=path%>/images/nav-ico1.png" /></a><br />
                <a href="<%=path%>/common/enterProjectNav.html" style="margin-top:64px;">我有项目</a>
            </li>
            <li>
            	<a href="<%=path%>/common/projectList.html"><img src="<%=path%>/images/nav-ico2.png" /></a><br />
                <a href="<%=path%>/common/projectList.html" style="margin-top:64px;">我想投资</a>
            </li>
            <li>
            	<a href="<%=path%>/common/nodeList.html?nodeType=help"><img src="<%=path%>/images/nav-ico3.png" /></a><br />
                <a href="<%=path%>/common/nodeList.html?nodeType=help" style="margin-top:64px;">新手指南</a>
            </li>
            <li>
            	<a href="<%=path%>/common/nodeList.html?nodeType=help"><img src="<%=path%>/images/nav-ico4.png" /></a><br />
                <a href="<%=path%>/common/nodeList.html?nodeType=help" style="margin-top:64px;">了解我们</a>
            </li>
        </ul>
    </div> -->
</div>
<input id="indexFor" type="hidden" namefor="loan"/>
<script type="text/javascript" src="<%=path%>/js/project/projectStateList.js"></script>