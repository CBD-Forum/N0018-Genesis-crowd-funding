<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
String realName = (String)session.getAttribute("realName");
%>
<link type="text/css" rel="stylesheet" href="<%=path%>/style/ichou.css" />
<div class="box about_us">
	<div class="about_us_num">
    	<h4 class="about_us_tit">01</h4>
        <p class="about_us_font">什么是爱筹网？</p>
        <p class="about_us_1">中国最全面的线下孵化众筹平台——爱筹网（www.5aichou.com ），隶属于上海爱投商务咨询有限公司，<br>
是致力于筹好货、私募股权众筹、房地产众筹、筹爱心的众筹平台，为众筹项目发起者提供筹资、孵化一站式综合服务，<br>
旨在为项目方和投资人搭建一个公平、透明、安全、高效、稳定的互联网金融服务平台。</p>
    </div>
    <div class="about_us_num">
    	<h4 class="about_us_tit">02</h4>
        <p class="about_us_font">平台保障</p>
        <ul class="about_us_2">
        	<li>
            	<div class="img"><img src="<%=path%>/images/ichou/us2.png"></div>
                <p class="p1">项目评审</p>
                <p class="p2">专业的评审机构</p>
            </li>
        	<li>
            	<div class="img"><img src="<%=path%>/images/ichou/us3.png"></div>
                <p class="p1">项目风控</p>
                <p class="p2">严格的风控体系</p>
            </li>
        	<li>
            	<div class="img"><img src="<%=path%>/images/ichou/us4.png"></div>
                <p class="p1">财务监管</p>
                <p class="p2">专业的财务监管机构</p>
            </li>
        </ul>
    </div>
    <div class="about_us_num">
    	<h4 class="about_us_tit">03</h4>
        <p class="about_us_font">交易流程</p>
        <div class="tc"><img src="<%=path%>/images/ichou/us5.png"></div>
    </div>
</div>
<%-- <div class="about_banner"></div>
<div class="about_tab">
	<ul class="box">
    	<li><a href="javascript:void(0);" class="a_home">关于我们</a></li>
        <li><a href="<%=path%>/common/ourTeam.html">团队介绍</a></li>
        <li><a href="<%=path%>/common/about-ckkj.html" style="width:125px;">爱筹网创客空间</a></li>
    </ul>
</div>

<div class="about_ren">
	<div class="box clearfix">
    <h3><img src="<%=path%>/images/about_a.png"/></h3>
	<div class="fl left_text">
    	<p class="p2">中国最强线下孵化众筹平台——爱筹网（www.rrtsangel.com ），隶属于山西爱筹网网络科技有限公司，由爱筹网投资管理（北京）股份有限公司控股，山西爱筹网网络科技有限公司开发运营，是专注于筹爱心、筹好货、筹好业、筹好房的综合性众筹平台，为众筹项目发起者提供筹资、孵化一站式综合服务，旨在为项目方和投资人搭建一个公平、透明、安全、高效、稳定的互联网金融服务平台。</p></div>
    <div class="fr"><img src="<%=path%>/images/about_3.png"/></div>
    </div>
    <h3><img src="<%=path%>/images/about_b.png"/></h3>
</div>

<div class="about_shi">
	<div class="box clearfix">
    <div class="fl rxi" id="aboutusVideo" style="width:594px;height:324px;"></div>
	<div class="rxi_right fr">
    	<p class="p1">爱筹网，未来已来 <img src="<%=path%>/images/about_f.png"/></p>
    	<p class="p2">爱筹网众筹平台，肩负“普惠小微金融，重塑商业模式”的使命，倡导“人人皆天使”，为促进小微企业融资、改善投融资环境，推动互联网金融健康快速发展，顺应“大众创业，万众创新”指导方向，贡献绵薄之力。</p>
    </div>
    </div>
</div>

<div class="about_lx">
	<div class="box clearfix">
    <h3><img src="<%=path%>/images/about_c.png"/></h3>
	<div class="fl left_text">
    	<p class="p2">邮编：030006<br />
        Email: rrts@rrtsangel.com<br />
        地址：中国·太原 长风街与体育路交叉口向南200米路西亲贤社区向内200米
         </p></div>
    <div class="fr">全国统一咨询服务热线：400-820-0878</div>
    </div>
</div>
<div class="lx_map" id="allmap">
	
</div> --%>
<input id="indexFor" type="hidden" namefor="help"/>
<script type="text/javascript" src="<%=path%>/js/aboutus/aboutus_new.js"></script>
