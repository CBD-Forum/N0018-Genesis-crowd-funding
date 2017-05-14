<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>
<!-- <div class="banner_lt"></div> -->

<div class="bai_box" style="padding-bottom:10px;background:#F3F3F3;">
    <div class="box ltListbox">
        <div class="ltList">
            <div class="lt_tzly">
                <span>投资领域：</span>
                <ul class="lt_tzly_ul" id="concernIndustryUl">
                   <!--  <li class="on"><a href="#">全部</a></li>
                    <li><a href="#">互联网</a></li>
                    <li><a href="#">移动互联网</a></li>
                    <li><a href="#">电子商务</a></li>
                    <li><a href="#">房地产</a></li>
                    <li><a href="#">金融</a></li>
                    <li><a href="#">物流与仓储</a></li>
                    <li><a href="#">快消品</a></li>
                    <li><a href="#">文化</a></li> -->
                </ul>
            </div>
        <!--      <div class="lt_tzly">
               <span>投资地区：</span>
                <ul class="lt_tzly_ul">
                    <li class="on"><a href="#">全部</a></li>
                    <li><a href="#">北京</a></li>
                    <li><a href="#">太原</a></li>
                    <li><a href="#">上海</a></li>
                    <li><a href="#">广州</a></li>
                </ul>
            </div> -->
            <div class="lt_tzly">
                <span class="lt_tzly_tzr"><i>投</i><i>资</i>人：</span>
                <ul class="lt_tzly_ul" id="titleApprove">
                    <li class="on" id="titleApproveAll"><a href="javascript:void(0);" code="">全部</a></li>
                    <li id="titleApproveInvest"><a href="javascript:void(0);" code="authed">跟投人认证</a></li>
                    <li id="titleApproveLeader"><a href="javascript:void(0);" code="lead">领投人认证</a></li>
                </ul>
                <span class="button" id="go2RZ" style="float:right;padding:10px 25px;margin:-40px 30px 0 0;color:#FFF;+margin-top:-60px;">我要认证</span>
            </div>
        </div>
    </div>
    <div class="box" style="overflow: hidden;">
        <div id="ltdw"><div></div></div>
    </div>
	<div class="page" id="approvePage"></div>
</div>
<!-- 
<div class="box">
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
</div> -->
<input id="indexFor" type="hidden" namefor="ltgt"/>
<input id="concernIndustry" type="hidden" name="concernIndustry" />
<input id="userLevel" type="hidden" name="userLevel" />
<script type="text/javascript" src="<%=path%>/js/common/jPages.js"></script>
<script type="text/javascript" src="<%=path%>/js/ltgt/ltgt.js"></script>
