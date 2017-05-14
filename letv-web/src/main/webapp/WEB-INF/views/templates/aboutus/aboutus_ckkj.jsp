<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
String realName = (String)session.getAttribute("realName");
%>
<div class="about_banner"></div>
<div class="about_tab">
	<ul class="box">
    	<li><a href="<%=path%>/common/aboutus.html">关于我们</a></li>
        <li><a href="<%=path%>/common/ourTeam.html">团队介绍</a></li>
        <li><a href="javascript:void(0);" style="width:125px;" class="a_home">爱筹网创客空间</a></li>
    </ul>
</div>

<div class="ck_room">
	<div class="box">
        <h3><img src="<%=path%>/images/about_g.png"/></h3>
        <div class="clearfix">
            <p class="left_text">爱筹网创客空间是响应李克强总理“大众创业，万众创新”的号召，为创业者提供一个开放、专业、休闲的交流场所和沟通平台，为创业者提供创业指导、项目路演、项目孵化、创业基金、品牌推广、人才招聘等综合性的创业生态体系。</p>
            <div class="fr"><img src="<%=path%>/images/ck1.png"/></div>
    	</div>
        <h3><img src="<%=path%>/images/about_h.png"/></h3>
        <div class="ck_space">
            <div class="space_left_box">
                <ul class="space_left">
                    <li><img src="<%=path%>/images/about_j.png"/></li>
                    <li><img src="<%=path%>/images/about_j1.png"/></li>
                    <li><img src="<%=path%>/images/about_j2.png"/></li>
                    <li><img src="<%=path%>/images/about_j3.png"/></li>
                    <li><img src="<%=path%>/images/about_j4.png"/></li>
                    <li><img src="<%=path%>/images/about_j5.png"/></li>
                    <li><img src="<%=path%>/images/about_j6.png"/></li>
                    <li><img src="<%=path%>/images/about_j7.png"/></li>
                </ul>
            </div>
            <div class="space_right">
            	<p>爱筹网创客空间</p>
                <img src="<%=path%>/images/about_f.png" width="149"/>
            </div>
        </div>
    </div>
</div>
<input id="indexFor" type="hidden" namefor="help"/>