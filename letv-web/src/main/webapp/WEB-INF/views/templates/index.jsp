<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%
	String path = request.getContextPath();
%>


<div class="banner" id="Banner" style="min-width:1200px; height:460px;">
	<div class="ibanner index_pig" id="index_pic" style="width:100%; height:100% ;">
    	<ul id="banner_pig"></ul>
    </div>
</div>

<div class="notice">
	<div class="box">
    	<a href="<%=path%>/common/nodeList.html?nodeType=zxgg" class="fr more-notice">更多公告</a>
        <div class="newest-notice" id="nodeCodeOne"><span>最新公告：</span></div>
    </div>
</div>
<div class="box index-tit"><span>热门推荐</span></div>
<div class="box popularRecommend clearfix">
	<div class="recommendFl fl" id="indexHotListTop">
    	
    </div>
    <div class="recommendList fr" id="indexHotList">
    	
    </div>
</div>
<div class="box index-tit"><span>产品众筹类</span><a class="index_more" href="<%=path%>/common/projectList.html?type=product">更多>></a></div>
<div class="box clearfix" style="overflow: inherit;" id="indexFundingList">
	
	
	
</div>
<div class="box index-tit"><span>非公开融资</span></div><!-- <a class="index_more" href="<%=path%>/common/projectList.html?type=stock">更多>></a> -->
<div class="box"><a href="<%=path%>/common/projectList.html?type=stock"><img src="<%=path%>/images/letv/letv_01.jpg"></a></div>
<div class="box index-tit"><span>公益众筹类</span><a class="index_more" href="<%=path%>/common/projectList.html?type=public">更多>></a></div>
<div class="box clearfix" style="overflow: inherit;" id="indexPreheatList">
	
</div>
<div class="box consultationNotice clearfix" style="overflow: inherit;">
	<div class="consultation fl">
    	<div class="divImg fl"><img src="" id="indexImgZCZX"  class="fl" width="280" height="154"></div>
        <div class="fr consultationFr" id="consultation">
        	<h3><a href="<%=path%>/common/nodeList.html?nodeType=zczx" class="fr">更多</a>众筹资讯</h3>
        </div>
    </div>
	<div class="noticeA fr">
    	<div class="divImg fl"><img src="" id="indexImgZXGG" class="fl" width="280" height="154"></div>
        <div class="fr">
        	<div class="fr noticeAfr" id="noticeList_l">
                <h3><a href="<%=path%>/common/nodeList.html?nodeType=zxgg" class="fr">更多</a>最新公告</h3>
            </div>
        </div>
    </div>
</div>
<div class="box"style="position:relative;">
	<img src="<%=path%>/images/letv/letv_02.jpg">
	<div class="index_cont" id="index_cont"></div>
</div>
<!-- <div class="mechanism">
    <div class="box" id="zzsc">
        <h4><span class="fr next" id="og_next"> </span><span class="fr prev" id="og_prev"> </span>合作机构</h4>
        <div style="height:175px;overflow: hidden;">
	        <div class="scroll" id="banner_hzhb">
	            
	        </div>
        </div>
    </div>
</div> -->


<div id="bgpop" class="bgpop" style="z-index:100;"></div>

<div id="scriptDiv"></div>
<div id="scriptDiv1"></div>
<input id="indexFor" type="hidden" namefor="index"/>
<div class="indexBom">
	<div class="indexBom-back"></div>
	<ul class="indexBom-Ul">
		<li><span id='fundingLoanCount'></span>进行中众筹项目</li>
		<li><span id='successLoanCount'></span>成功众筹项目</li>
		<li><span id='succcessLoanAmt'></span>成功众筹金额</li>
		<li><span id='userCount'></span>注册人数</li>
	</ul>
	<div class="indexBom-none"></div>
</div>
<script type="text/javascript" src="<%=path%>/js/common/rollImage.js"></script>
<script type="text/javascript" src="<%=path%>/js/index.js"></script>