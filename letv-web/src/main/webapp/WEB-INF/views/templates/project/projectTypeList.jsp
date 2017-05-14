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
<div class="projectNav" id="indexScrollLoan">
	<ul>
    	<li class="cur"><img src="<%=path%>/images/dian_lan.png" width="15"/><a scroll="#hotLoanIndex" href="javascript:void(0);">热门项目</a></li>
        <li><img src="<%=path%>/images/dian_bai.png" width="15"/><a scroll="#preheatLoanIndex" href="javascript:void(0);">预热项目</a></li>
        <li><img src="<%=path%>/images/dian_bai.png" width="15"/><a scroll="#fundingLoanIndex" href="javascript:void(0);">融资项目</a></li>
        <li><img src="<%=path%>/images/dian_bai.png" width="15"/><a scroll="#successLoanIndex" href="javascript:void(0);">成功项目</a></li>
        <li><img src="<%=path%>/images/dian_bai.png" width="15"/><a scroll="#backLoanIndex" href="javascript:void(0);">回报项目</a></li>
    </ul>
    <div class="xian"></div>
    <div class="lt_ko"></div>
</div>
<div class="box projectList rel_ov">
	<div class="w1000_ri">
		<div class="projectInfo">
			<div id="hotLoanIndex" class="title clearfix"><span>热门项目</span><a href="<%=path%>/common/projectList.html">查看更多>></a></div>
	        <div class="info clearfix">
	        	<ul id="indexHotList"></ul>
	        </div>
		</div>
	    
	    <div class="projectInfo">
			<div id="preheatLoanIndex" class="title clearfix"><span>预热项目</span><a href="<%=path%>/common/projectList.html">查看更多>></a></div>
	        <div class="info clearfix">
	        	<ul id="indexPreheatList"></ul>
	        </div>
		</div>
	    
	    <div class="projectInfo">
			<div id="fundingLoanIndex" class="title clearfix"><span>融资项目</span><a href="<%=path%>/common/projectList.html">查看更多>></a></div>
	        <div class="info clearfix">
	        	<ul id="indexFundingList"></ul>
	        </div>
		</div>
	    
	    <div class="projectInfo">
			<div id="successLoanIndex" class="title clearfix"><span>成功项目</span><a href="<%=path%>/common/projectList.html">查看更多>></a></div>
	        <div class="info clearfix">
	        	<ul id="indexSuccessList"></ul>
	        </div>
		</div>
	    
	    <div class="projectInfo">
			<div id="backLoanIndex" class="title clearfix"><span>回报项目</span><a href="<%=path%>/common/projectList.html">查看更多>></a></div>
	        <div class="info clearfix">
	        	<ul id="indexBackList"></ul>
	        </div>
		</div>
	</div>
</div>


<div class="box projectList">    
    <div class="fastNav">
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
            	<a href="<%=path%>/common/newGuide.html"><img src="<%=path%>/images/nav-ico3.png" /></a><br />
                <a href="<%=path%>/common/newGuide.html" style="margin-top:64px;">新手指南</a>
            </li>
            <li>
            	<a href="<%=path%>/common/aboutus.html"><img src="<%=path%>/images/nav-ico4.png" /></a><br />
                <a href="<%=path%>/common/aboutus.html" style="margin-top:64px;">了解我们</a>
            </li>
        </ul>
    </div>
</div>

<input id="indexFor" type="hidden" namefor="loan"/>
<script type="text/javascript" src="<%=path%>/js/project/projectTypeList.js"></script>