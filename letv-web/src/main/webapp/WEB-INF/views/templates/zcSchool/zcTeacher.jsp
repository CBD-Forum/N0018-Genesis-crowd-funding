<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>
<div class="mxds_ban"></div>

<div class="bgColorWh">
	<div class="mxds_work">
    	<img src="<%=path%>/images/mxds_work.png" width="245"/>
        <ul class="work_list clearfix">
            <li><a ><img src="<%=path%>/images/mxds_w1.png" width="280"/></a></li>
            <li><a ><img src="<%=path%>/images/mxds_w2.png" width="280"/></a></li>
<!--             <li><a ><img src="<%=path%>/images/mxds_w3.png" width="280"/></a></li> -->
            <li><a ><img src="<%=path%>/images/mxds_w5.png" width="280"/></a></li>
            <li><a ><img src="<%=path%>/images/mxds_w4.png" width="280"/></a></li>
        </ul>
    </div>
	<div class="mxds_team">
   	<div class="box">
    	<img src="<%=path%>/images/mxds_team.png" width="226"/>
        <ul class="team_list">
        	<li><i></i><img src="<%=path%>/images/mxds_team1.png" width="238"/></li>
            <li><i></i><img src="<%=path%>/images/1.png" width="238"/></li>
            <li><i></i><img src="<%=path%>/images/2.png" width="238"/></li>
            <li><i></i><img src="<%=path%>/images/3.png" width="238"/></li>
            <li><i></i><img src="<%=path%>/images/4.png" width="238"/></li>
            <li><i></i><img src="<%=path%>/images/7.png" width="238"/></li>
            <li><i></i><img src="<%=path%>/images/8.png" width="238"/></li>
            <li><i></i><img src="<%=path%>/images/9.png" width="238"/></li>
            <li><i></i><img src="<%=path%>/images/6.png" width="238"/></li>
            <li><i></i><img src="<%=path%>/images/5.png" width="238"/></li>
        </ul>
    </div>
    </div>
    
    <div class="box mxds_video">
    	<img src="<%=path%>/images/mxds_video.png" width="228"/>
        <ul class="video_list">
        	<li class="fl"><p><i></i></p><img src="<%=path%>/images/video_1.png" width="582"/></li>
            <li class="fr"><p><i></i></p><img src="<%=path%>/images/video_1.png" width="582"/></li>
        </ul>
    </div>
    <div class="mxds_back"><a href="<%=path%>/common/zcSchool.html">返回上一级</a></div>
</div>
<input id="indexFor" type="hidden" namefor="zcSchool"/>
