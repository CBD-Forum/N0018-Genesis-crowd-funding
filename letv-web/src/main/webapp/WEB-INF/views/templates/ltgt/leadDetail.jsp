<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>
<!-- <div class="banner_lt"></div> -->

<div class="ltxq_banner">
	<div class="ltxq_top">
        <div class="ltxq_boxWidth clearfix">
            <dl class="fl" style="width:660px;">
                <dt><img id="ltPhoto" src="" width="122" height="122"></dt>
                <dd>
                    <div class="ltxq_title clearfix">
                    	<span id="realName"></span><br/>
                    	<i id="concernIndustry"></i>
                    </div>
                    <p id="selfDetail"></p>
                </dd>
            </dl>
            <div class="fl ltxq_share" id="ltAttentDiv"></div>
        </div>
    </div>
    
    
</div>

<div class="ltxq_boxWidth clearfix">
    <div class="ltxq_left">
        <div class="ltxq_main case">
            <div class="ltxq_tit">投资过的项目</div>
            <div id="iLoanDiv">
	            <ul class="ltxq_sh"></ul>
            </div>
            <div class="page" id="ipage" style="display:none;margin-top:30px;"></div>
        </div>
        <div class="ltxq_main explain">
        	<div class="ltxq_tit">个人投资说明</div>
        	<div id="userExperience">
        		
        	</div>
            <!-- <dl>
            	<dt>投资理念</dt>
                <dd>1、团队拥有一定的行业资源和经验。</dd>
                <dd>2、团队有很强的执行力和凝聚力。</dd>
            </dl>
            <dl>
            	<dt>•擅长投资领域</dt>
            	<dd>旅游 • 金融 • 投资 • 物流 • 平台 • 移动互联网</dd>
            </dl>
            <dl>
            	<dt>•可以提供的附加价值</dt>
            	<dd>管理咨询经验其他服务 • 商业模式分析 • 管理咨询</dd>
            </dl> -->
        </div>
        <ul class="ltxq_main follow" style="height:auto;">
        	<div class="ltxq_tit">关注过的项目</div>
        	<div id="aLoanDiv">
        		<div></div>
        	</div>
        	<div class="page" id="apage" style="display:none;margin-top:30px;"></div>
        </ul>
    </div>
    <div class="ltxq_right">
        <ul class="ltxq_main ltxq_play">
        	<div class="ltxq_tit">投资计划</div>
            <li class="play_top clearfix">
                <span>关注领域</span>
                <p id="concernIndustry1"></p>
                <span>关注城市</span>
                <p id="concernCity"></p>
            </li>
            <li class="play_min clearfix">
            	<p class="fl">一年计划投资项目<br /><i id="investPlanNum"></i>个</p>
                <p class="fr">预计总投资额<br />￥<i id="investPlanAmt"></i></p>
            </li>
            <li class="play_bottom"><a href="<%=path%>/common/projectList.html">投资项目</a></li>
        </ul>
        <div class="ltxq_fans">
        	<div class="ltxq_tab clearfix" id="ltxqTab">
            	<a href="javascript:void(0);" class="a_home" name="attent">关注</a>
                <a href="javascript:void(0);" name="likey">粉丝</a>
            </div>
            <div id="ltxqBody">
            	<div class="ltxq_tx clearfix" id="attentDiv" style="padding:40px;">
	            	
	            </div>
	            <div class="ltxq_w clearfix" id="likeyDiv" style="padding:40px;">
	            	
	            </div>
            </div>
        </div>
    </div>
</div>
<div style="width:100%; height:100px;"></div>
<!--
<div class="box projectList">    
    <div class="fastNav">
    	<div class="title">第一次接触众筹吗？</div>
        <ul class="clearfix">
        	<li>
            	<img src="<%=path%>/images/nav-ico1.png" /><br />
                <a href="<%=path%>/common/enterProjectNav.html">我有项目</a>
            </li>
            <li>
            	<img src="<%=path%>/images/nav-ico2.png" /><br />
                <a href="<%=path%>/common/projectList.html">我想投资</a>
            </li>
            <li>
            	<img src="<%=path%>/images/nav-ico3.png" /><br />
                <a href="<%=path%>/common/newGuide.html">新手指南</a>
            </li>
            <li>
            	<img src="<%=path%>/images/nav-ico4.png" /><br />
                <a href="<%=path%>/common/aboutus.html">了解我们</a>
            </li>
        </ul>
    </div>
</div>
  -->
<input id="indexFor" type="hidden" namefor="ltgt"/>

<script type="text/javascript" src="<%=path%>/js/common/jPages.js"></script>
<script type="text/javascript" src="<%=path%>/js/ltgt/leadDetail.js"></script>