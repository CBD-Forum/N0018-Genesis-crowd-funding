<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>
<!-- <div class="zcxy_banner">
<div class="box">
	<a href="#" class="ban_left"></a>
    <a href="#" class="ban_right"></a>
</div>
</div> -->
<div class="banner" id="Banner">
	<div class="ibanner index_pig" id="index_pic" style="width:100%; height:100% ;">
    	<ul id="banner_pig"></ul>
    </div>
</div>

<div class="box">
	<div class="zcxy_img_tit"><i class="i3"></i>商学院课程表</div>
	<div class="zcxy_bb">
    	<div class="zcxy_calendar"></div>
    	<div id="mr_frbox">
	    	<div id="mr_frUl" class="zcxy_title">
		        <ul id="courseTitle" class="zcxy_cal clearfix"></ul>
	        </div>
	        <a href="javascript:void(0);" class="bbl mr_frBtnL prev"></a>
	        <a href="javascript:void(0);" class="bbr mr_frBtnR next"></a>
        </div>
       <%--  <div class="zcxy_dian"><img src="<%=path %>/images/zcxy_dian.png"/></div> --%>
        
        <div id="teacherInfo"></div>
        
        
    </div>
</div>

<div class="zcxy_box clearfix">
	<div class="zcxy_fl">
    	<div class="zcxy_img_tit"><i class="i1"></i>众筹知识</div>
    	<div class = "zcxy_maLix zcxy_new">
	    	<ul id="zczsList">
	        </ul>
	        <a href="<%=path %>/common/zcKnowList.html?type=know" class="more">查看更多</a>
        </div>
        
        <div class="zcxy_img_tit"><i class="i2"></i>行业资讯</div>
        <div class = "zcxy_maLix zcxy_new">
	        <ul id="hyzxList">
	        </ul>
	        <a href="<%=path %>/common/zcKnowList.html?type=info" class="more">查看更多</a>
	    </div>
	</div>
    <div class="zcxy_fr" style="padding:0 50px;">
    	<div class="zcxy_img_tit"><i></i>明星导师</div>
	    <div id="teacherList" class="zcxy_fr zcxy_maLix"></div>
    </div>
</div>




<div class="box projectList">    
    <div class="fastNav">
    	<div class="title">第一次接触众筹吗？</div>
        <ul class="clearfix">
        	<li>
               <a href="<%=path %>/common/enterProjectNav.html"><img src="<%=path %>/images/nav-ico1.png" /></a><br />
               <a href="<%=path %>/common/enterProjectNav.html" style="margin-top:64px;">发起项目</a>
           </li>
           <li>
               <a href="<%=path %>/common/projectList.html"><img src="<%=path %>/images/nav-ico2.png" /></a><br />
               <a href="<%=path %>/common/projectList.html" style="margin-top:64px;">我要投资</a>
           </li>
           <li>
               <a href="<%=path%>/common/newGuide.html"><img src="<%=path %>/images/nav-ico3.png" /></a><br />
               <a href="<%=path%>/common/newGuide.html" style="margin-top:64px;">新手指南</a>
           </li>
           <li>
               <a href="<%=path%>/common/aboutus.html"><img src="<%=path%>/images/nav-ico4.png" /></a><br />
                <a href="<%=path%>/common/aboutus.html" style="margin-top:64px;">了解我们</a>
           </li>
        </ul>
    </div>
</div>
<input id="indexFor" type="hidden" namefor="zcSchool"/>
<script type="text/javascript" src="<%=path%>/js/common/jquery.SuperSlide2.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/rollImage.js"></script>
<script type="text/javascript" src="<%=path%>/js/zcSchool/zcSchool.js"></script>